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
import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.StudentWorkRec;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.service.sysset.DictionaryService;
import edu.hfu.train.service.teacher.TeacherWorkRecService;
import edu.hfu.train.util.FormatUtil;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherWorkRecAction {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource
	TeacherWorkRecService teacherWorkRecService;

	@Resource
	DictionaryService dictionaryService;

	@RequestMapping(value = "/gotoTeacherWorkRec", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoTeacherWorkRec() {
		ModelAndView mod = new ModelAndView("/teacher/teastudentworkrec.btl");
		return mod;
	}

	// 查询
	@RequestMapping(value = "/getTeacherWorkRecByCon", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getTeacherWorkRecByCon(HttpSession session, StudentRecord stuRec,
			StudentWorkRec stuWorkRec, SysStudent stu, int page, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<StudentApplication> ls1 = new ArrayList<StudentApplication>();
		SysStaff thisStaff = (SysStaff) session.getAttribute("user");
		try {

			ls1 = teacherWorkRecService.getTeacherWorkRecByCon(session.getAttribute("userCode").toString(), thisStaff,
					stuRec, stuWorkRec, stu, page, rows);
			Integer count = teacherWorkRecService.getTeacherWorkRecCount(session.getAttribute("userCode").toString(),
					thisStaff, stuRec, stuWorkRec, stu);
			map.put("rows", ls1);
			map.put("total", count);
		} catch (Exception e) {
			LOG.error("Exception:", e);
			map.put("total", 0);
			map.put("rows", null);
		}
		return map;
	}

	// 指导
	@RequestMapping(value = "/passWorkRecTeacher", method = { RequestMethod.GET, RequestMethod.POST })
	public String passWorkRecTeacher(HttpSession session, StudentRecord stuRec, StudentWorkRec stuWorkRec) {
		String mess = "";
		SysStaff thisStaff = (SysStaff) session.getAttribute("user");
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "stuRec:" + stuRec);
			}
			teacherWorkRecService.passWorkRecTeacher(stuRec, stuWorkRec, thisStaff);
			mess = "succ";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	// 查询周实习记录
	@RequestMapping(value = "/workrec", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoTeastudentworkrec(Integer teastudentworkrecId, HttpSession session) {
		ModelAndView mod = new ModelAndView("teacher/everyweekwork.btl");
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前用户类型
		Object userType = session.getAttribute("userType");

		try {
			if (null == user)
				throw new NullPointerException("无法查看：未登录状态！");
			else if (null == userType)
				throw new NullPointerException("无法查看：未知用户类型！");
			else if (null == teastudentworkrecId)
				throw new NullPointerException("无法查看：请选中记录表！");
			// 查询记录表表Id对应的申请表
			StudentWorkRec workrec = teacherWorkRecService.getTeacherWorkRecById(teastudentworkrecId);

			if (null == workrec)
				throw new NullPointerException("无法查看：未查找到该申请记录！"); // 不存在该申请记录
			else {

				// btl替换数据

				mod.addObject("workLogId", workrec.getWorkLogId()); // 工作记录编号
				mod.addObject("weeks", workrec.getWeeks()); // 周数
				mod.addObject("startDate", workrec.getStartDate()); // 记录开始日期
				mod.addObject("startendDate", FormatUtil.formatDateToStr(workrec.getStartDate(), "yyyy-MM-dd") + "至"
						+ FormatUtil.formatDateToStr(workrec.getEndDate(), "yyyy-MM-dd")); // 周记录起止时间

				mod.addObject("endDate", workrec.getEndDate()); // 记录结束日期
				mod.addObject("workContents", workrec.getWorkContents()); // 工作主要内容
				mod.addObject("maingains", workrec.getMaingains()); // 主要收获
				mod.addObject("tutorComate", workrec.getTutorComate()); // 导师沟通
				mod.addObject("tutorComatemode", workrec.getTutorComatemode()); // 导师沟通方式
				mod.addObject("tutorGuidcontent", workrec.getTutorGuidcontent()); // 导师沟通内容
				mod.addObject("selorComate", workrec.getSelorComate()); // 辅导员沟通
				mod.addObject("selorComatemode", workrec.getSelorComatemode()); // 辅导员沟通方式
				mod.addObject("selorGuidcontent", workrec.getSelorGuidcontent()); // 辅导员指导内容
				mod.addObject("notes", workrec.getNotes()); // 其他（备注）
				mod.addObject("teacherNo", workrec.getTeacherNo()); // 教师工号
				mod.addObject("fullHours", workrec.getFullHours()); // 满勤
				mod.addObject("lateTime", workrec.getLateTime()); // 迟到
				mod.addObject("leaveEarly", workrec.getLeaveEarly()); // 早退
				mod.addObject("neglect", workrec.getNeglect()); // 旷工
				mod.addObject("sickLeave", workrec.getSickLeave()); // 病假
				mod.addObject("thingsLeave", workrec.getThingsLeave()); // 事假

			}
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mod.addObject("tip", e.getMessage());
		}
		return mod;

	}

}
