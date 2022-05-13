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
import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.TeacherReportAppraisal;
import edu.hfu.train.service.sysset.DictionaryService;
import edu.hfu.train.service.teacher.TeacherStudentInterReportService;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherStudentInterReportAction {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource
	TeacherStudentInterReportService teacherStudentInterReport;

	@Resource
	DictionaryService dictionaryService;

	/**
	 * 跳转老师报告评阅界面
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/gototeacherstudentreport", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoTeacherStudentReport() {
		ModelAndView mod = new ModelAndView("/teacher/teastudentinterreport.btl");
		return mod;
	}

	/**
	 * 查询
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getStudentReportByCon", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getStudentReportByCon(HttpSession session, SysStudent stu,
			TeacherReportAppraisal teaReportAppraisal, StudentInterReport stuReport, int page, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<StudentInterReport> ls1 = new ArrayList<StudentInterReport>();
		try {

			ls1 = teacherStudentInterReport.getTeacherStudentReportByCon(session.getAttribute("userCode").toString(),
					stu, teaReportAppraisal, stuReport, page, rows);
			Integer count = teacherStudentInterReport.getTeacherStudentReportCount(
					session.getAttribute("userCode").toString(), stu, teaReportAppraisal, stuReport);

			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "StudentInterReport:" + ls1.toString());
				LOG.debug("{}", "Status:" + stuReport.getStatus());
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
	 * 实习总结报告审阅人意见
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTeacherReportAppraisal", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveTeacherReportAppraisal(StudentInterReport studentInterReportId,
			TeacherReportAppraisal teacherReportAppraisal) {
		String mess = "";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "ReportId:" + studentInterReportId.getReportId());
				LOG.debug("{}", "studentInterReportId:" + studentInterReportId.toString());
				LOG.debug("{}", "teacherReportAppraisal.getStudentInterReport:"
						+ teacherReportAppraisal.getStudentInterReport());
				LOG.debug("{}", "teacherReportAppraisal:" + String.valueOf(teacherReportAppraisal));
			}
			teacherReportAppraisal.setStudentInterReport(studentInterReportId);
			mess = teacherStudentInterReport.saveTeacherReportAppraisal(studentInterReportId.getReportId(),
					teacherReportAppraisal);
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	// 查询实习报告记录
	@RequestMapping(value = "/report", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotostudentreport(Integer intreportId, HttpSession session) {
		ModelAndView mod = new ModelAndView("teacher/report.btl");
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
			StudentInterReport intrep = teacherStudentInterReport.getStudentInterReportById(intreportId);

			if (null == intrep)
				throw new NullPointerException("无法查看：未查找到该申请记录！"); // 不存在该申请记录
			else {
				StudentApplication application = intrep.getApplication();
				if (userType.equals("student")) {
					// 检查查当前的学生是否为申请表中记录的学生
					if (!((SysStudent) user).getStudentId().equals(application.getStudent().getStudentId()))
						throw new Exception("无法查看：非法操作");
				} else if (userType.equals("teacher")) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("{}", "UserCode:" + ((SysStaff) user).getUserCode());
						LOG.debug("{}", "TeacherNo:" + application.getTeacherNo());

					}

					// 检查查当前的指导老师是否为申请表中记录的学生的指导老师
					if (((SysStaff) user).getUserCode().equals(application.getTeacherNo())) {
					} else {
						throw new Exception("无法查看：非法操作");
					}
				} else
					throw new Exception("无法查看：非法操作"); // 未知用户类型
				// 获取从申请表中获取学生信息
				SysStudent student = application.getStudent();
				// 获取从申请表中获取实习单位信息
				SysCompany company = application.getCompany();
				// btl替换数据

				mod.addObject("reportId", intrep.getReportId()); // 实习报告Id
				mod.addObject("comProfile", intrep.getComProfile());// 实习单位简介
				mod.addObject("comworkfile", intrep.getComworkfile()); // 实习单位工作简介
				mod.addObject("conclusion", intrep.getConclusion());// 实习总结
				mod.addObject("teacherNo", intrep.getTeacherNo()); // 教师工号
				mod.addObject("studentName", student.getStudentName());// 学生姓名
				mod.addObject("major", student.getMajor());// 学生专业
				mod.addObject("classNumber", student.getClassNumber());// 学生班级
				mod.addObject("teacherName", application.getTeacherName()); // 校内指导老师
				mod.addObject("comName", company.getComName()); // 单位名称
			}
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mod.addObject("tip", e.getMessage());
		}
		return mod;

	}

	// 跳转查看详情界面
	@RequestMapping(value = "/all", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotostudentreport1(Integer intreportId, HttpSession session) {
		ModelAndView mod = new ModelAndView("teacher/all.btl");
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
			StudentInterReport intrep = teacherStudentInterReport.getStudentInterReportById(intreportId);

			List<TeacherReportAppraisal> teaReportAppraisal = teacherStudentInterReport
					.getTeacherReportAppraisalById(intreportId);

			if (teaReportAppraisal.size() != 0) {
				TeacherReportAppraisal teaReportAppraisalObj = teaReportAppraisal.get(0);
				if (LOG.isDebugEnabled()) {
					LOG.debug("{}", "Grate:" + teaReportAppraisalObj.getFinalgrate());
					LOG.debug("{}", "Signaturedate:" + teaReportAppraisalObj.getSignaturedate());
					LOG.debug("{}", "teachergrate:" + teaReportAppraisalObj.getTeachergrate());

				}
				mod.addObject("teachergrate", teaReportAppraisalObj.getTeachergrate()); // 老师成绩
				mod.addObject("companygrate", teaReportAppraisalObj.getCompanygrate()); // 公司成绩
				mod.addObject("fianlgrate", teaReportAppraisalObj.getFinalgrate()); // 总成绩
				mod.addObject("reviewrComment", teaReportAppraisalObj.getReviewercomments());// 评语
				mod.addObject("signaturedate", teaReportAppraisalObj.getSignaturedate());// 评阅时间

			} else {
				mod.addObject("teachergrate", ""); // 老师成绩
				mod.addObject("companygrate", ""); // 公司成绩
				mod.addObject("fianlgrate", ""); // 总成绩
				mod.addObject("reviewrComment", "");// 评语
				mod.addObject("signaturedate", "");// 评阅时间
			}

			if (null == intrep)
				throw new NullPointerException("无法查看：未查找到该申请记录！"); // 不存在该申请记录
			else {
				StudentApplication application = intrep.getApplication();
				if (userType.equals("student")) {
					// 检查查当前的学生是否为申请表中记录的学生
					if (!((SysStudent) user).getStudentId().equals(application.getStudent().getStudentId()))
						throw new Exception("无法查看：非法操作");
				} else if (userType.equals("teacher")) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("{}", "UserCode:" + ((SysStaff) user).getUserCode());
						LOG.debug("{}", "TeacherNo:" + application.getTeacherNo());

					}
					// 检查查当前的指导老师是否为申请表中记录的学生的指导老师
					if (((SysStaff) user).getUserCode().equals(intrep.getTeacherNo())) {
					} else {
						throw new Exception("无法查看：非法操作");
					}
				} else
					throw new Exception("无法查看：非法操作"); // 未知用户类型
				// 获取从申请表中获取学生信息
				SysStudent student = application.getStudent();
				// 获取从申请表中获取实习单位信息
				SysCompany company = application.getCompany();
				// btl替换数据
				mod.addObject("reportId", intrep.getReportId()); // 实习报告Id
				mod.addObject("comProfile", intrep.getComProfile());// 实习单位简介
				mod.addObject("comworkfile", intrep.getComworkfile()); // 实习单位工作简介
				mod.addObject("conclusion", intrep.getConclusion());// 实习总结
				mod.addObject("teacherNo", intrep.getTeacherNo()); // 教师工号
				mod.addObject("studentName", student.getStudentName());// 学生姓名
				mod.addObject("major", student.getMajor());// 学生专业
				mod.addObject("classNumber", student.getClassNumber());// 学生班级
				mod.addObject("teacherName", application.getTeacherName()); // 校内指导老师
				if(null!=application.getAppraisalFromFirmPath()) {
					mod.addObject("appraisalpath", "/getRemoteImgFile?filePath="+application.getAppraisalFromFirmPath()); // 图片
				}else {
					mod.addObject("appraisalpath", ""); // 图片
				}
				
				
				mod.addObject("comName", company.getComName()); // 单位名称
				mod.addObject("status", intrep.getStatus());// 审阅状态
			}
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mod.addObject("tip", e.getMessage());
		}
		return mod;

	}
}
