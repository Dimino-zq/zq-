package edu.hfu.train.action.student;

import java.util.HashMap;
import java.util.Iterator;
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
import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.StudentWorkRec;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.service.student.StudentApplicationService;
import edu.hfu.train.service.student.StudentRecordService;
import edu.hfu.train.service.student.StudentSecApplicationService;
import edu.hfu.train.util.FormatUtil;

@RestController
@RequestMapping(value = "/studentrec")
public class StudentRecordAction {
	private final Logger LOG = LoggerFactory.getLogger(StudentRecordAction.class);

	@Resource
	StudentRecordService recordService;
	
	@Resource
	StudentSecApplicationService secApplicationService;
	
	@Resource
	StudentApplicationService applicationService;

	@RequestMapping(value = "/gotostudentrecord", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentApplication(HttpSession session) {
		ModelAndView mod = null;
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		if (null == user)
			throw new NullPointerException("查询失败：未登录状态！");
		else if (!session.getAttribute("userType").equals("student"))
			throw new ClassCastException("查询失败：用户类型不匹配！");
		try {
			SysStudent student = (SysStudent) user;
			if (recordService.checkApplication((SysStudent) user))
			{
				mod = new ModelAndView("student/studentrecord.btl");
				mod.addObject("stuTrainStatus", student.getTrainStatus());
			}
		} catch (Exception e) {
			LOG.debug("{}",e.getMessage());
			mod = new ModelAndView("student/tip.btl");
			mod.addObject("tipURL", "/static/images/noApplication.jpg");
		}
		return mod;
	}
	
	

	@RequestMapping(value = "/getrecord",method = {RequestMethod.GET,RequestMethod.POST})
	public List<StudentRecord> getRecord(HttpSession session){
		try {
			//从session中获取当前用户
			Object user = session.getAttribute("user");
			if (null == user)
				throw new NullPointerException("查询失败：未登录状态！");
			else if(!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("查询失败：用户类型不匹配！");
			//查询当前学生的申请表
			List<StudentRecord> list = recordService.getRecord((SysStudent) user);
			return list;
		} catch (ClassCastException | NullPointerException e) {
			LOG.debug("{}",e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	//保存工作记录表--创建实习记录
	@RequestMapping(value = "/saverecord",method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String,String> saveRecord(StudentRecord record,StudentWorkRec workrec,HttpSession session)
	{
		Map<String,String> message = new HashMap<String, String>();
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("保存失败：未登录状态！");
			else if(!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("保存失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("保存失败：实训周期未开启！");
			else if(null == ((SysStudent) user).getStudentId())
				throw new Exception("保存失败：学生Id为空！");
			else if(null == workrec)
				throw new NullPointerException("保存失败：工作记录不存在！");
			else if(null == record || null == record.getComPost() || "".equals(record.getComPost()))
				throw new NullPointerException("保存失败：实习岗位信息不存在！");
			//在记录中绑定学生信息
			record.setStudent((SysStudent) user);
			workrec.setStudent((SysStudent) user);
			//保存实习记录
			if(recordService.checkForm(record,workrec))
				message.put("tip",recordService.saveRecord(record,workrec));
		} catch (ClassCastException | NullPointerException e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}
	
	//修改工作记录表--修改实习记录
	@RequestMapping(value = "/updaterecord",method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String,String> updateRecord(StudentRecord record,StudentWorkRec workrec,HttpSession session)
	{
		Map<String,String> message = new HashMap<String, String>();
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("修改失败：未登录状态！");
			else if(!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("修改失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("修改失败：实训周期未开启！");
			else if(null == ((SysStudent) user).getStudentId())
				throw new RuntimeException("修改失败：学生Id为空！");
			else if(null == record)
				throw new NullPointerException("修改失败：实习记录丢失，请重新提交！");
			if(null != record.getRecordId())
			{
				if(recordService.checkForm(record,workrec))
					message.put("tip",recordService.updateRecord(record ,workrec ,((SysStudent) user).getStudentId()));
			}
			else
				message.put("error","修改失败，请选中一行记录！");
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}


	//删除记录表
	@RequestMapping(value = "/deleteRecord",method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String,String> deleteRecord(Integer recordId, HttpSession session)
	{
		Map<String,String> message = new HashMap<String, String>();
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("删除失败：未登录状态！");
			else if(!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("删除失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("删除失败：实训周期未开启！");
			else if(null == ((SysStudent) user).getStudentId())
				throw new Exception("删除失败：学生Id为空！");
			if(recordId!=null)
				message.put("tip",recordService.deleteRecord(recordId,((SysStudent) user).getStudentId()));
			else
				message.put("error","删除失败，请选中申请表！");
		} catch (ClassCastException | NullPointerException e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error",e.getMessage());
		}
		return message;
	}
	
	//获取当前已通过实习（变更）申请中的实习岗位
	@RequestMapping(value = "/getlatestapplication",method = {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> getLatestApplication(HttpSession session)
	{
		Map<String,Object> message = new HashMap<String, Object>();
		try {
			//从session中获取当前用户
			Object user = session.getAttribute("user");
			if (null == user)
				throw new NullPointerException("查询失败：未登录状态！");
			else if(!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("查询失败：用户类型不匹配！");
			//获取实习岗位
			message.put("tip",recordService.getLatestApplication((SysStudent) user));
		} catch(Exception e){
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}
	
	//html查看总实习记录表 --只有学生能查询
	@RequestMapping(value = "/gotohtmltrainrecord",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView gotoHTMLApplication(HttpSession session)
	{
		ModelAndView mod = new ModelAndView("student/htmltrainrecord.btl");
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		//从session中获取当前用户类型
		Object userType = session.getAttribute("userType");
		try {
			if (null == user)
				throw new NullPointerException("无法查看：未登录状态！");
			else if(null==userType)
				throw new RuntimeException("无法查看：未知用户类型！");
			SysStudent student = (SysStudent) user;
			//学生基本信息
			mod.addObject("studentName",student.getStudentName());  //姓名
			mod.addObject("studentNo",student.getStudentNo());  //学号
			mod.addObject("studentSex",student.getStudentSex());  //性别
			mod.addObject("depart",student.getDepart());//系部
			mod.addObject("major",student.getMajor());//专业
			mod.addObject("studentClass",student.getClassNumber()+"班");//班级

			//根据学生查询自主实习申请
			List<StudentApplication> applicationList = applicationService.getApplication(student);
			if(null==applicationList || applicationList.size()==0 || !applicationList.get(0).getStatus().equals("DApprove") && !applicationList.get(0).getStatus().equals("EApprove"))
				throw new RuntimeException("无法查看：您从未开始过实习！");
			mod.addObject("comName1",applicationList.get(0).getCompany().getComName());//单位名称
			mod.addObject("comcontacts1",applicationList.get(0).getCompany().getComcontacts());//企业联系人
			
			//根据学生查询所有变更申请
			List<StudentSecApplication> secApplicationList = secApplicationService.getSecApplication((SysStudent) user);
			if(null==secApplicationList || secApplicationList.size()==0 || (secApplicationList.size()==1 && !secApplicationList.get(0).getStatus().equals("DApprove") && !secApplicationList.get(0).getStatus().equals("EApprove")))
			{
				mod.addObject("applyDate1",FormatUtil.formatDateToStr(applicationList.get(0).getApplydate(),"yyyy-MM-dd") + "至" + FormatUtil.formatDateToStr(applicationList.get(0).getEndDate(),"yyyy-MM-dd"));//实习起止时间
				mod.addObject("surcomContent",applicationList.get(0).getSurcomcontent());//实习项目名称
			}
			else
			{
				mod.addObject("applyDate1",FormatUtil.formatDateToStr(secApplicationList.get(0).getOldComStartDate(),"yyyy-MM-dd") + "至" + FormatUtil.formatDateToStr(secApplicationList.get(0).getOldComEndDate(),"yyyy-MM-dd"));//实习起止时间
				int count = 1;
				for(Iterator<StudentSecApplication> it = secApplicationList.iterator();it.hasNext();count++)
				{
					if(count==3)
						break;
					StudentSecApplication secApplication = it.next();
					if(secApplication.getStatus().equals("DApprove"))//审批通过状态为通过的申请为最新的申请，此时显示最新的实习项目名称
						mod.addObject("surcomContent",secApplication.getNewContent());//实习项目名称
					if(it.hasNext()) //若还有申请，实习起止时间实际是下一个变更申请的旧实习起止时间（包含实际结束原实习的时间）
						mod.addObject("applyDate"+(count+1),FormatUtil.formatDateToStr(secApplicationList.get(count).getOldComStartDate(),"yyyy-MM-dd") + "至" + FormatUtil.formatDateToStr(secApplicationList.get(count).getOldComEndDate(),"yyyy-MM-dd"));//实习起止时间
					else
						mod.addObject("applyDate"+(count+1),FormatUtil.formatDateToStr(secApplication.getNewComStartDate(),"yyyy-MM-dd") + "至" + FormatUtil.formatDateToStr(secApplication.getNewComEndDate(),"yyyy-MM-dd"));//实习起止时间
					mod.addObject("comName"+(count+1),secApplication.getNewCompany().getComName());  //实习单位名称
					mod.addObject("comcontacts"+(count+1),secApplication.getNewCompany().getComcontacts());  //实习单位名称
				}
			}
			//根据学生查询所有实习记录
			List<StudentRecord> recordList = recordService.getRecord(student);
			if(recordList!=null & recordList.size()!=0)
			{
				mod.addObject("weeks",recordList.size());
				for(int i=1; i<=recordList.size();i++)
				{
					StudentRecord record = recordList.get(i-1);
					mod.addObject("workDate"+i,FormatUtil.formatDateToStr(record.getStartDate(),"yyyy-MM-dd") + "至" + FormatUtil.formatDateToStr(record.getStartDate(),"yyyy-MM-dd"));
					mod.addObject("comPost"+i,record.getComPost());
					mod.addObject("content"+i,record.getContent());
					mod.addObject("compleStatus"+i,record.getCompleStatus());
					mod.addObject("guidStatus"+i,record.getGuidStatus());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mod.addObject("tip", e.getMessage());
		}
		return mod;
	}
}
