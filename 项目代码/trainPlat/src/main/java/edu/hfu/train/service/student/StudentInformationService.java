package edu.hfu.train.service.student;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentInterReport;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.student.StudentInformationDao;
import edu.hfu.train.service.security.UserPassEncoder;
import edu.hfu.train.service.sysset.SysStudentService;

@Service
@Transactional
public class StudentInformationService {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource
	StudentInformationDao studentInformationDao;

	@Resource
	SysStudentService sysStudentService;

	public Integer getInformationCount(SysStudent stu) throws Exception {
		// TODO Auto-generated method stub
		return studentInformationDao.getWorkRecordCount(stu);
	}

	public String chgPassword(SysStudent student, String newpassword, String oldpassword) throws Exception {

		UserPassEncoder userPassEncoder = new UserPassEncoder();
		SysStudent studentForPassword = sysStudentService.getStudentByCon(student).get(0);
		if (userPassEncoder.matches(oldpassword, studentForPassword.getPassword())) {
			String newMd = userPassEncoder.encode(newpassword);
			studentForPassword.setPassword(newMd);
			studentInformationDao.chgPassword(studentForPassword);
			return "succ";

		} else {
			return "passworderror";
		}
	}

	public String getApplication(SysStudent student) throws Exception {
		// TODO Auto-generated method stub
		List<StudentApplication> ls = new ArrayList<StudentApplication>();
		ls = studentInformationDao.getApplication(student);

		StudentApplication stuApp = null;
		if (ls.size() != 0) {
			stuApp = ls.get(0);

			return stuApp.getStatusVal();
		}

		return "未申请";

	}

	public String getSecApplication(SysStudent student) throws Exception {
		// TODO Auto-generated method stub
		List<StudentSecApplication> ls = new ArrayList<StudentSecApplication>();
		ls = studentInformationDao.getSecApplication(student);

		String backMess = "";
		String backMes = "";
		if (ls.size() != 0) {
			for (StudentSecApplication ls1 : ls) {
				String tempStatus = ls1.getStatusVal();
				if (tempStatus.equals("作废")) {

				} else if (tempStatus.equals("审批通过")) {
					backMess = "审批通过";
				} else if (tempStatus.equals("已提交")) {
					backMes = "审核中";
					if (LOG.isDebugEnabled()) {
						LOG.debug("{}", "backMes:" + backMes);
					}
				} else {
					backMess = "审批通过";
				}
				if (LOG.isDebugEnabled()) {
					LOG.debug("{}", "tempStatus:" + tempStatus);
				}
			}
		} else {
			backMess = "无变更";
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("{}", "backMes:" + backMes);
		}
		if (backMes == "审核中") {
			return backMes;
		} else {
			return backMess;

		}

	}

	public String getReportApplication(SysStudent student) throws Exception {
		// TODO Auto-generated method stub
		List<StudentInterReport> ls = new ArrayList<StudentInterReport>();
		ls = studentInformationDao.getReportApplication(student);
		StudentInterReport stuIntReport = null;
		if (ls.size() != 0) {
			stuIntReport = ls.get(0);

			return stuIntReport.getStatus();
		}

		return "无报告";
	}

}
