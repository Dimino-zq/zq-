package edu.hfu.train.action.teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.service.student.StudentApplicationService;
import edu.hfu.train.service.sysset.DictionaryService;
import edu.hfu.train.service.teacher.TeacherSecApplicationService;
import edu.hfu.train.util.FormatUtil;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherSecApplicationAction {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource
	TeacherSecApplicationService teacherSecApplicationService;
	@Resource
	StudentApplicationService studentApplicationService;
	@Resource
	DictionaryService dictionaryService;

	@RequestMapping(value = "/gototeachersec", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentSecApplication() {
		ModelAndView mod = new ModelAndView("/teacher/secapplication.btl");
		return mod;
	}

	@RequestMapping(value = "/studentSecform", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentformApplication() {
		ModelAndView mod1 = new ModelAndView("/teacher/internapply.btl");
		return mod1;
	}

	@RequestMapping(value = "/getAuditSec", method = { RequestMethod.GET, RequestMethod.POST })
	public List<StudentSecApplication> getAudit(String studentNo, String status, String teachermeaning) {
		List<StudentSecApplication> ls = new ArrayList<StudentSecApplication>();
		try {
			ls = ((TeacherSecApplicationService) teacherSecApplicationService).getAudit(studentNo, status);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Exception:", e);
		}
		return ls;
	}

	@RequestMapping(value = "/getStudentSecByCon", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getStudentSecByCon(HttpSession session, StudentSecApplication stuSecApp, SysStudent stu,
			int page, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<StudentSecApplication> ls1 = new ArrayList<StudentSecApplication>();
		Integer count = 0;
		try {
			System.out.println(session.getAttribute("userCode").toString());
			String a = session.getAttribute("userCode").toString();
			SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "action:" + auth.toString());
				LOG.debug("{}", "enroll:" + a);
				LOG.debug("{}", "enrolldepary:" + userBackStaff.getStaffParentDepart());
			}

			ls1 = teacherSecApplicationService.getStudentSecByCon(auth, a, userBackStaff, stuSecApp, stu, page, rows);
			count = teacherSecApplicationService.getSecApplicationCount(auth, a, userBackStaff, stuSecApp, stu);
			map.put("rows", ls1);
			map.put("total", count);
		} catch (Exception e) {
			LOG.error("Exception:", e);
			map.put("total", 0);
			map.put("rows", null);
		}
		return map;
	}

	@RequestMapping(value = "/passTeacherSec", method = { RequestMethod.GET, RequestMethod.POST })
	public String passTeacher(HttpSession session, StudentSecApplication stuSecApp, String applyId) {
		String mess = "";
		String a = session.getAttribute("userCode").toString();
		SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String passWay = "teacher";
		try {
			teacherSecApplicationService.passTeacherSec(passWay, auth, a, userBackStaff, stuSecApp, applyId);
			mess = "succ";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/refuseTeacherSec", method = { RequestMethod.GET, RequestMethod.POST })
	public String refuseTeacher(HttpSession session, StudentSecApplication stuSecApp, String applyId) {
		String mess = "";
		String a = session.getAttribute("userCode").toString();
		SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String passWay = "teacher";
		try {
			teacherSecApplicationService.refuseTeacherSec(passWay, auth, userBackStaff, stuSecApp, applyId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "DeptView:" + stuSecApp.getDeptView());
			}
			mess = "succ";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/passDeptSec", method = { RequestMethod.GET, RequestMethod.POST })
	public String passDept(HttpSession session, StudentSecApplication stuSecApp, String applyId) {
		String mess = "";
		String a = session.getAttribute("userCode").toString();
		SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String passWay = "dept";
		try {
			teacherSecApplicationService.passTeacherSec(passWay, auth, a, userBackStaff, stuSecApp, applyId);

			mess = "succ";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/refuseDeptSec", method = { RequestMethod.GET, RequestMethod.POST })
	public String refuseDept(HttpSession session, StudentSecApplication stuSecApp, String applyId) {
		String mess = "";
		String a = session.getAttribute("userCode").toString();
		SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String passWay = "dept";
		try {
			teacherSecApplicationService.refuseTeacherSec(passWay, auth, userBackStaff, stuSecApp, applyId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "DeptView:" + stuSecApp.getDeptView());
			}
			mess = "succ";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	// html查看变更申请表 --学生和教师通用方法，通过session中的useType判断用户类型
	@RequestMapping(value = "/gotohtmlapplication2", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoHTMLApplication(Integer secApplicationId, HttpSession session) {
		ModelAndView mod = new ModelAndView("teacher/chgapplication.btl");
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前用户类型
		Object userType = session.getAttribute("userType");
		try {
			if (null == user)
				throw new NullPointerException("无法查看：未登录状态！");
			else if (null == userType)
				throw new NullPointerException("无法查看：未知用户类型！");
			else if (null == secApplicationId)
				throw new NullPointerException("无法查看：请选中申请表！");
			// 查询申请表Id对应的申请表
			StudentSecApplication secApplication = teacherSecApplicationService.getSecApplicationById(secApplicationId);
			if (null == secApplication)
				throw new NullPointerException("无法查看：未查找到该申请记录！"); // 不存在该申请记录
			else {
				StudentApplication application = secApplication.getApplication();
				if (userType.equals("student")) {
					// 检查查当前的学生是否为申请表中记录的学生
					if (!((SysStudent) user).getStudentId().equals(application.getStudent().getStudentId()))
						throw new Exception("无法查看：非法操作");
				} else if (userType.equals("teacher")) {
					SysStaff thisStaff = (SysStaff) session.getAttribute("user");
					if (LOG.isDebugEnabled()) {
						LOG.debug("{}", "UserCode:" + ((SysStaff) user).getUserCode());
						LOG.debug("{}", "TeacherNo:" + application.getTeacherNo());

					}

					// 获取从申请表中获取学生信息
					SysStudent student = application.getStudent();
					// btl替换数据
					mod.addObject("enroll", ((SysStaff) user).getUserCode()); // 登陆者标识
					mod.addObject("applyId", application.getApplyId());
					mod.addObject("secApplyId", secApplicationId);
					mod.addObject("studentName", student.getStudentName()); // 姓名
					mod.addObject("studentNo", student.getStudentNo()); // 性别
					mod.addObject("major", student.getMajor()); // 专业
					mod.addObject("classNumber", student.getClassNumber() + "班"); // 班级
					if(null!=secApplication.getFilePath()) {
						mod.addObject("filePath", "/getRemoteImgFile?filePath="+secApplication.getFilePath()); // 文件
					}else {
						mod.addObject("filePath", "");
					}
					mod.addObject("oldCompany", secApplication.getOldComName()); // 原实习单位
					if (null == secApplication.getOldComStartDate() || null == secApplication.getOldComEndDate()) {
						throw new NullPointerException("原实习开始时间或结束时间为空");
					} else {
						mod.addObject("oldDate",
								FormatUtil.formatDateToStr(secApplication.getOldComStartDate(), "yyyy-MM-dd") + " 至 "
										+ FormatUtil.formatDateToStr(secApplication.getOldComEndDate(), "yyyy-MM-dd")); // 原实习起止时间}
					}

					mod.addObject("newCompany", secApplication.getNewCompany().getComName()); // 原实习单位
					if (null == secApplication.getNewComStartDate() || null == secApplication.getNewComEndDate()) {
						throw new NullPointerException("新实习开始时间或结束时间为空");
					} else {
						mod.addObject("newDate",
								FormatUtil.formatDateToStr(secApplication.getNewComStartDate(), "yyyy-MM-dd") + " 至 "
										+ FormatUtil.formatDateToStr(secApplication.getNewComEndDate(), "yyyy-MM-dd")); // 原实习起止时间
					}

					mod.addObject("reason", secApplication.getReason()); // 变更原因
					mod.addObject("teacherNo", application.getTeacherNo()); // 指导老师工号
					mod.addObject("teacherName", secApplication.getTeacherName()); // 指导老师名字
					mod.addObject("teacherView", secApplication.getTeacherView()); // 指导老师意见
					if (null == secApplication.getTeacherViewDate() || null == secApplication.getTeacherViewDate()) {
						mod.addObject("teacherViewDate",
								" 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 月&nbsp;&nbsp;&nbsp;&nbsp; 日");

					} else {
						mod.addObject("teacherViewDate",
								FormatUtil.formatDateToStr(secApplication.getTeacherViewDate(), "yyyy年MM月dd日"));
					}
					// 指导老师时间
					mod.addObject("adviserName", secApplication.getAdviserName()); // 辅导员名字
					mod.addObject("adviserView", secApplication.getAdviserView()); // 辅导员意见

					if (secApplication.getAdviserViewDate() == null) {
						mod.addObject("adviserViewDate",
								" 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 月&nbsp;&nbsp;&nbsp;&nbsp; 日");
					} else {
						mod.addObject("adviserViewDate",
								FormatUtil.formatDateToStr(secApplication.getAdviserViewDate(), "yyyy年MM月dd日")); // 辅导员时间
					}

					mod.addObject("deptName", secApplication.getDeptName()); // 系（部）主任名字
					mod.addObject("departView", secApplication.getDeptView()); // 系（部）主任意见
					if (null == secApplication.getDeptViewDate() || null == secApplication.getDeptViewDate()) {
						mod.addObject("deptViewDate", " 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 月&nbsp;&nbsp;&nbsp;&nbsp; 日");

					} else {
						mod.addObject("deptViewDate",
								FormatUtil.formatDateToStr(secApplication.getDeptViewDate(), "yyyy年MM月dd日")); // 系（部）主任时间
					}
					mod.addObject("staff", thisStaff.getPoststr());

				} else
					throw new Exception("无法查看：非法操作"); // 未知用户类型

			}
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mod.addObject("tip", e.getMessage());
		}
		return mod;
	}

}
