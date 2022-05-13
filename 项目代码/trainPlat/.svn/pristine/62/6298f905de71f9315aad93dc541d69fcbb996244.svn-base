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
import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.bean.SysDepartTrainCycle;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.service.student.StudentApplicationService;
import edu.hfu.train.service.sysset.DictionaryService;
import edu.hfu.train.service.teacher.TeacherApplicationService;
import edu.hfu.train.util.FormatUtil;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherApplicationAction {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource
	TeacherApplicationService teacherApplicationService;
	@Resource
	StudentApplicationService studentApplicationService;
	@Resource
	DictionaryService dictionaryService;

	/**
	 * 跳转老师审批申请表界面
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/gototeacher", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentApplication() {
		ModelAndView mod = new ModelAndView("/teacher/teacherapplication.btl");
		return mod;
	}

	@RequestMapping(value = "/studentform", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentformApplication() {
		ModelAndView mod1 = new ModelAndView("/teacher/internapply.btl");
		return mod1;
	}

	/**
	 * 查询
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getStudentByCon", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getStudentByCon(HttpSession session, SysStudent stu, StudentApplication stuApp, int page,
			int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<StudentApplication> ls1 = new ArrayList<StudentApplication>();
		try {
			ls1 = teacherApplicationService.getStudentByCon(session.getAttribute("userCode").toString(), stu, stuApp,
					page, rows);
			Integer count = teacherApplicationService.getApplicationCount(session.getAttribute("userCode").toString(),
					stu, stuApp);
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "stu.toString:" + stu.toString());
				LOG.debug("{}", "ls1.toString:" + ls1.toString());
			}
			map.put("rows", ls1);
			map.put("total", count);
		} catch (Exception e) {
			LOG.error("Exception:", e);
			map.put("total", 0);
			map.put("rows", null);
		}
		return map;
	}

	/**
	 * 将待审批改为已通过
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/passTeacher", method = { RequestMethod.GET, RequestMethod.POST })
	public String passTeacher(StudentApplication teacher) {
		String mess = "";
		try {
			teacherApplicationService.passTeacher(teacher);
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "teacher.getApplyId:" + teacher.getApplyId());
			}
			mess = "succ";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	/**
	 * 将待审批改为未通过
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/refuseTeacher", method = { RequestMethod.GET, RequestMethod.POST })
	public String refuseTeacher(StudentApplication teacher) {
		String mess = "";
		try {
			teacherApplicationService.refuseTeacher(teacher);
			mess = "succ";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	/**
	 * html查看申请表
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/gotinternapply", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoInternapply(Integer applicationId, HttpSession session) {
		ModelAndView mod = new ModelAndView("teacher/internapply.btl");
		String userType = (String) session.getAttribute("userType");
		Object user = session.getAttribute("user");
		StudentApplication application = null;
		if (applicationId != null) {
			if (user != null) {
				try {
					application = studentApplicationService.getApplicationById(applicationId);
				} catch (Exception e) {
					mod.addObject("tip", e.toString());
					if (LOG.isDebugEnabled()) {
						LOG.debug("{}", "查询申请表数据失败：" + e.toString());
					}

				}

				if (application != null) {
					if (userType.equals("student")) {
						if (!((SysStudent) user).getStudentId().equals(application.getStudent().getStudentId())) {
							mod.addObject("tip", "非法操作！");
							if (LOG.isDebugEnabled()) {
								LOG.debug("{}", "学生id不匹配");
							}
							return mod;
						}
					}
					// 获取从申请表中获取学生信息
					SysStudent student = application.getStudent();
					// 获取从申请表中获取实训周期信息
					SysDepartTrainCycle trainCycle = application.getTrainCycle();
					// 获取从申请表中获取实习单位信息
					SysCompany company = application.getCompany();
					// btl替换数据
					mod.addObject("trainCycle", trainCycle.getSysTrainCycle().getStartschoolyear() + "-"
							+ trainCycle.getSysTrainCycle().getEndschoolyear()); // 学年度
					mod.addObject("semester", trainCycle.getSysTrainCycle().getSemester()); // 学期
					mod.addObject("depart", student.getDepart()); // 系别
					mod.addObject("studentName", student.getStudentName()); // 姓名
					mod.addObject("studentSex", student.getStudentSex()); // 性别
					mod.addObject("studentClass", student.getMajor() + student.getClassNumber() + "班"); // 专业班级
					mod.addObject("insurance", application.getInsurance()); // 保险情况
					mod.addObject("phoneOrQQ", application.getPhoneOrQQ()); // 联系方式
					mod.addObject("applyDate", FormatUtil.formatDateToStr(application.getApplydate(), "yyyy-MM-dd")
							+ "至" + FormatUtil.formatDateToStr(application.getEndDate(), "yyyy-MM-dd")); // 实习起止时间
					mod.addObject("teacherName", application.getTeacherName()); // 校内指导老师
					mod.addObject("comName", company.getComName()); // 单位名称
					mod.addObject("comAddress", company.getComAddress()); // 单位地址
					mod.addObject("industry", company.getIndustry()); // 所属行业
					mod.addObject("surcomJob", application.getSurcomjob()); // 实习岗位
					mod.addObject("comContacts", company.getComcontacts()); // 企业联系人
					mod.addObject("phone", company.getPhone()); // 联系电话
					// 企业指导老师 //联系电话
					mod.addObject("surcomContent", application.getSurcomcontent()); // 实习项目内容
				} else {
					mod.addObject("tip", "无效数据！");
				}
			} else
				mod.addObject("tip", "未知用户！");
		} else
			mod.addObject("tip", "未选中申请表！");
		return mod;
	}

}
