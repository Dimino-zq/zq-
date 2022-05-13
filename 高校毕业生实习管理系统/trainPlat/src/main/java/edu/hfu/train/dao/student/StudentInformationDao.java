package edu.hfu.train.dao.student;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentInterReport;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class StudentInformationDao {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource
	BaseDaoImpl baseDaoImpl;

	// 查询数量
	public Integer getWorkRecordCount(SysStudent stu) throws Exception {
		String hql = "from StudentWorkRec stuWorkRec where student='" + stu.getStudentId() + "'";
		List<Object> params = new ArrayList<Object>();

		if (LOG.isDebugEnabled()) {
			LOG.debug("{}", "hql" + hql);
		}

		return baseDaoImpl.queryBeanCountByHql(hql, params.toArray());
	}

	public void chgPassword(SysStudent student) throws Exception {

		baseDaoImpl.update(student);

		// TODO Auto-generated method stub

	}

	public List<StudentApplication> getApplication(SysStudent student) throws Exception {
		// TODO Auto-generated method stub
		String hql = "from StudentApplication stuApp where stuApp.student.studentNo  = '" + student.getStudentNo()
				+ "'";
		return baseDaoImpl.find(hql);

	}

	public List<StudentSecApplication> getSecApplication(SysStudent student) throws Exception {
		// TODO Auto-generated method stub
		String hql = "from StudentSecApplication stuSecApp where stuSecApp.application.student.studentNo  = '"
				+ student.getStudentNo() + "'";
		return baseDaoImpl.find(hql);

	}

	public List<StudentInterReport> getReportApplication(SysStudent student) throws Exception {
		// TODO Auto-generated method stub

		String hql = "from StudentInterReport stuIntReport where stuIntReport.application.student.studentNo  = '"
				+ student.getStudentNo() + "'";
		return baseDaoImpl.find(hql);

	}

}
