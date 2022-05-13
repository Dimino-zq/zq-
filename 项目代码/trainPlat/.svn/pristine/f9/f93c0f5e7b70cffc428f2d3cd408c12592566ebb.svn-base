package edu.hfu.train.action.teacher;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentInterReport;
import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.SysTrainCycle;
import edu.hfu.train.service.sysset.SysTrainCycleService;
import edu.hfu.train.service.teacher.TeacherInformationService;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherInformationAction {
	private final Logger LOG = LoggerFactory.getLogger(TeacherInformationAction.class);
	
	@Resource
	SysTrainCycleService trainCycleService;
	
	@Resource
	TeacherInformationService teacherInformationService;
	
	@RequestMapping(value = "/gototeacherinformation", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentApplication(HttpSession session) {
		ModelAndView mod = new ModelAndView("teacher/teacherInformation.btl");
		//获取当前登录的用户信息
		Object user = session.getAttribute("user");
		//获取当前周期
		Object cycle = session.getAttribute("cycleCologe");
		try {
			if(null == user)
				throw new RuntimeException("未登录状态！");
			else if(null == cycle)
				return mod;		//当周期不存在时不再查询指导学生的信息
			//获取当前进行中的实训周期
			SysTrainCycle trainCycle = (SysTrainCycle) cycle;
//			SysTrainCycle trainCycle = new SysTrainCycle();
//			trainCycle.setStatus("进行中");
//			trainCycle = trainCycleService.getSysTrainCycleByCon(trainCycle).get(0);
			if(null!=trainCycle)
				mod.addObject("semester",trainCycle.getStartschoolyear()+"-"+trainCycle.getEndschoolyear());
			
			//获取当前学期当前教师指导的学生的学生申请
			SysStaff teacher = (SysStaff) user;
			List<StudentApplication> list_application = teacherInformationService.getApplicationByTeacher(teacher.getUserCode(),trainCycle.getStartschoolyear());
			mod.addObject("studentNum",list_application.size());
			
			int applicationNum = 0;	//已通过申请数
			int unCheckedApplicationNum = 0;	//未审批（已提交或未通过）申请数
			int unCheckedSecApplicationNum = 0;		//未审批（未提交或通过）的变更申请数
			int unCheckedRecordNum = 0;	//未查阅的周工作记录数
			int unCheckedInterReportNum = 0;	//未查阅的实习报告数
			int trainCompleteNum = 0;	//完成实习学生数
			
			//循环遍历所有申请
			for(StudentApplication application : list_application)
			{
				if("DApprove".equals(application.getStatus()) || "EApprove".equals(application.getStatus()))
				{
					//查询周工作记录情况
					List<StudentRecord> stuRecords = teacherInformationService.getStuRecordByStudentId(application.getStudent().getStudentId());
					for(StudentRecord record : stuRecords)
					{
						if(null == record.getGuidStatus())
							unCheckedRecordNum++;
					}
					//查询实习报告情况
					List<StudentInterReport> stuInterReport = teacherInformationService.getStuInterReportByApplicationId(application.getApplyId());
					for(StudentInterReport interReport : stuInterReport)
					{
						if(null!=interReport.getStatus() && !"".equals(interReport.getStatus()))
							trainCompleteNum++;
						else
							unCheckedInterReportNum++;
					}
					//查询变更申请情况
					List<StudentSecApplication> stuSecApplication = teacherInformationService.getStuSecApplicationBy(application.getApplyId());
					for(StudentSecApplication secApplication : stuSecApplication)
					{
						if("CApprove".equals(secApplication.getStatus()))
							unCheckedSecApplicationNum++;
					}
					
					applicationNum++;
				}
				else if("CApprove".equals(application.getStatus()))
				{
					unCheckedApplicationNum++;
				}
			}
			mod.addObject("applicationNum",applicationNum);
			mod.addObject("trainCompleteNum",trainCompleteNum);
			//消息
			String message = "";
			if(unCheckedApplicationNum != 0)
				message+=(unCheckedApplicationNum+"个实习申请");
			if(unCheckedSecApplicationNum != 0)
			{
				if(!"".equals(message))
					message+="，";
				message+=(unCheckedSecApplicationNum+"个变更申请");
			}
			if(unCheckedRecordNum != 0)
			{
				if(!"".equals(message))
					message+="，";
				message+=(unCheckedRecordNum+"个实习周工作记录");
			}
			if(unCheckedInterReportNum != 0)
			{
				if(!"".equals(message))
					message+="，";
				message+=(unCheckedInterReportNum+"个实习报告书");
			}
			if("".equals(message))
				message = null;
			else
			{
				message = "您当前有"+message+"待审批!";
			}
			
			mod.addObject("message",message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mod;
	}
	
	
	@RequestMapping(value = "/getStuTrainInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getStuTrainInfo(HttpSession session, SysStudent student) {
		Map<String, Object> map = new HashMap<String, Object>();
		//获取当前登录的用户信息
		Object user = session.getAttribute("user");
		//获取当前周期
		Object cycle = session.getAttribute("cycleCologe");
		try {
			if(null == user)
				throw new RuntimeException("未登录状态！");
			else if(null == cycle)
				throw new RuntimeException("实训周期未开启！");		//当周期不存在时不再查询指导学生的信息
			else if (!session.getAttribute("userType").equals("teacher"))
				throw new RuntimeException("查询失败：用户类型不匹配！");
			List<Map<String, Object>> ls = teacherInformationService.getStuTrainInfo(((SysStaff) user).getUserCode(), ((SysTrainCycle) cycle).getStartschoolyear(), student);
			map.put("rows", ls);
		}catch (RuntimeException e) {
			LOG.debug("{}","用户 "+((SysStaff) user).getUserCode()+" 操作失败："+e.getMessage());
			map.put("rows", new ArrayList<>());
			map.put("error", e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			map.put("rows", new ArrayList<>());
			map.put("error", "未知错误！");
		}
		return map;
	}
	

}
