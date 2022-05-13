package edu.hfu.train.dao.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.StudentWorkRec;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class TeacherWorkRecDao {

	@Resource
	BaseDaoImpl baseDaoImpl;

	// 查询
	public List<StudentApplication> getStudentWorkRecByCon(String teacherNo, SysStaff thisStaff, StudentRecord stuRec,
			StudentWorkRec stuWorkRec, SysStudent stu, int page, int rows) throws Exception {
		String hql = "from StudentRecord stuRec  ";
		List<Object> params = new ArrayList<Object>();
		int index = 0;
		String staff = thisStaff.getPoststr();
		if (null != stu) {
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += (index == 0 ? " where" : " and") + " stuRec.student.studentName like ?0 ";
				index++;
				params.add('%' + stu.getStudentName() + '%');
			}
		}

		if (null != stuRec) {
			if (null == stuWorkRec.getTutorComate()) {
				hql += (index == 0 ? " where" : " or") + "  stuRec.workRecord.tutorComate is null ";
				index++;
			} else if ("".equals(stuWorkRec.getTutorComate())) {
				hql += (index == 0 ? " where" : " or") + "  stuRec.workRecord.tutorComate is null ";
				index++;
			} else if ("有".equals(stuWorkRec.getTutorComate())) {
				hql += (index == 0 ? " where" : " and") + "  stuRec.workRecord.tutorComate='"
						+ stuWorkRec.getTutorComate() + "'";
				index++;
			} else if ("查询所有".equals(stuWorkRec.getTutorComate())) {

			}
		} else {

		}
		return baseDaoImpl.findByPage(hql, params.toArray(), page, rows);

	}

	// 查询数量
	public Integer getApplicationCount(String teacherNo, SysStaff thisStaff, StudentRecord stuRec,
			StudentWorkRec stuWorkRec, SysStudent stu) throws Exception {
		String hql = "from StudentRecord stuRec  ";
		List<Object> params = new ArrayList<Object>();
		int index = 0;
		String staff = thisStaff.getPoststr();

		if (null != stu) {
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += (index == 0 ? " where" : " and") + " stuRec.student.studentName like ?0 ";
				index++;
				params.add('%' + stu.getStudentName() + '%');
			}
		}

		if (null != stuRec) {
			if (null == stuWorkRec.getTutorComate()) {
				hql += (index == 0 ? " where" : " or") + "  stuRec.workRecord.tutorComate is null ";
				index++;
			} else if ("".equals(stuWorkRec.getTutorComate())) {
				hql += (index == 0 ? " where" : " or") + "  stuRec.workRecord.tutorComate is null ";
				index++;
			} else if ("已指导".equals(stuWorkRec.getTutorComate())) {
				hql += (index == 0 ? " where" : " and") + "  stuRec.workRecord.tutorComate='"
						+ stuWorkRec.getTutorComate() + "'";
				index++;
			} else if ("查询所有".equals(stuWorkRec.getTutorComate())) {

			}
		} else {

		}

		return baseDaoImpl.queryBeanCountByHql(hql, params.toArray());
	}

	// 教师辅导员指导
	public void passWorkRecTeacher(StudentRecord stuRec, StudentWorkRec stuWorkRec, SysStaff thisStaff)
			throws Exception {

		String hql1 = "update StudentWorkRec stuWorkRec " + "set  tutorComate = '" + stuWorkRec.getTutorComate() + "'"
				+ " , tutorComatemode = '" + stuWorkRec.getTutorComatemode() + "'" + " , tutorGuidcontent = '"
				+ stuWorkRec.getTutorGuidcontent() + "'" + " where workLogId = '" + stuWorkRec.getWorkLogId() + "'";

		String hql = "update StudentWorkRec stuWorkRec ";
		List<Object> params = new ArrayList<Object>();
		int index = 0;
		String staff = thisStaff.getPoststr();
		if (null != stuWorkRec.getTutorComate()) {
			hql += (index == 0 ? "set" : ",") + " tutorComate = '" + stuWorkRec.getTutorComate() + "'"
					+ " , tutorComatemode = '" + stuWorkRec.getTutorComatemode() + "'" + " , tutorGuidcontent = '"
					+ stuWorkRec.getTutorGuidcontent() + "'";
		}

		hql += " where workLogId = '" + stuWorkRec.getWorkLogId() + "'";
		baseDaoImpl.bulkSqlUpdate(hql);

	}

	// 修改record为已指导
	public void changeStatus(StudentRecord stuRec) throws Exception {
		String hql2 = "update StudentRecord stuRec " + "set guidStatus='已指导' " + " where  recordId = '"
				+ stuRec.getRecordId() + "'";

		baseDaoImpl.bulkSqlUpdate(hql2);

	}

	// 通过recordId查询数据
	public List<StudentWorkRec> getStudentWorkRecordById(StudentWorkRec stuWorkRec) throws Exception {
		String hql = "from StudentWorkRec where workLogId='" + stuWorkRec.getWorkLogId() + "'";
		return baseDaoImpl.find(hql);
	}

	public StudentWorkRec getTeacherWorkRecById(Integer workrecId) throws Exception {
		return baseDaoImpl.get(StudentWorkRec.class, workrecId);
	}
}
