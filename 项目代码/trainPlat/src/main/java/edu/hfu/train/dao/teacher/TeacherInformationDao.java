package edu.hfu.train.dao.teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentInterReport;
import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class TeacherInformationDao {

	@Resource
	BaseDaoImpl baseDaoImpl;

	/**
	 * 查询指定实训周期下，指定教师所指导的学生提交的实习申请
	 * @param teacherNo
	 * @param cycleStartYser
	 * @param semester
	 * @return
	 * @throws Exception 
	 */
	public List<StudentApplication> getApplcationByTeacher(String teacherNo, String cycleStartYser) throws Exception
	{
		String hql = "from StudentApplication where teacherNo=?0 and trainCycle.sysTrainCycle.startschoolyear=?1";
		return baseDaoImpl.find(hql, teacherNo, cycleStartYser);
	}

	public List<StudentRecord> getStuRecordByStudentId(Integer stuId) throws Exception
	{
		String hql = "from StudentRecord where student.studentId=?0";
		return baseDaoImpl.find(hql, stuId);
	}

	public List<StudentInterReport> getStuInterReportByApplicationId(Integer applyId) throws Exception
	{
		String hql = "from StudentInterReport where application.applyId=?0";
		return baseDaoImpl.find(hql, applyId);
	}

	public List<StudentSecApplication> getStuSecApplicationBy(Integer applyId) throws Exception
	{
		String hql = "from StudentSecApplication where application.applyId=?0";
		return baseDaoImpl.find(hql, applyId);
	}

	public List<Map<String, Object>> getStuTrainInfo(String teacherNo, String cycleStartYser, SysStudent student) throws Exception {
		List<Object> params = new ArrayList<Object>();
		int index = 2;
		String sql = "SELECT"
				+ " dc.weeks,"
				+ "	s.studentNo,"
				+ "	s.studentName,"
				+ "	s.major,"
				+ "	s.classNumber,"
				+ "	COUNT(DISTINCT r.recordId) AS trainProgress"
				+ " FROM"
				+ "	studentapplication a"
				+ " LEFT JOIN sysdeparttraincycle dc ON a.trainCycleId = dc.sysDepartTrainCycleId"
				+ " LEFT JOIN systraincycle c ON dc.sysTrainCycleId = c.cycleId"
				+ " LEFT JOIN sysstudent s ON s.studentId = a.studentId"
				+ " LEFT JOIN studentrecord r ON a.studentId = r.studentid"
				+ " WHERE"
				+ "	a.teacherNo = ?0"
				+ " AND c.startschoolyear = ?1";
		params.add(teacherNo);
		params.add(cycleStartYser);
		if (null != student) {
			if (null != student.getStudentName() && !"".equals(student.getStudentName())) {
				sql = sql + " AND s.studentName like ?" + index++;
				params.add('%'+student.getStudentName()+'%');
			}
			if (null != student.getMajor() && !"".equals(student.getMajor())) {
				sql = sql + " AND s.major like ?" + index++;
				params.add('%'+student.getMajor()+'%');
			}
			if (null != student.getClassNumber() && !"".equals(student.getClassNumber())) {
				sql = sql + " AND s.classNumber = ?" + index++;
				params.add(student.getClassNumber());
			}
		}
		sql+=" GROUP BY a.studentId";
		return baseDaoImpl.findBySQL(sql, params.toArray());
	}
	
}
