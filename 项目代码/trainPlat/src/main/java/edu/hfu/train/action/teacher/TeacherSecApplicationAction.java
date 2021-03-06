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

	// html????????????????????? --????????????????????????????????????session??????useType??????????????????
	@RequestMapping(value = "/gotohtmlapplication2", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoHTMLApplication(Integer secApplicationId, HttpSession session) {
		ModelAndView mod = new ModelAndView("teacher/chgapplication.btl");
		// ???session?????????????????????
		Object user = session.getAttribute("user");
		// ???session???????????????????????????
		Object userType = session.getAttribute("userType");
		try {
			if (null == user)
				throw new NullPointerException("?????????????????????????????????");
			else if (null == userType)
				throw new NullPointerException("????????????????????????????????????");
			else if (null == secApplicationId)
				throw new NullPointerException("????????????????????????????????????");
			// ???????????????Id??????????????????
			StudentSecApplication secApplication = teacherSecApplicationService.getSecApplicationById(secApplicationId);
			if (null == secApplication)
				throw new NullPointerException("?????????????????????????????????????????????"); // ????????????????????????
			else {
				StudentApplication application = secApplication.getApplication();
				if (userType.equals("student")) {
					// ????????????????????????????????????????????????????????????
					if (!((SysStudent) user).getStudentId().equals(application.getStudent().getStudentId()))
						throw new Exception("???????????????????????????");
				} else if (userType.equals("teacher")) {
					SysStaff thisStaff = (SysStaff) session.getAttribute("user");
					if (LOG.isDebugEnabled()) {
						LOG.debug("{}", "UserCode:" + ((SysStaff) user).getUserCode());
						LOG.debug("{}", "TeacherNo:" + application.getTeacherNo());

					}

					// ???????????????????????????????????????
					SysStudent student = application.getStudent();
					// btl????????????
					mod.addObject("enroll", ((SysStaff) user).getUserCode()); // ???????????????
					mod.addObject("applyId", application.getApplyId());
					mod.addObject("secApplyId", secApplicationId);
					mod.addObject("studentName", student.getStudentName()); // ??????
					mod.addObject("studentNo", student.getStudentNo()); // ??????
					mod.addObject("major", student.getMajor()); // ??????
					mod.addObject("classNumber", student.getClassNumber() + "???"); // ??????
					if(null!=secApplication.getFilePath()) {
						mod.addObject("filePath", "/getRemoteImgFile?filePath="+secApplication.getFilePath()); // ??????
					}else {
						mod.addObject("filePath", "");
					}
					mod.addObject("oldCompany", secApplication.getOldComName()); // ???????????????
					if (null == secApplication.getOldComStartDate() || null == secApplication.getOldComEndDate()) {
						throw new NullPointerException("??????????????????????????????????????????");
					} else {
						mod.addObject("oldDate",
								FormatUtil.formatDateToStr(secApplication.getOldComStartDate(), "yyyy-MM-dd") + " ??? "
										+ FormatUtil.formatDateToStr(secApplication.getOldComEndDate(), "yyyy-MM-dd")); // ?????????????????????}
					}

					mod.addObject("newCompany", secApplication.getNewCompany().getComName()); // ???????????????
					if (null == secApplication.getNewComStartDate() || null == secApplication.getNewComEndDate()) {
						throw new NullPointerException("??????????????????????????????????????????");
					} else {
						mod.addObject("newDate",
								FormatUtil.formatDateToStr(secApplication.getNewComStartDate(), "yyyy-MM-dd") + " ??? "
										+ FormatUtil.formatDateToStr(secApplication.getNewComEndDate(), "yyyy-MM-dd")); // ?????????????????????
					}

					mod.addObject("reason", secApplication.getReason()); // ????????????
					mod.addObject("teacherNo", application.getTeacherNo()); // ??????????????????
					mod.addObject("teacherName", secApplication.getTeacherName()); // ??????????????????
					mod.addObject("teacherView", secApplication.getTeacherView()); // ??????????????????
					if (null == secApplication.getTeacherViewDate() || null == secApplication.getTeacherViewDate()) {
						mod.addObject("teacherViewDate",
								" ???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ???&nbsp;&nbsp;&nbsp;&nbsp; ???");

					} else {
						mod.addObject("teacherViewDate",
								FormatUtil.formatDateToStr(secApplication.getTeacherViewDate(), "yyyy???MM???dd???"));
					}
					// ??????????????????
					mod.addObject("adviserName", secApplication.getAdviserName()); // ???????????????
					mod.addObject("adviserView", secApplication.getAdviserView()); // ???????????????

					if (secApplication.getAdviserViewDate() == null) {
						mod.addObject("adviserViewDate",
								" ???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ???&nbsp;&nbsp;&nbsp;&nbsp; ???");
					} else {
						mod.addObject("adviserViewDate",
								FormatUtil.formatDateToStr(secApplication.getAdviserViewDate(), "yyyy???MM???dd???")); // ???????????????
					}

					mod.addObject("deptName", secApplication.getDeptName()); // ????????????????????????
					mod.addObject("departView", secApplication.getDeptView()); // ????????????????????????
					if (null == secApplication.getDeptViewDate() || null == secApplication.getDeptViewDate()) {
						mod.addObject("deptViewDate", " ???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ???&nbsp;&nbsp;&nbsp;&nbsp; ???");

					} else {
						mod.addObject("deptViewDate",
								FormatUtil.formatDateToStr(secApplication.getDeptViewDate(), "yyyy???MM???dd???")); // ????????????????????????
					}
					mod.addObject("staff", thisStaff.getPoststr());

				} else
					throw new Exception("???????????????????????????"); // ??????????????????

			}
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mod.addObject("tip", e.getMessage());
		}
		return mod;
	}

}
