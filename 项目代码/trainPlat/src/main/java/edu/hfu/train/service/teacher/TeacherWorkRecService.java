package edu.hfu.train.service.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.StudentWorkRec;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.sysset.ReviewLogDao;
import edu.hfu.train.dao.teacher.TeacherWorkRecDao;

@Service
@Transactional
public class TeacherWorkRecService {

	@Resource
	TeacherWorkRecDao teaWorkDao;
	@Resource
	ReviewLogDao reviewLogDao;

	public List<StudentApplication> getTeacherWorkRecByCon(String teacherNo, SysStaff thisStaff, StudentRecord stuRec,
			StudentWorkRec stuWorkRec, SysStudent stu, int page, int rows) throws Exception {
		return teaWorkDao.getStudentWorkRecByCon(teacherNo, thisStaff, stuRec, stuWorkRec, stu, page, rows);
	}

	public Integer getTeacherWorkRecCount(String teacherNo, SysStaff thisStaff, StudentRecord stuRec,
			StudentWorkRec stuWorkRec, SysStudent stu) throws Exception {
		// TODO Auto-generated method stub
		return teaWorkDao.getApplicationCount(teacherNo, thisStaff, stuRec, stuWorkRec, stu);
	}

	public void passWorkRecTeacher(StudentRecord stuRec, StudentWorkRec stuWorkRec, SysStaff thisStaff)
			throws Exception {
		teaWorkDao.passWorkRecTeacher(stuRec, stuWorkRec, thisStaff);
		List<StudentWorkRec> stuWorkRecord = teaWorkDao.getStudentWorkRecordById(stuWorkRec);
		teaWorkDao.changeStatus(stuRec);
	}

	public StudentWorkRec getTeacherWorkRecById(Integer workrecId) throws Exception {
		return teaWorkDao.getTeacherWorkRecById(workrecId);
	}
}
