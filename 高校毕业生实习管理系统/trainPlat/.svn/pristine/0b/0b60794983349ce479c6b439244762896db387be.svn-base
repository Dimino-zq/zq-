package edu.hfu.train.service.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentInterReport;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.TeacherReportAppraisal;
import edu.hfu.train.dao.teacher.TeacherStudentInterReportDao;

@Service
@Transactional
public class TeacherStudentInterReportService {

	@Resource
	TeacherStudentInterReportDao teacherStudentInterReportDao;

	public List<StudentInterReport> getTeacherStudentReportByCon(String string, SysStudent stu,
			TeacherReportAppraisal teaReportAppraisal, StudentInterReport stuReport, int page, int rows)
			throws Exception {
		return teacherStudentInterReportDao.getTeacherStudentReportByCon(string, stu, teaReportAppraisal, stuReport,
				page, rows);
		// TODO Auto-generated method stub

	}

	public Integer getTeacherStudentReportCount(String string, SysStudent stu,
			TeacherReportAppraisal teaReportAppraisal, StudentInterReport stuReport) throws Exception {
		// TODO Auto-generated method stub
		return teacherStudentInterReportDao.getTeacherStudentReportCount(string, stu, teaReportAppraisal, stuReport);

	}

	public String saveTeacherReportAppraisal(Integer ReportId, TeacherReportAppraisal teacherReportAppraisal)
			throws Exception {
		// TODO Auto-generated method stub
		SysStudent stu = new SysStudent();
		StudentApplication stuapp = new StudentApplication();
		StudentInterReport stureport = new StudentInterReport();

		stureport = getStudentInterReportById(ReportId);
		stuapp = stureport.getApplication();
		stu = stuapp.getStudent();
		teacherStudentInterReportDao.saveTeacherReportAppraisal(teacherReportAppraisal, stu);
		return "succ";
	}

	public StudentInterReport getStudentInterReportById(Integer reportId) throws Exception {
		return teacherStudentInterReportDao.getStudentInterReportById(reportId);
	}

	public List<TeacherReportAppraisal> getTeacherReportAppraisalById(Integer reportId) throws Exception {
		return teacherStudentInterReportDao.getTeacherReportAppraisalById(reportId);
	}
}
