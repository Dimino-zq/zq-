package edu.hfu.train.service.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.sysset.ReviewLogDao;
import edu.hfu.train.dao.teacher.TeacherApplicationDao;

@Service
@Transactional
public class TeacherApplicationService {

	@Resource
	TeacherApplicationDao applicationDao;
	@Resource
	ReviewLogDao reviewLogDao;

	/**
	 * 根据老师编号查询
	 * 
	 * @param student
	 *            学生对象
	 * @return List
	 * @throws Exception
	 */
	public List<StudentApplication> getStudentByCon(String teacherNo, SysStudent stu, StudentApplication stuApp,
			int page, int rows) throws Exception {
		return applicationDao.getStudentByCon(teacherNo, stu, stuApp, page, rows);
	}

	public Integer getApplicationCount(String teacherNo, SysStudent stu, StudentApplication stuApp) throws Exception {
		// TODO Auto-generated method stub
		return applicationDao.getApplicationCount(teacherNo, stu, stuApp);
	}

	public void refuseTeacher(StudentApplication studentApplicationTeacher) throws Exception {
		applicationDao.refuseTeacher(studentApplicationTeacher);
		reviewLogDao.addReviewLog("StudentApplication", studentApplicationTeacher.getApplyId(), "审批", "CApprove",
				"FApprove", "实习申请:审批未通过!原因:" + studentApplicationTeacher.getDeptView() + "!");
	}

	public void passTeacher(StudentApplication studentApplicationTeacher) throws Exception {
		applicationDao.passTeacher(studentApplicationTeacher);
		reviewLogDao.addReviewLog("StudentApplication", studentApplicationTeacher.getApplyId(), "审批", "CApprove",
				"DApprove", "实习申请:审批通过!");

		// return "succ";
	}
}
