package edu.hfu.train.dao.student;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.StudentInterReport;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class StudentIntRepDao {
	@Resource
	BaseDaoImpl baseDaoImpl;
	
	
	/**
	 * 根据报告表id查找报告表,未查找到数据是返回null
	 * @param applicationId
	 * @return
	 * @throws Exception
	 */
	public StudentInterReport getReportById(Integer reportId) throws Exception
	{
		return baseDaoImpl.get(StudentInterReport.class, reportId);
	}
	
	/**
	 * 根据学生id查找报告表
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public List<StudentInterReport> getReportByStudent(SysStudent student) throws Exception 
	{
		String hql = "from StudentInterReport where application.student.studentId = ?0";
		return baseDaoImpl.find(hql, student.getStudentId());
	}
	
	
	/**
	 * 保存一条申请表数据
	 * @param application
	 * @throws Exception
	 */
	public Integer saveReport(StudentInterReport report) throws Exception
	{
		return (Integer) baseDaoImpl.save(report);
	}

	/**
	 * 更新申请表的数据
	 * @param application
	 * @throws Exception
	 */
	public void updateReport(StudentInterReport report) throws Exception
	{
		baseDaoImpl.update(report);
	}

}
