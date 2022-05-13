package edu.hfu.train.service.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.sysset.ReviewLogDao;
import edu.hfu.train.dao.teacher.TeacherSecApplicationDao;

@Service
@Transactional
public class TeacherSecApplicationService {
	@Resource
	TeacherSecApplicationDao applicationDao;
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
	public List<StudentSecApplication> getStudentSecByCon(Authentication auth, String a, SysStaff userBackStaff,
			StudentSecApplication stuSecApp, SysStudent stu, int page, int rows) throws Exception {

		return applicationDao.getStudentSecByCon(auth, a, userBackStaff, stuSecApp, stu, page, rows);
	}

	public Integer getSecApplicationCount(Authentication auth, String a, SysStaff userBackStaff,
			StudentSecApplication stuSecApp, SysStudent stu) throws Exception {
		// TODO Auto-generated method stub
		return applicationDao.getSecApplicationCount(auth, a, userBackStaff, stuSecApp, stu);
	}

	public List<StudentSecApplication> getAudit(String studentNo, String status) throws Exception {
		return applicationDao.getAudit(studentNo, status);

	}

	public void refuseTeacherSec(String passWay, Authentication auth, SysStaff userBackStaff,
			StudentSecApplication stuSecApp, String applyId) throws Exception {

		if (passWay == "teacher") {
			// 老师通过
			applicationDao.refuseTeacherSec(auth, userBackStaff, stuSecApp, applyId);
		} else {
			// 系主任通过
			applicationDao.refuseDeptSec(auth, userBackStaff, stuSecApp, applyId);
		}

	}

	public void passTeacherSec(String passWay, Authentication auth, String a, SysStaff userBackStaff,
			StudentSecApplication stuSecApp, String applyId) throws Exception {

		if (passWay == "teacher") {
			// 老师通过
			applicationDao.passTeacherSec(auth, a, userBackStaff, stuSecApp, applyId);
		} else {
			// 系主任通过
			applicationDao.passDeptSec(auth, a, userBackStaff, stuSecApp, applyId);
		}

	}

	public StudentSecApplication getSecApplicationById(Integer secApplicationId) throws Exception {
		return applicationDao.getSecApplicationById(secApplicationId);
	}
}
