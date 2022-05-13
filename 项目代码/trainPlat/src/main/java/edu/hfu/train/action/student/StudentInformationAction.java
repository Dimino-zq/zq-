package edu.hfu.train.action.student;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.train.bean.SysDepartTrainCycle;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.SysTrainCycle;
import edu.hfu.train.service.student.StudentInformationService;
import edu.hfu.train.service.sysset.SysStudentService;
import edu.hfu.train.util.FormatUtil;

@RestController
@RequestMapping(value = "/studentapp")
public class StudentInformationAction {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource
	StudentInformationService studentInformationService;
	@Resource
	SysStudentService studentservice;

	@RequestMapping(value = "/gotostudentinformation", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentApplication(HttpSession session) throws Exception {
		ModelAndView mod = new ModelAndView("student/studentInformation.btl");
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		SysDepartTrainCycle cycleDepart = (SysDepartTrainCycle) session.getAttribute("cycleDepart");
		SysTrainCycle cycleCologe = (SysTrainCycle) session.getAttribute("cycleCologe");
		SysStudent student = (SysStudent) user;
		// 获取学生信息
		List<SysStudent> studentTemp = studentservice.getStudent(student);
		SysStudent studentMuch = studentTemp.get(0);
		// 获取学生id
		student.getStudentId();
		// 根据id在workrec表中查询记录数量
		int cycleNum = studentInformationService.getInformationCount(student);
		// 获取学生总周期
		int totalCycleNum = 0;
		if (cycleDepart != null) {
			totalCycleNum = cycleDepart.getWeeks();
		}
		// 根据学号查询申请表状况
		String application = studentInformationService.getApplication(student);
		// 根据学号查询变更表状况
		String SecApplication = studentInformationService.getSecApplication(student);
		// 查询报告状况
		String reportApplication = studentInformationService.getReportApplication(student);
		mod.addObject("studentId", studentMuch.getStudentId()); // 系别
		mod.addObject("depart", studentMuch.getDepart()); // 系别
		mod.addObject("major", studentMuch.getMajor()); // 专业
		mod.addObject("studentName", studentMuch.getStudentName()); // 姓名
		mod.addObject("studentSex", studentMuch.getStudentSex()); // 性别
		mod.addObject("studentGrade", studentMuch.getStudentGrade()); // 年级
		mod.addObject("studentNo", studentMuch.getStudentNo()); // 学号
		mod.addObject("birthDay", FormatUtil.formatDateToStr(studentMuch.getBirthDay(), "yyyy-MM-dd"));// 生日
		mod.addObject("classNumber", studentMuch.getClassNumber());// 班级
		mod.addObject("politics", studentMuch.getPolitics());// 政治面貌
		mod.addObject("nation", studentMuch.getNation());// 民族
		mod.addObject("nativeplace", studentMuch.getNativeplace());// 籍贯
		mod.addObject("identity", studentMuch.getIdentity());// 身份证
		mod.addObject("application", application);// 申请状态
		mod.addObject("secApplication", SecApplication);// 变更状态
		mod.addObject("reportApplication", reportApplication);// 报告状态
		mod.addObject("cycleNum", cycleNum);// 现记录数
		mod.addObject("totalCycleNum", totalCycleNum);// 总记录数

		if (LOG.isDebugEnabled()) {
			LOG.debug("{}", "user:" + user);
			LOG.debug("{}", "studentMuch:" + studentMuch);
			LOG.debug("{}", "Password:" + student.getPassword());
			LOG.debug("{}", "cycleNum:" + cycleNum);
			LOG.debug("{}", "totalCycleNum:" + totalCycleNum);
			LOG.debug("{}", "application:" + application);
			LOG.debug("{}", "SecApplication:" + SecApplication);
			LOG.debug("{}", "reportApplication:" + reportApplication);

		}
		return mod;
	}

	@RequestMapping(value = "/updStudentInformation", method = { RequestMethod.GET, RequestMethod.POST })
	public String updStudentInformation(HttpSession session, SysStudent sysStu) {
		String mess = "";

		try {
			// SysCompany.setSchool(school);
			/*
			 * Date companyTime = DateUtil.strToDate(companyTimeVal, "yyyy-MM-dd");
			 * sysCompa.setCompanyTime(companyTime);
			 */
			// sysCompa.setUpdDate(new Date());
			Object user = session.getAttribute("user");
			SysStudent student = (SysStudent) user;

			sysStu.setUpdDate(new Date());
			sysStu.setUpdUser(sysStu.getStudentName());
			sysStu.setPassword(student.getPassword());
			sysStu.setCreateUser(student.getCreateUser());
			sysStu.setCreateDate(student.getCreateDate());

			mess = studentservice.updataSysStudent(sysStu);
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "sysStu:" + sysStu);
				LOG.debug("{}", "CreateUser:" + student.getCreateUser());
				LOG.debug("{}", "---------------");
				LOG.debug("{}", "sysStu:" + sysStu);
				LOG.debug("{}", "---------------");
			}

		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}

		return mess;
	}

	@RequestMapping(value = "/updStudentPassword", method = { RequestMethod.GET, RequestMethod.POST })
	public String updStudentPassword(HttpSession session, String newpassword, String oldpassword) {
		String mess = "";

		try {
			Object user = session.getAttribute("user");
			SysStudent student = (SysStudent) user;
			mess = studentInformationService.chgPassword(student, newpassword, oldpassword);

		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

}
