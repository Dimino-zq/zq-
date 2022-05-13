package edu.hfu.train.dao.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.StudentInterReport;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.TeacherReportAppraisal;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class TeacherStudentInterReportDao {

	@Resource
	BaseDaoImpl baseDaoImpl;

	public List<StudentInterReport> getTeacherStudentReportByCon(String teacherNo, SysStudent stu,
			TeacherReportAppraisal teaReportAppraisal, StudentInterReport stuReport, int page, int rows)
			throws Exception {
		String hql = "from StudentInterReport stuReport where stuReport.teacherNo = '" + teacherNo + "'";
		List<Object> params = new ArrayList<Object>();
		int index = 0;
		if (null != stu) {
			System.out.println(stu.getStudentName());
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += " and  stuReport.application.student.studentName like ?" + index++;
				params.add('%' + stu.getStudentName() + '%');
			}

		}
		if ("已评阅".equals(stuReport.getStatus())) {
			hql += " and  stuReport.status=?" + index++;
			params.add(stuReport.getStatus());
		} else if ("查询所有".equals(stuReport.getStatus())) {

		} else if ("未评阅".equals(stuReport.getStatus())) {
			hql += " and  stuReport.status <> '已评阅' or stuReport.status is null ";

		} else {
		}
		return baseDaoImpl.findByPage(hql, params.toArray(), page, rows);
	}

	public Integer getTeacherStudentReportCount(String teacherNo, SysStudent stu,
			TeacherReportAppraisal teaReportAppraisal, StudentInterReport stuReport) throws Exception {
		String hql = "from StudentInterReport stuReport where stuReport.teacherNo = '" + teacherNo + "'";
		List<Object> params = new ArrayList<Object>();
		int index = 0;
		if (null != stu) {
			System.out.println(stu.getStudentName());
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += " and  stuReport.application.student.studentName=?" + index++;

				params.add(stu.getStudentName());
			}

		}

		if ("已评阅".equals(stuReport.getStatus())) {
			hql += " and  stuReport.status=?" + index++;
			params.add(stuReport.getStatus());

		} else if ("查询所有".equals(stuReport.getStatus())) {

		} else if ("未评阅".equals(stuReport.getStatus())) {
			hql += " and  stuReport.status <> '已评阅' or stuReport.status is null ";

		} else {
		}

		return baseDaoImpl.queryBeanCountByHql(hql, params.toArray());
	}

	public void saveTeacherReportAppraisal(TeacherReportAppraisal teacherReportAppraisal, SysStudent stu)
			throws Exception {
		// TODO Auto-generated method stub

		baseDaoImpl.save(teacherReportAppraisal);
		String hql = "update StudentInterReport set status='已评阅',grate='" + teacherReportAppraisal.getFinalgrate()
				+ "' where reportId='"
				+ ((StudentInterReport) teacherReportAppraisal.getStudentInterReport()).getReportId() + "'";
		baseDaoImpl.bulkSqlUpdate(hql);
		String hqla = "update SysStudent set trainStatus='1'  where studentNo='" + stu.getStudentNo() + "'";
		baseDaoImpl.bulkSqlUpdate(hqla);

	}

	public StudentInterReport getStudentInterReportById(Integer reportId) throws Exception {
		return baseDaoImpl.get(StudentInterReport.class, reportId);
	}

	public List<TeacherReportAppraisal> getTeacherReportAppraisalById(Integer reportId) throws Exception {
		String hql = "from TeacherReportAppraisal teaReport where teaReport.studentInterReport.reportId = '" + reportId
				+ "'";
		return baseDaoImpl.find(hql);

	}
}
