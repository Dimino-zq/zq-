package edu.hfu.train.action.student;

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
import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.bean.SysDepartTrainCycle;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.TeacherReportAppraisal;
import edu.hfu.train.service.student.StudentIntRepService;
import edu.hfu.train.service.student.StudentRecordService;
import edu.hfu.train.service.teacher.TeacherStudentInterReportService;
import edu.hfu.train.util.FormatUtil;

@RestController
@RequestMapping(value = "/studentapp")
public class StudentIntRepAction {
	private final Logger LOG = LoggerFactory.getLogger(StudentIntRepAction.class);

	@Resource
	StudentIntRepService stuentrepservice;

	@Resource
	StudentRecordService studentRecordService;

	@Resource
	TeacherStudentInterReportService teacherStudentInterReport;

	/**
	 * 返回实习报告书的操作界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gototrainreport", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentApplication(HttpSession session) {
		ModelAndView mod = null;
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前实训周期
		Object deptCycle = session.getAttribute("cycleDepart");
		if (null == user)
			throw new NullPointerException("查询失败：未登录状态！");
		else if (!session.getAttribute("userType").equals("student"))
			throw new ClassCastException("查询失败：用户类型不匹配！");
		try {
			SysStudent student = (SysStudent) user;
			if (stuentrepservice.checkApplication((SysStudent) user)) {
				Integer recordCount = studentRecordService.getRecordCount((SysStudent) user);
				Integer weeks = ((SysDepartTrainCycle) deptCycle).getWeeks();
				if (null == deptCycle || recordCount >= weeks) {
					mod = new ModelAndView("student/studenttrainreport.btl"); // 当实习工作记录数大于等于系周期中规定的实习周数时才可提交实习报告书
					mod.addObject("stuTrainStatus", student.getTrainStatus());

					List<StudentInterReport> list = stuentrepservice.getReportByStudent((SysStudent) user);
					if (null != list && 0 != list.size()) {
						List<TeacherReportAppraisal> teaReportAppraisals = teacherStudentInterReport
								.getTeacherReportAppraisalById(list.get(0).getReportId());
						if (null != teaReportAppraisals && 0 != teaReportAppraisals.size()) {
							TeacherReportAppraisal teaReportAppraisal = teaReportAppraisals.get(0);
							mod.addObject("reportAppraisal", true);
							mod.addObject("reviewercomments", teaReportAppraisal.getReviewercomments());
							mod.addObject("grade", teaReportAppraisal.getFinalgrate());
							mod.addObject("signature", teaReportAppraisal.getSignature());
							mod.addObject("signaturedate", FormatUtil
									.formatDateToStr(teaReportAppraisal.getSignaturedate(), "yyyy年   MM月   dd日"));
						}
					}
				} else {
					mod = new ModelAndView("student/tip.btl");
					mod.addObject("tipURL", "/static/images/noSucc.jpg");
					mod.addObject("recordCount", recordCount);
					mod.addObject("weeks", weeks);
				}
			}
		} catch (Exception e) {
			LOG.debug("{}", e.getMessage());
			mod = new ModelAndView("student/tip.btl");
			mod.addObject("tipURL", "/static/images/noApplication.jpg");
		}
		return mod;
	}

	/**
	 * 返回当前学生用户的实习报告书
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/gettrainreport", method = { RequestMethod.GET, RequestMethod.POST })
	public List<StudentInterReport> getTrainReport(HttpSession session) {
		try {
			// 从session中获取当前用户
			Object user = session.getAttribute("user");
			if (null == user)
				throw new NullPointerException("查询失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("查询失败：用户类型不匹配！");
			// 查询当前学生的报告表
			List<StudentInterReport> list = stuentrepservice.getReportByStudent((SysStudent) user);
			return list;
		} catch (ClassCastException | NullPointerException e) {
			LOG.debug("{}", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 保存申请表
	@RequestMapping(value = "/savereport", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> saveReport(StudentInterReport report, HttpSession session) {
		Map<String, String> message = new HashMap<String, String>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("保存失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("保存失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("保存失败：实训周期未开启！");
			else if (null == ((SysStudent) user).getStudentId())
				throw new Exception("保存失败：学生Id为空！");
			else if (null == report)
				throw new NullPointerException("保存失败：报告表不存在！");

			// 保存实习申请
			message.put("tip", stuentrepservice.saveReport(report, (SysStudent) user));
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}

	// 修改申请表
	@RequestMapping(value = "/updatereport", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> updateApplication(StudentInterReport report, HttpSession session) {
		Map<String, String> message = new HashMap<String, String>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("修改失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("修改失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("保存失败：实训周期未开启！");
			else if (null == ((SysStudent) user).getStudentId())
				throw new Exception("修改失败：学生Id为空！");
			else if (null == report)
				throw new NullPointerException("修改失败：实习报告数据不存在！");
			// 检查要修改的申请表的Id是否存在
			if (report.getReportId() != null) {
				message.put("tip", stuentrepservice.updateReport(report, ((SysStudent) user).getStudentId()));
			} else
				message.put("error", "修改失败，未知的实习报告书！");
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}

	// 查询实习报告记录
	@RequestMapping(value = "/allinfo", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotostudentreport(Integer intreportId, HttpSession session) {
		ModelAndView mod = new ModelAndView("student/htmltrainreport.btl");
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前用户类型
		Object userType = session.getAttribute("userType");

		try {
			if (null == user)
				throw new NullPointerException("无法查看：未登录状态！");
			else if (null == userType)
				throw new NullPointerException("无法查看：未知用户类型！");
			else if (null == intreportId)
				throw new NullPointerException("无法查看：请选中实习报告表！");
			// 查询实习报告Id对应的实习报告
			StudentInterReport intrep = stuentrepservice.getReportById(intreportId);

			if (null == intrep)
				throw new NullPointerException("无法查看：未查找到该申请记录！"); // 不存在该申请记录
			else {
				StudentApplication application = intrep.getApplication();
				if (userType.equals("student")) {
					// 检查查当前的学生是否为申请表中记录的学生
					if (!((SysStudent) user).getStudentId().equals(application.getStudent().getStudentId()))
						throw new Exception("无法查看：非法操作");
				} else
					throw new Exception("无法查看：非法操作"); // 未知用户类型
				// 获取从申请表中获取学生信息
				SysStudent student = application.getStudent();
				// 获取从申请表中获取实习单位信息
				SysCompany company = application.getCompany();
				// btl替换数据
				mod.addObject("date", FormatUtil.formatDateToStr(intrep.getUpdDate(), "yyyy年   MM月   dd日")); // 实习报告Id
				mod.addObject("reportId", intrep.getReportId()); // 实习报告Id
				mod.addObject("comProfile", intrep.getComProfile());// 实习单位简介
				mod.addObject("comworkfile", intrep.getComworkfile()); // 实习单位工作简介
				mod.addObject("conclusion", intrep.getConclusion());
				// 实习总结
				mod.addObject("teacherNo", intrep.getTeacherNo()); // 教师工号
				mod.addObject("studentName", student.getStudentName());// 学生姓名
				mod.addObject("major", student.getMajor());// 学生专业
				mod.addObject("classNumber", student.getClassNumber());// 学生班级
				mod.addObject("teacherName", application.getTeacherName()); // 校内指导老师
				mod.addObject("comName", company.getComName()); // 单位名称
				// 成绩评语
				List<TeacherReportAppraisal> teaReportAppraisals = teacherStudentInterReport
						.getTeacherReportAppraisalById(intrep.getReportId());
				if (null != teaReportAppraisals && 0 != teaReportAppraisals.size()) {
					TeacherReportAppraisal teaReportAppraisal = teaReportAppraisals.get(0);
					mod.addObject("reportAppraisal", true);
					mod.addObject("reviewercomments", teaReportAppraisal.getReviewercomments());
					mod.addObject("grade", teaReportAppraisal.getFinalgrate());
					mod.addObject("signature", teaReportAppraisal.getSignature());
					mod.addObject("signaturedate",
							FormatUtil.formatDateToStr(teaReportAppraisal.getSignaturedate(), "yyyy年   MM月   dd日"));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			mod.addObject("tip", e.getMessage());
		}
		return mod;

	}
}
