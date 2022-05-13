package edu.hfu.train.service.student;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysReviewLog;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.student.StudentApplicationDao;
import edu.hfu.train.dao.student.StudentSecApplicationDao;
import edu.hfu.train.dao.sysset.ReviewLogDao;
import edu.hfu.train.service.sysset.FastDFSService;
import edu.hfu.train.util.FileUpload;

@Service
@Transactional
public class StudentSecApplicationService {
	@Resource
	StudentSecApplicationDao secApplicationDao;

	@Resource
	StudentApplicationDao applicationDao;

	@Resource
	ReviewLogDao reviewLogDao;
	
	@Autowired
	private FastDFSService fastDFSService;//上传

	private static final String RECORD_TYPE = "StudentSecApplication"; // 审批记录类型

	/**
	 * 根据变更申请表的id查询
	 * 
	 * @param secApplicationId
	 *            变更申请Id
	 * @return StudentSecApplication 变更申请表对象
	 * @throws Exception
	 */
	public StudentSecApplication getSecApplicationById(Integer secApplicationId) throws Exception {
		return secApplicationDao.getSecApplicationById(secApplicationId);
	}

	/**
	 * 根据学生id查询
	 * 
	 * @param student
	 *            学生对象
	 * @return List
	 * @throws Exception
	 */
	public List<StudentSecApplication> getSecApplication(SysStudent student) throws Exception {
		if (student.getStudentId() == null)
			throw new Exception("查询失败：学生Id为空！");
		return secApplicationDao.getSecApplication(student);
	}

	/**
	 * 检查当前学生是否由已通过的实习申请或变更申请（变更申请通过时，原实习申请作废）， 若不满足条件则返回false，前端应无法进入变更申请界面
	 * 
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public Boolean checkApplication(SysStudent student) throws Exception {
		if (student.getStudentId() == null)
			throw new Exception("查询失败：学生Id为空！");
		List<StudentApplication> ls = applicationDao.getApplication(student);
		if (ls == null || ls.size() == 0)
			throw new RuntimeException("实习申请不存在！");
		if (!ls.get(0).getStatus().equals("DApprove") && !ls.get(0).getStatus().equals("EApprove")) // 不是
																									// ”已通过“
																									// 或
																									// ”作废“
																									// 状态
			throw new RuntimeException("没有已通过的申请！");
		return true;
	}

	/**
	 * 保存变更申请表，仅在实习（变更）申请存在且已通过的情况下允许操作 变更申请表与第一次实习申请关联
	 * 
	 * @param secApplication
	 *            变更申请表对象
	 * @return String
	 * @throws Exception
	 */
	public String saveSecApplication(StudentSecApplication secApplication) throws Exception {
		// 获取当前学生所有的变更申请表
		List<StudentSecApplication> list = secApplicationDao
				.getSecApplication(secApplication.getApplication().getStudent());
		// 检查是否存在变更申请或已通过的变更申请
		out: if (null != list && 0 != list.size()) {
			// 检查是否存在已通过的变更申请
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getStatus().equals("EApprove"))
					continue;
				else if (list.get(i).getStatus().equals("DApprove")) {
					for (i = i + 1; i < list.size(); i++)
						if (!list.get(i).getStatus().equals("EApprove"))
							throw new RuntimeException("保存失败：当前还有未通过的申请！");
					break out;
				} else
					throw new RuntimeException("保存失败：当前还有未通过的申请！");
			}
			throw new RuntimeException("保存失败：当前没有已通过的申请！");
		}

		// 设置变更申请表的状态
		secApplication.setStatus("BApprove"); // 已提交
		secApplication.setCreateDate(new Date()); // 创建时间
		secApplication.setUpdDate(new Date()); // 更新时间
		secApplication.setCreateUser(secApplication.getApplication().getStudent().getStudentNo()); // 创建人
		secApplication.setUpdUser(secApplication.getApplication().getStudent().getStudentNo()); // 修改人
		// 保存申请表
		Integer applyId = secApplicationDao.saveSecApplication(secApplication);
		// 创建记录，原状态为新建
		reviewLogDao.addReviewLog(RECORD_TYPE, applyId, "创建", "AApprove", secApplication.getStatus(), "变更申请：创建成功！");
		return "保存成功";
	}

	/**
	 * 根据变更申请表的id删除，不允许删除状态为“已提交”、“已通过”和“作废”的变更申请表
	 * 
	 * @param secApplicationId
	 *            变更申请表Id
	 * @param studentId
	 *            学生Id
	 * @return String
	 * @throws Exception
	 */
	public String deleteSecApplication(Integer secApplicationId, Integer studentId) throws Exception {
		StudentSecApplication secApplication = getSecApplicationById(secApplicationId);
		if (null == secApplication)
			throw new NullPointerException("删除失败：无效的申请表Id");
		// 判断当前用户的操作是否合法
		if (!secApplication.getApplication().getStudent().getStudentId().equals(studentId))
			throw new Exception("删除失败：信息不匹配，非法操作！");
		// 获取变更申请表状态
		String status = secApplication.getStatus();
		// 判断申请表的状态
		if (status.equals("CApprove")) // 已提交
			throw new RuntimeException("删除失败：该申请已提交，请耐心等待审批！");
		else if (status.equals("DApprove")) // 审批通过
			throw new RuntimeException("删除失败：该申请已通过！");
		else if (status.equals("EApprove")) // 作废
			throw new RuntimeException("删除失败：该申请已作废！");
		// 删除附件
		if (null!=secApplication.getFilePath()) {//删除旧文件
			fastDFSService.deleteFile(secApplication.getFilePath());
		}
		// 删除该申请表
		secApplicationDao.deleteSecApplication(secApplicationId);
		return "删除成功！";
	}

	/**
	 * 更新“未提交”和“未通过”状态的变更申请表数据，不允许修改状态为“已提交”和“已通过”和“作废”的申请表
	 * 
	 * @param secApplication
	 *            修改后的变更申请表实例
	 * @param studentId
	 *            学生Id
	 * @return String
	 * @throws Exception
	 */
	public String updateSecApplication(StudentSecApplication secApplication, Integer studentId) throws Exception {
		// 获取原申请表实例
		StudentSecApplication studentSecApplication = getSecApplicationById(secApplication.getSecApplyId());
		// 若查询不到原申请表记录则抛出异常
		if (null == studentSecApplication)
			throw new NullPointerException("修改失败：未查找到该申请记录！");
		// 判断当前用户的操作是否合法
		if (!studentSecApplication.getApplication().getStudent().getStudentId().equals(studentId))
			throw new RuntimeException("修改失败：信息不匹配，非法操作！");
		// 获取申请表状态
		String status = studentSecApplication.getStatus();
		// 判断申请表的状态
		if (status.equals("CApprove")) // 已提交
			throw new RuntimeException("修改失败：该申请已提交，请耐心等待审批！");
		else if (status.equals("DApprove")) // 审批通过
			throw new RuntimeException("修改失败：该申请已通过！");
		else if (status.equals("EApprove")) // 作废
			throw new RuntimeException("修改失败：该申请已作废！");
		// 将持久化的原申请表替换新的申请信息
		studentSecApplication.setOldComName(secApplication.getOldComName());
		studentSecApplication.setOldComStartDate(secApplication.getOldComStartDate());
		studentSecApplication.setOldComEndDate(secApplication.getOldComEndDate());
		studentSecApplication.setNewCompany(secApplication.getNewCompany());
		studentSecApplication.setNewComStartDate(secApplication.getNewComStartDate());
		studentSecApplication.setNewComEndDate(secApplication.getNewComEndDate());
		studentSecApplication.setNewAdress(secApplication.getNewAdress());
		studentSecApplication.setNewStation(secApplication.getNewStation());
		studentSecApplication.setNewContent(secApplication.getNewContent());
		studentSecApplication.setReason(secApplication.getReason());// 重置各个审批意见
		studentSecApplication.setStatus("BApprove"); // 修改申请后申请状态重置为“未提交”
		studentSecApplication.setUpdDate(new Date()); // 修改时间
		studentSecApplication.setUpdUser(studentSecApplication.getApplication().getStudent().getStudentNo()); // 修改人
		secApplicationDao.updateSecApplication(studentSecApplication);
		return "修改成功！";
	}

	/**
	 * 将“未提交”变更申请表的状态修改为“已提交”状态，仅允许提交“未提交”的变更申请表
	 * 
	 * @param secApplisecAcationId
	 *            变更申请表Id
	 * @param studentId
	 *            学生Id
	 * @return String
	 * @throws Exception
	 */
	public String submitSecApplication(Integer secApplicationId, Integer studentId) throws Exception {
		// 获取原申请表实例
		StudentSecApplication secApplication = getSecApplicationById(secApplicationId);
		// 若查询不到原申请表记录则抛出异常
		if (null == secApplication)
			throw new NullPointerException("提交失败：未查找到该申请记录！");
		// 判断当前用户的操作是否合法
		if (!secApplication.getApplication().getStudent().getStudentId().equals(studentId))
			throw new RuntimeException("提交失败：信息不匹配，非法操作！");
		// 检查是否已经上传附件
		if (null == secApplication.getFilePath() || secApplication.getFilePath().equals(""))
			throw new RuntimeException("提交失败：未上传附件！");
		// 获取申请表状态
		String status = secApplication.getStatus();
		// 判断申请表的状态
		if (status.equals("CApprove")) // 已提交
			throw new RuntimeException("提交失败：该申请已提交，请勿重复操作！");
		else if (status.equals("DApprove")) // 审批通过
			throw new RuntimeException("提交失败：该申请已通过！");
		else if (status.equals("EApprove")) // 作废
			throw new RuntimeException("提交失败：该申请已作废！");
		else if (status.equals("FApprove")) // 未通过
			throw new RuntimeException("提交失败：请修改申请后再提交！");
		// 将持久化的原申请表替换新的申请信息
		// 清除意见数据
		secApplication.setTeacherView("");
		secApplication.setAdviserView("");
		secApplication.setDeptView("");
		secApplication.setTeacherName("");
		secApplication.setTeacherViewDate(null);
		secApplication.setAdviserName("");
		secApplication.setAdviserViewDate(null);
		secApplication.setDeptName("");
		secApplication.setDeptViewDate(null);
		secApplication.setStatus("CApprove"); // 已提交
		secApplication.setUpdDate(new Date()); // 修改时间
		secApplication.setUpdUser(secApplication.getApplication().getStudent().getStudentNo()); // 修改人
		secApplicationDao.updateSecApplication(secApplication);
		// 创建记录
		reviewLogDao.addReviewLog(RECORD_TYPE, secApplication.getSecApplyId(), "提交", status, secApplication.getStatus(),
				"变更申请：提交成功！");
		return "提交成功！";
	}

	/**
	 * 保存附件并将附件地址保存至数据库，变更申请表状态为“已提交”和“已通过”和“作废”时不允许操作
	 * 
	 * @param secApplicationId
	 *            变更申请表Id
	 * @param studentId
	 *            学生Id
	 * @param file
	 *            接收到的文件
	 * @param savepath
	 *            附件保存路径
	 * @return
	 * @throws Exception
	 */
	public String upLoadFile(Integer secApplicationId, Integer studentId, MultipartFile file, String savepath)
			throws Exception {
		// 获取原申请表实例
		StudentSecApplication secApplication = getSecApplicationById(secApplicationId);
		// 若查询不到原申请表记录则抛出异常
		if (null == secApplication)
			throw new NullPointerException("提交失败：未查找到该申请记录！");
		// 判断当前用户的操作是否合法
		if (!secApplication.getApplication().getStudent().getStudentId().equals(studentId))
			throw new Exception("提交失败：信息不匹配，非法操作！");
		// 获取申请表状态
		String status = secApplication.getStatus();
		// 判断申请表的状态
		if (status.equals("CApprove")) // 已提交
			throw new RuntimeException("提交失败：该申请已提交，请勿重复操作！");
		else if (status.equals("DApprove")) // 审批通过
			throw new RuntimeException("提交失败：该申请已通过！");
		else if (status.equals("EApprove")) // 作废
			throw new RuntimeException("提交失败：该申请已作废！");
		
		if (null!=secApplication.getFilePath()) {//删除旧文件
			fastDFSService.deleteFile(secApplication.getFilePath());
		}
		//保存文件到fds服务器
		String path=fastDFSService.uploadFile(file.getBytes());
		// 更新附件地址
		secApplication.setFilePath(path);
		secApplication.setStatus("BApprove"); // 重置申请状态为未提交
		secApplication.setUpdDate(new Date()); // 修改时间
		secApplication.setUpdUser(secApplication.getApplication().getStudent().getStudentNo()); // 修改人
		secApplicationDao.updateSecApplication(secApplication);
		return "提交成功！";
	}

	/**
	 * 查询变更申请的审批记录
	 * 
	 * @param secApplicationId
	 *            申请编号
	 * @return 返回该学生的所有实习申请的审批记录
	 * @throws Exception
	 */
	public List<SysReviewLog> getReviewLog(Integer secApplicationId) throws Exception {
		return reviewLogDao.findLog(RECORD_TYPE, secApplicationId);
	}

	/**
	 * 检查接受到的变更申请表字段是否符合要求
	 * 
	 * @param secApplication
	 * @return Boolean 符合要求返回true，否则抛出相应异常信息
	 * @throws Exception
	 */
	public Boolean checkForm(StudentSecApplication secApplication) throws Exception {
		if (null != secApplication) {
			if (null == secApplication.getNewCompany() || null == secApplication.getNewCompany().getCompanyId())
				throw new RuntimeException("请选择新实习单位！");
			else if (null == secApplication.getOldComStartDate() || null == secApplication.getOldComEndDate())
				throw new RuntimeException("请输入原实习开始时间和结束时间！");
			else if (null == secApplication.getNewComStartDate() || null == secApplication.getNewComEndDate())
				throw new RuntimeException("请输入新实习开始时间和结束时间！");
			else if (null == secApplication.getNewContent() || "".equals(secApplication.getNewContent()))
				throw new RuntimeException("请输入新实习内容！");
			else if (secApplication.getNewContent().length() > 50)
				throw new RuntimeException("新实习内容不能超过50个字！");
			else if (null == secApplication.getNewStation() || "".equals(secApplication.getNewStation()))
				throw new RuntimeException("请输入新实习岗位！");
			else if (secApplication.getNewStation().length() > 40)
				throw new RuntimeException("新实习岗位不能超过40个字！");
			else if (null == secApplication.getReason() || "".equals(secApplication.getReason()))
				throw new RuntimeException("请输入变更原因！");
			else if (secApplication.getReason().length() > 100)
				throw new RuntimeException("变更理由不能超过100个字！");
			else if (null == secApplication.getNewAdress() || "".equals(secApplication.getNewAdress()))
				throw new RuntimeException("请输入新住宿地址！");
			else if (secApplication.getNewAdress().length() > 80)
				throw new RuntimeException("新住宿地址不能超过80个字！");
		} else {
			throw new Exception("参数缺失：secApplication");
		}
		return true;
	}
}
