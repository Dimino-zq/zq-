package edu.hfu.train.service.teacher;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentInterReport;
import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.teacher.TeacherInformationDao;
import edu.hfu.train.service.sysset.SysStudentService;

@Service
@Transactional
public class TeacherInformationService {
	@Resource
	TeacherInformationDao teacherInformationDao;

	@Resource
	SysStudentService sysStudentService;

	/**
	 * 查询当前实训周期下，指定教师所指导的学生提交的实习申请
	 * @param teacherNo 教师工号
	 * @param cycleStartYser 周期开始年份
	 * @param semester	学期
	 * @return
	 */
	public List<StudentApplication> getApplicationByTeacher(String teacherNo, String cycleStartYser) throws Exception
	{
		return teacherInformationDao.getApplcationByTeacher(teacherNo, cycleStartYser);
	}

	public List<StudentRecord> getStuRecordByStudentId(Integer stuId) throws Exception
	{
		
		return teacherInformationDao.getStuRecordByStudentId(stuId);
	}

	public List<StudentInterReport> getStuInterReportByApplicationId(Integer applyId) throws Exception {
		return teacherInformationDao.getStuInterReportByApplicationId(applyId);
	}

	public List<StudentSecApplication> getStuSecApplicationBy(Integer applyId) throws Exception
	{
		return teacherInformationDao.getStuSecApplicationBy(applyId);
	}

	/**
	 * 获取当前教师指导的学生的实习情况
	 * @param teacherNo 教师工号
	 * @param startschoolyear 实训周期开始年份
	 * @param student 查询的条件
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public List<Map<String, Object>> getStuTrainInfo(String teacherNo, String startschoolyear, SysStudent student) throws Exception {
		if(null == teacherNo || null == startschoolyear)
			throw new RuntimeException("参数缺失");
		return teacherInformationDao.getStuTrainInfo(teacherNo, startschoolyear, student);
	}
	
}
