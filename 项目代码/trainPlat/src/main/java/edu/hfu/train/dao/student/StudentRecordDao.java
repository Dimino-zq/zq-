package edu.hfu.train.dao.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class StudentRecordDao {
	@Resource
	BaseDaoImpl baseDaoImpl;

	/**
	 * 根据实习记录Id查询对应的实习记录
	 * @param recordId
	 * @return 返回一条StudentRecord记录
	 * @throws Exception
	 */
	public StudentRecord getRecordById(Integer recordId) throws Exception
	{
		return baseDaoImpl.get(StudentRecord.class, recordId);
	}
	
	/**
	 * 根据学生查询实习记录
	 * @param student
	 * @return 返回包该学生所有实习记录的集合
	 * @throws Exception
	 */
	public List<StudentRecord> getRecord(SysStudent student) throws Exception
	{
		String hql = "from StudentRecord where student.studentId = ?0 order by startDate";
		return baseDaoImpl.find(hql, student.getStudentId());
	}
	
	/**
	 * 根据学生查询实习记录数量
	 * @param student
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getRecordCount(SysStudent student) throws Exception
	{
		List<Object> params = new ArrayList<Object>();
		String hql = "from StudentRecord where student.studentId = ?0";
		params.add(student.getStudentId());
		return baseDaoImpl.queryBeanCountByHql(hql, params.toArray());
	}

	/**
	 * 新增一条实习记录，同时级联增加对应的工作记录
	 * @param record
	 * @throws Exception
	 */
	public void saveRecord(StudentRecord record) throws Exception
	{
		baseDaoImpl.save(record);
	}
	
	/**
	 * 删除实习记录，同时级联删除工作记录
	 * @param recordId
	 * @throws Exception
	 */
	public void deleteRecord(Integer recordId) throws Exception
	{
		//String hql = "delete StudentRecord where recordId=?0";
		//baseDaoImpl.bulkUpdate(hql,recordId);
		baseDaoImpl.delete(getRecordById(recordId));
	}
    /**
     * 更新实习记录，同时级联更新工作记录
     * @param record
     * @throws Exception
     */
	public void updateRecord(StudentRecord record) throws Exception
	{
		//baseDaoImpl.clear();
		baseDaoImpl.update(record);
	}

	/**
	 * 根据传入时间查询当前周提交的实习记录<br>
	 * startDate<= time or endDate>=time
	 * @param sysStudent 
	 * @param date
	 * @return 返回当前周的实习记录表，若不存在记录 list.size()==0
	 * @throws Exception
	 */
	public List<StudentRecord> getRecordByDate(SysStudent student, Date date) throws Exception
	{
		String hql = "from StudentRecord where student.studentId = ?0 and startDate <= ?1 and ?1 <= endDate";
		return baseDaoImpl.find(hql,student.getStudentId(),date);
	}


}
