package edu.hfu.train.dao.teacher;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class TeacherApplicationDao {
	@Resource
	BaseDaoImpl baseDaoImpl;

	/**
	 * 根据老师编号查找申请表
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	public List<StudentApplication> getStudentByCon(String teacherNo, SysStudent stu, StudentApplication stuApp,
			int page, int rows) throws Exception {
		String hql = "from StudentApplication a where  teacherNo = '" + teacherNo + "'";
		List<Object> params = new ArrayList<Object>();
		int index = 0;

		if (null != stu) {
			System.out.println(stu.getStudentName());
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += " and a.student.studentName=?" + index++;
				params.add(stu.getStudentName());
			}

		}
		if (null != stuApp) {
			if ("查询所有".equals(stuApp.getStatus())) {
				hql += " and (status='DApprove' or status='CApprove' or status='BApprove' )";
			} else if (null != stuApp.getStatus() && !"".equals(stuApp.getStatus())) {
				hql += " and status=?" + index++;
				params.add(stuApp.getStatus());
			} else {
				hql += " and ( status='CApprove' )";
			}
		}
		return baseDaoImpl.findByPage(hql, params.toArray(), page, rows);

	}

	/**
	 * 输出查询的数量
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	public Integer getApplicationCount(String teacherNo, SysStudent stu, StudentApplication stuApp) throws Exception {
		String hql = "from StudentApplication a where  teacherNo = '" + teacherNo + "'";
		List<Object> params = new ArrayList<Object>();
		int index = 0;

		if (null != stu) {
			System.out.println(stu.getStudentName());
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += " and a.student.studentName=?" + index++;
				params.add(stu.getStudentName());
			}

		}
		if (null != stuApp) {
			if ("查询所有".equals(stuApp.getStatus())) {
				hql += " and (status='DApprove' or status='CApprove' or status='BApprove' )";
			} else if (null != stuApp.getStatus() && !"".equals(stuApp.getStatus())) {
				hql += " and status=?" + index++;
				params.add(stuApp.getStatus());
			} else {
				hql += " and ( status='CApprove' )";
			}
		}
		return baseDaoImpl.queryBeanCountByHql(hql, params.toArray());
	}

	/**
	 * 将待审批改为未通过
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	public void refuseTeacher(StudentApplication teacher) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String hql = "update StudentApplication u set u.status='FApprove', deptView='" + teacher.getDeptView()
				+ "'  where u.status='CApprove' and applyId=?0 ";
		baseDaoImpl.bulkSqlUpdate(hql, teacher.getApplyId());

	}

	/**
	 * 将待审批改为已通过
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */

	public void passTeacher(StudentApplication teacher) throws Exception {
		String hql = "update StudentApplication u set u.status='DApprove',u.deptView='同意' where u.status='CApprove' and applyId=?0";
		// System.out.println(teacher.getApplyId().toString());
		baseDaoImpl.bulkSqlUpdate(hql, teacher.getApplyId());
	}

}
