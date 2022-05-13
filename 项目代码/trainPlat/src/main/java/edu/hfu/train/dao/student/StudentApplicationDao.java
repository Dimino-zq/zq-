package edu.hfu.train.dao.student;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class StudentApplicationDao {
	@Resource
	BaseDaoImpl baseDaoImpl;

	/**
	 * 根据申请表id查找申请表,未查找到数据是返回null
	 * @param applicationId
	 * @return
	 * @throws Exception
	 */
	public StudentApplication getApplicationById(Integer applicationId) throws Exception
	{
		return baseDaoImpl.get(StudentApplication.class, applicationId);
	}
	
	/**
	 * 根据学生id查找申请表
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public List<StudentApplication> getApplication(SysStudent student) throws Exception
	{
		String hql = "from StudentApplication where student.studentId = ?0";
		return baseDaoImpl.find(hql, student.getStudentId());
	}

	/**
	 * 保存一条申请表数据
	 * @param application
	 * @throws Exception
	 */
	public Integer saveApplication(StudentApplication application) throws Exception
	{
		return (Integer) baseDaoImpl.save(application);
	}

	/**
	 * 根据申请表id删除申请表
	 * @param studentApplication
	 * @throws Exception
	 */
	public void deleteApplication(StudentApplication studentApplication) throws Exception
	{
		baseDaoImpl.delete(studentApplication);
//			String hql = "delete StudentApplication where applyId=?0";
//			baseDaoImpl.bulkUpdate(hql,applicationId);
	}

	/**
	 * 更新申请表的数据
	 * @param application
	 * @throws Exception
	 */
	public void updateApplication(StudentApplication application) throws Exception
	{
		//baseDaoImpl.clear();
		baseDaoImpl.update(application);
	}

	/**
	 * 更新实习岗位已招聘人数+1
	 * @param detailId
	 * @throws Exception 
	 */
	public void addActualStu(Integer detailId) throws Exception {
		String hql = "update SysPlanDetail set actualStu = IFNULL(actualStu,0)+1 where detailId = ?0";
		baseDaoImpl.bulkUpdate(hql, detailId);
	}

	/**
	 * 更新实习岗位已招聘人数-1
	 * @param detailId
	 * @throws Exception 
	 */
	public void subActualStu(Integer detailId) throws Exception {
		String hql = "update SysPlanDetail set actualStu = IFNULL(actualStu,0)-1 where detailId = ?0 and actualStu>0";
		baseDaoImpl.bulkUpdate(hql, detailId);
	}

}
