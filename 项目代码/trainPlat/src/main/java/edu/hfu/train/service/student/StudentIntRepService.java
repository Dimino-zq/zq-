package edu.hfu.train.service.student;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentInterReport;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.student.StudentApplicationDao;
import edu.hfu.train.dao.student.StudentIntRepDao;

@Service
@Transactional
public class StudentIntRepService {
	@Resource
	StudentIntRepDao studentrepdao;

	@Resource
	StudentApplicationDao applicationdao;

	/**
	 * 根据实习报告书的id查询
	 * 
	 * @param reportId
	 * @return StudentInterReport 实习报告书对象
	 * @throws Exception
	 */
	public StudentInterReport getReportById(Integer reportId) throws Exception {
		return studentrepdao.getReportById(reportId);
	}

	/**
	 * 根据学生id查询
	 * 
	 * @param student
	 *            学生对象
	 * @return List
	 * @throws Exception
	 */
	public List<StudentInterReport> getReportByStudent(SysStudent student) throws Exception {
		if (student.getStudentId() == null)
			throw new Exception("查询失败：学生Id为空！");
		return studentrepdao.getReportByStudent(student);
	}
	
    /**
     * 检查当前学生是否由已通过的实习申请或变更申请（变更申请通过时，原实习申请作废），
     * 若不满足条件则返回false，前端应无法进入变更申请界面
     * @param student
     * @return
     * @throws Exception
     */
    public Boolean checkApplication(SysStudent student) throws Exception
    {
        if (student.getStudentId()==null)
            throw new Exception("查询失败：学生Id为空！");
        List<StudentApplication> ls = applicationdao.getApplication(student);
        if (ls==null || ls.size()==0)
            throw new RuntimeException("实习申请不存在！");
        if (!ls.get(0).getStatus().equals("DApprove") && !ls.get(0).getStatus().equals("EApprove")) //不是 ”已通过“ 或 ”作废“ 状态
            throw new RuntimeException("没有已通过的申请！");
        return true;
    }

	/**
	 * 保存报告表，只允许存在一张实习报告表，否则不允许保存
	 * 
	 * @param report
	 *            报告对象
	 * @return String
	 * @throws Exception
	 */
	public String saveReport(StudentInterReport report, SysStudent student) throws Exception {
		// 获取学生报告表
		List<StudentInterReport> list = studentrepdao.getReportByStudent(student);
		// 若已存在报告则抛出异常
		if (null != list && 0 != list.size())
			throw new Exception("保存失败：已创建实习报告书，请勿重复操作！");
		// 获取当前学生的实习申请
		List<StudentApplication> applicationList = applicationdao.getApplication(student);
		// 若不存在申请则抛出异常
		if (null == applicationList || 0 == applicationList.size())
			throw new Exception("保存失败：您还未开始实习！");

		report.setApplication(applicationList.get(0));
		report.setTeacherNo(applicationList.get(0).getTeacherNo());
		report.setStatus("未评阅");
		report.setCreateDate(new Date()); // 创建时间
		report.setUpdDate(new Date()); // 更新时间
		report.setCreateUser(student.getStudentNo()); // 创建人
		report.setUpdUser(student.getStudentNo()); // 修改人
		// 保存申请表
		studentrepdao.saveReport(report);
		return "保存成功";
	}

	/**
	 * 修改实习报告书
	 * 
	 * @param report
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public String updateReport(StudentInterReport report, Integer studentId) throws Exception {
		// 获取原实习报告实例
		StudentInterReport interReport = studentrepdao.getReportById(report.getReportId());
		// 若查询不到原实习报告记录则抛出异常
		if (null == interReport)
			throw new NullPointerException("修改失败：未查找到该实习报告书的记录！");
		// 判断当前用户的操作是否合法
		if (!interReport.getApplication().getStudent().getStudentId().equals(studentId))
			throw new Exception("修改失败：信息不匹配，非法操作！");
		// 根据评阅人意见是否存在判断指导老师是否已经评阅该实习报告书

		if (null != interReport.getStatus())
			throw new RuntimeException("修改失败：该实习报告书已被指导老师评阅！");

		// 将持久化的原申请表替换新的申请信息
		interReport.setComProfile(report.getComProfile());
		interReport.setComworkfile(report.getComworkfile());
		interReport.setConclusion(report.getConclusion());
		// 设置修改人和修改时间
		interReport.setUpdDate(new Date());
		interReport.setUpdUser(interReport.getApplication().getStudent().getStudentNo());
		studentrepdao.updateReport(interReport);
		return "修改成功！";
	}
}
