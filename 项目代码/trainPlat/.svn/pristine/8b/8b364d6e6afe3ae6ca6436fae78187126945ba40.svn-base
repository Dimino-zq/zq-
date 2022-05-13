package edu.hfu.train.service.student;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import edu.hfu.train.bean.SysReviewLog;
import edu.hfu.train.dao.sysset.ReviewLogDao;
import edu.hfu.train.service.sysset.FastDFSService;
import edu.hfu.train.util.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.dao.student.StudentApplicationDao;


@Service
@Transactional
public class StudentApplicationService {
	@Resource
	StudentApplicationDao applicationDao;

	@Resource
	ReviewLogDao reviewLogDao;
	
	@Autowired
	private FastDFSService fastDFSService;//上传

	private static final String RECORD_TYPE = "StudentApplication";		//审批记录类型

	/**
	 * 根据申请表的id查询
	 * @param applicationId
	 * @return StudentApplication 实习申请表对象
	 * @throws Exception
	 */
	public StudentApplication getApplicationById(Integer applicationId) throws Exception
	{
		return applicationDao.getApplicationById(applicationId);
	}
	
	/**
	 * 根据学生id查询
	 * @param student 学生对象
	 * @return List
	 * @throws Exception
	 */
	public List<StudentApplication> getApplication(SysStudent student) throws Exception
	{
		if (student.getStudentId()==null)
			throw new Exception("查询失败：学生Id为空！");
		return applicationDao.getApplication(student);
	}
	
    /**
     * 检查当前学生是否存在实习申请，存在返回true，不存在则返回false
     * @param student
     * @return Boolean
     * @throws Exception
     */
    public Boolean checkApplication(SysStudent student) throws Exception
    {
        if (student.getStudentId()==null)
            throw new Exception("查询失败：学生Id为空！");
        List<StudentApplication> ls = applicationDao.getApplication(student);
        if (ls==null || ls.size()==0)
            return false;
        return true;
    }
	
	/**
	 * 保存申请表，只允许存在一张实习申请表，否则不允许保存
	 * @param application 实习申请表对象
	 * @return String
	 * @throws Exception
	 */
	public String saveApplication(StudentApplication application) throws Exception
	{
		//获取当前学生的申请表
		List<StudentApplication> list = applicationDao.getApplication(application.getStudent());
		//若已存在申请则抛出异常
		if (null != list && 0 != list.size())
			throw new RuntimeException("保存失败：已创建申请表，请勿重复操作！");
		//设置申请表的状态
		application.setStatus("BApprove");	//已提交
		application.setCreateDate(new Date());	//创建时间
		application.setUpdDate(new Date());	//更新时间
		application.setCreateUser(application.getStudent().getStudentNo());	//创建人
		application.setUpdUser(application.getStudent().getStudentNo());	//修改人
		
		//保存申请表
		Integer applyId = applicationDao.saveApplication(application);
		//根据application中的detailId判断实习申请是否是直接申请岗位
		if(null!=application.getDetailId())
			applicationDao.addActualStu(application.getDetailId());

		//创建记录，原状态为新建
		reviewLogDao.addReviewLog(RECORD_TYPE,applyId,"创建","AApprove",application.getStatus(),"实习申请：创建成功！");
		return "保存成功";
	}

	/**
	 * 根据申请表的id删除，不允许删除状态为“已提交”、“已通过”和“作废”的申请表
	 * @param applicationId 申请表Id
	 * @param studentId 学生Id
	 * @return String
	 * @throws Exception
	 */
	public String deleteApplication(Integer applicationId, Integer studentId)  throws Exception
	{
		StudentApplication studentApplication = getApplicationById(applicationId);
		if(null == studentApplication)
			throw new NullPointerException("删除失败：无效的申请表Id");
		//判断当前用户的操作是否合法
		if(!studentApplication.getStudent().getStudentId().equals(studentId))
			throw new Exception("删除失败：信息不匹配，非法操作！");
		//获取申请表状态
		String status = studentApplication.getStatus();
		//判断申请表的状态
		if(status.equals("CApprove"))	//已提交
			throw new RuntimeException("删除失败：该申请已提交，请耐心等待审批！");
		else if(status.equals("DApprove"))	//审批通过
			throw new RuntimeException("删除失败：该申请已通过！");
		else if(status.equals("EApprove"))	//作废
			throw new RuntimeException("删除失败：该申请已作废！");
		
		if (null!=studentApplication.getFilePath()) {//删除旧文件
			fastDFSService.deleteFile(studentApplication.getFilePath());
		}
		if (null!=studentApplication.getAppraisalFromFirmPath()) {//删除旧文件
			fastDFSService.deleteFile(studentApplication.getAppraisalFromFirmPath());
		}
		//删除该申请表
		applicationDao.deleteApplication(studentApplication);
		if(null!=studentApplication.getDetailId())
			applicationDao.subActualStu(studentApplication.getDetailId());
		return "删除成功！";
	}

	/**
	 * 更新“未提交”和“未通过”状态的申请表数据，不允许修改状态为“已提交”、“已通过”和“作废”的申请表
	 * @param application 修改后的申请表实例
	 * @param studentId 学生Id
	 * @return String
	 * @throws Exception
	 */
	public String updateApplication(StudentApplication application, Integer studentId) throws Exception
	{
		//获取原申请表实例
		StudentApplication studentApplication = getApplicationById(application.getApplyId());
		//若查询不到原申请表记录则抛出异常
		if (null == studentApplication)
			throw new NullPointerException("修改失败：未查找到该申请记录！");
		//判断当前用户的操作是否合法
		if (!studentApplication.getStudent().getStudentId().equals(studentId))
			throw new Exception("修改失败：信息不匹配，非法操作！");
		//获取申请表状态
		String status = studentApplication.getStatus();
		//判断申请表的状态
		if (status.equals("CApprove"))	//已提交
			throw new RuntimeException("修改失败：该申请已提交，请耐心等待审批！");
		else if (status.equals("DApprove"))	//审批通过
			throw new RuntimeException("修改失败：该申请已通过！");
		else if(status.equals("EApprove"))	//作废
			throw new RuntimeException("修改失败：该申请已作废！");
		//将持久化的原申请表替换新的申请信息
		studentApplication.setCompany(application.getCompany());
		studentApplication.setAdress(application.getAdress());
		studentApplication.setApplydate(application.getApplydate());
		studentApplication.setEndDate(application.getEndDate());
		studentApplication.setInsurance(application.getInsurance());
		studentApplication.setPhoneOrQQ(application.getPhoneOrQQ());
		studentApplication.setSurcomcontent(application.getSurcomcontent());
		studentApplication.setSurcomjob(application.getSurcomjob());
		studentApplication.setTeacherName(application.getTeacherName());
		studentApplication.setTeacherNo(application.getTeacherNo());
		studentApplication.setStatus("BApprove");	//修改申请后申请状态重置为“未提交”
		studentApplication.setUpdDate(new Date());	//修改时间
		studentApplication.setUpdUser(studentApplication.getStudent().getStudentNo());	//修改人
		applicationDao.updateApplication(studentApplication);
		return "修改成功！";
	}
	
	/**
	 * 将“未提交”申请表的状态修改为“已提交”状态，仅允许提交“未提交”的申请表
	 * @param applicationId  申请表Id
	 * @param studentId 学生Id
	 * @return String
	 * @throws Exception
	 */
	public String submitApplication(Integer applicationId, Integer studentId) throws Exception
	{
		//获取原申请表实例
		StudentApplication studentApplication = getApplicationById(applicationId);
		//若查询不到原申请表记录则抛出异常
		if (null == studentApplication)
			throw new NullPointerException("提交失败：未查找到该申请记录！");
		//判断当前用户的操作是否合法
		if (!studentApplication.getStudent().getStudentId().equals(studentId))
			throw new Exception("提交失败：信息不匹配，非法操作！");
		//检查是否已经上传附件
		if (null==studentApplication.getFilePath() || studentApplication.getFilePath().equals(""))
			throw new Exception("提交失败：未上传附件！");
		//获取申请表状态
		String status = studentApplication.getStatus();
		//判断申请表的状态
		if (status.equals("CApprove"))	//已提交
			throw new RuntimeException("提交失败：该申请已提交，请勿重复操作！");
		else if (status.equals("DApprove"))	//审批通过
			throw new RuntimeException("提交失败：该申请已通过！");
		else if(status.equals("EApprove"))	//作废
			throw new RuntimeException("提交失败：该申请已作废！");
		else if (status.equals("FApprove"))	//未通过
			throw new RuntimeException("提交失败：请修改申请后再提交！");
		//将持久化的原申请表替换新的申请信息
		studentApplication.setStatus("CApprove");	//已提交
		studentApplication.setUpdDate(new Date());	//修改时间
		studentApplication.setUpdUser(studentApplication.getStudent().getStudentNo());	//修改人
		applicationDao.updateApplication(studentApplication);
		//创建记录
		reviewLogDao.addReviewLog(RECORD_TYPE,studentApplication.getApplyId(),"提交",status,studentApplication.getStatus(),"实习申请：提交成功！");
		return "提交成功！";
	}
	
	/**
	 * 保存附件并将附件地址保存至数据库，申请表状态为“已提交”、“已通过”和“作废”时不允许操作
	 * @param applicationId 申请表Id
	 * @param studentId 学生Id
	 * @param file 接收到的文件
	 * @param savepath 附件保存路径
	 * @return
	 * @throws Exception
	 */
	public String upLoadFile(Integer applicationId, Integer studentId, MultipartFile file) throws Exception
	{
		//获取原申请表实例
		StudentApplication studentApplication = getApplicationById(applicationId);
		//若查询不到原申请表记录则抛出异常
		if (null == studentApplication)
			throw new NullPointerException("提交失败：未查找到该申请记录！");
		//判断当前用户的操作是否合法
		if (!studentApplication.getStudent().getStudentId().equals(studentId))
			throw new Exception("提交失败：信息不匹配，非法操作！");
		//获取申请表状态
		String status = studentApplication.getStatus();
		//判断申请表的状态
		if (status.equals("CApprove"))	//已提交
			throw new RuntimeException("提交失败：该申请已提交，请勿重复操作！");
		else if (status.equals("DApprove"))	//审批通过
			throw new RuntimeException("提交失败：该申请已通过！");
		else if(status.equals("EApprove"))	//作废
			throw new RuntimeException("提交失败：该申请已作废！");
		if (null!=studentApplication.getFilePath()) {//删除旧文件
			fastDFSService.deleteFile(studentApplication.getFilePath());
		}
		
		//保存文件到fds服务器
		String path=fastDFSService.uploadFile(file.getBytes());
		//更新附件地址
		studentApplication.setFilePath(path);
		
		studentApplication.setStatus("BApprove");	//重置申请状态为未提交
		studentApplication.setUpdDate(new Date());	//修改时间
		studentApplication.setUpdUser(studentApplication.getStudent().getStudentNo());	//修改人
		applicationDao.updateApplication(studentApplication);
		return "提交成功！";
	}
	
	/**
	 * 保存鉴定表附件并将附件地址保存至数据库
	 * @param applicationId 申请表Id
	 * @param studentId 学生Id
	 * @param file 接收到的文件
	 * @param savepath 附件保存路径
	 * @return
	 * @throws Exception
	 */
	public String uploadAppraisalFromFirm(Integer applicationId, Integer studentId, MultipartFile file) throws Exception
	{
		//获取原申请表实例
		StudentApplication studentApplication = getApplicationById(applicationId);
		//若查询不到原申请表记录则抛出异常
		if (null == studentApplication)
			throw new NullPointerException("提交失败：未查找到该申请记录！");
		//判断当前用户的操作是否合法
		if (!studentApplication.getStudent().getStudentId().equals(studentId))
			throw new Exception("提交失败：信息不匹配，非法操作！");
		
		if (null!=studentApplication.getAppraisalFromFirmPath()) {//删除旧文件
			fastDFSService.deleteFile(studentApplication.getAppraisalFromFirmPath());
		}
		//保存文件到fds服务器
		String path=fastDFSService.uploadFile(file.getBytes());
		//更新附件地址
		studentApplication.setAppraisalFromFirmPath(path);
		studentApplication.setUpdDate(new Date());	//修改时间
		studentApplication.setUpdUser(studentApplication.getStudent().getStudentNo());	//修改人
		applicationDao.updateApplication(studentApplication);
		return "提交成功！";
	}

	/**
	 * 查询实习申请的审批记录
	 * @param applicationId 申请编号
	 * @return 返回该学生的所有实习申请的审批记录
	 * @throws Exception
	 */
	public List<SysReviewLog> getReviewLog(Integer applicationId) throws Exception
	{
		return reviewLogDao.findLog(RECORD_TYPE,applicationId);
	}
	
	/**
	 * 检查接受到的申请表字段是否符合要求
	 * @param application
	 * @return Boolean 符合要求返回true，否则抛出相应异常信息
	 * @throws Exception
	 */
	public Boolean checkForm(StudentApplication application) throws Exception
	{
		if(null !=application)
		{
			if(null == application.getCompany() || null == application.getCompany().getCompanyId())
				throw new RuntimeException("请选择实习单位！");
			else if(null == application.getInsurance() || "".equals(application.getInsurance()))
				throw new RuntimeException("请选择保险情况！");
			else if(null == application.getTeacherNo() || "".equals(application.getTeacherNo()))
				throw new RuntimeException("请选择指导教师！");
			else if(null == application.getSurcomjob() || "".equals(application.getSurcomjob()))
				throw new RuntimeException("请输入实习岗位！");
			else if(application.getSurcomjob().length()>50)
				throw new RuntimeException("实习岗位不能超过50个字！");
			else if(null == application.getSurcomcontent() || "".equals(application.getSurcomcontent()))
				throw new RuntimeException("请输入实习内容！");
			else if(application.getSurcomcontent().length()>100)
				throw new RuntimeException("实习内容不能超过100个字！");
			else if(null == application.getAdress() || "".equals(application.getAdress()))
				throw new RuntimeException("请输入实习住宿地址！");
			else if(application.getAdress().length()>80)
				throw new RuntimeException("实习住宿地址不能超过80个字！");
			else if(null == application.getPhoneOrQQ() || "".equals(application.getPhoneOrQQ()))
				throw new RuntimeException("请输入联系方式！");
			else if(application.getPhoneOrQQ().length()>20)
				throw new RuntimeException("联系方式不能超过20个字！");
			else if(null == application.getApplydate() || null == application.getEndDate())
				throw new RuntimeException("请输入实习开始时间和结束时间！");
		}
		else
		{
			throw new Exception("参数缺失：application");
		}
		return true;
	}
}
