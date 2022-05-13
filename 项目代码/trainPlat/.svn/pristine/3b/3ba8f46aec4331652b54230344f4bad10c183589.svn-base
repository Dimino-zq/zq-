package edu.hfu.train.dao.sysset;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class SysStudentDao {
	@Resource
	private BaseDaoImpl studentdao;

	/**
	 * 跟据学生id查询学生信息
	 * 
	 * @param studentId
	 *            Integer类型
	 * @return 返回一个学生实例，如果没找到返回null
	 * @throws Exception
	 */
	public SysStudent getStudentById(Integer studentId) throws Exception {
		return studentdao.get(SysStudent.class, studentId);
	}

	public List<SysStudent> getAllStudent() throws Exception {
		String hql = "from SysStudent";
		return studentdao.find(hql);
	}

	/**
	 * 根据条件查询学员，返回一个List结果集
	 * @param stu
	 * @return
	 * @throws Exception
	 */ 
	public List<SysStudent> getStudentByCon(SysStudent stu) throws Exception {
		String hql = "from SysStudent ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != stu) {
			if (null != stu.getStudentNo() && !"".equals(stu.getStudentNo())) {
				hql += (index == 0 ? "where" : "and") + " studentNo =? " + index++;
				params.add(stu.getStudentNo());
			}
			if (null != stu.getClassNumber() && !"".equals(stu.getClassNumber())) {
				hql += (index == 0 ? "where" : "and") + " classNumber =? " + index++;
				params.add(stu.getClassNumber());
			}
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += (index == 0 ? "where" : "and") + " studentName like ?" + index++;
				params.add('%' + stu.getStudentName() + '%');
			}
		}
		hql += " order by studentNo";
		return studentdao.find(hql, params.toArray());
	}

	/**
	 * 根据条件分页查询学员，返回一个List结果集
	 * @param stu
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<SysStudent> getStudentByCon(SysStudent stu, int pageNo, int pageSize) throws Exception {
		String hql = "from SysStudent ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != stu) {
			//根据学号
			if (null != stu.getStudentNo() && !"".equals(stu.getStudentNo())) {
				hql += (index == 0 ? "where" : "and") + " studentNo=?" + index++;
				params.add(stu.getStudentNo());
			}
			//根据班级
			if (null != stu.getClassNumber() && !"".equals(stu.getClassNumber())) {
				hql += (index == 0 ? "where" : "and") + " classNumber=?" + index++;
				params.add(stu.getClassNumber());
			}
			//根据姓名
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += (index == 0 ? "where" : "and") + " studentName like ?" + index++;
				params.add('%' + stu.getStudentName() + '%');
			}
			//根据院系
			if (null != stu.getDepart() && !"".equals(stu.getDepart())) {
				hql += (index == 0 ? "where" : "and") + " depart=? " + index++;
				params.add(stu.getDepart());
			}
			//根据专业
			if (null != stu.getMajor() && !"".equals(stu.getMajor())) {
				hql += (index == 0 ? "where" : "and") + " major like ? " + index++;
				params.add('%' + stu.getMajor() + '%');
			}
			//根据班级号
			if (null != stu.getClassNumber() && !"".equals(stu.getClassNumber())) {
				hql += (index == 0 ? "where" : "and") + " classNumber = ?" + index++;
				params.add(stu.getClassNumber());
			}
			//根据年级
			if (null != stu.getStudentGrade() && !"".equals(stu.getStudentGrade())) {
				hql += (index == 0 ? "where" : "and") + " studentGrade=?" + index++;
				params.add(stu.getStudentGrade());
			}
		}
		hql += " order by studentId";
		return studentdao.findByPage(hql, params.toArray(), pageNo, pageSize);
	}
	
	/**
	 * 根据条件查询学员，返回查询到的结果的数量
	 * @param stu
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public int getStudentCountByCon(SysStudent stu) throws Exception {
		String hql = "from SysStudent ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != stu) {
			//根据学号
			if (null != stu.getStudentNo() && !"".equals(stu.getStudentNo())) {
				hql += (index == 0 ? "where" : "and") + " studentNo=? " + index++;
				params.add(stu.getStudentNo());
			}
			//根据班级
			if (null != stu.getClassNumber()&& !"".equals(stu.getClassNumber())) {
				hql += (index == 0 ? "where" : "and") + " classNumber= ? " + index++;
				params.add(stu.getClassNumber());
			}
			//根据姓名
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += (index == 0 ? "where" : "and") + " studentName like ? " + index++;
				params.add('%' + stu.getStudentName() + '%');
			}
			//根据院系
			if (null != stu.getDepart() && !"".equals(stu.getDepart())) {
				hql += (index == 0 ? "where" : "and") + " depart=? " + index++;
				params.add(stu.getDepart());
			}
			//根据专业
			if (null != stu.getMajor() && !"".equals(stu.getMajor())) {
				hql += (index == 0 ? "where" : "and") + " major like ? " + index++;
				params.add('%' + stu.getMajor() + '%');
			}
			//根据班级号
			if (null != stu.getClassNumber() && !"".equals(stu.getClassNumber())) {
				hql += (index == 0 ? "where" : "and") + " classNumber = ?" + index++;
				params.add(stu.getClassNumber());
			}
			//根据年级
			if (null != stu.getStudentGrade() && !"".equals(stu.getStudentGrade())) {
				hql += (index == 0 ? "where" : "and") + " studentGrade=?" + index++;
				params.add(stu.getStudentGrade());
			}

		}
		hql += " order by studentId";
		return studentdao.queryBeanCountByHql(hql, params.toArray());
	}

	/**
	 * 保存一条学员数据
	 * @param student
	 * @throws Exception
	 */
	public void saveSysStudent(SysStudent student) throws Exception {
		studentdao.save(student);
	}

	/**
	 * 更新一条学员的数据
	 * @param student
	 * @throws Exception
	 */
	public void updateStudent(SysStudent student) throws Exception {
		studentdao.update(student);
	}

	/**
	 * 删除一条学生信息
	 * 
	 * @throws Exception
	 */
	public void delStudent(Integer studentId) throws Exception {
		String hql = "delete SysStudent where studentId=?0";
		studentdao.bulkUpdate(hql, studentId);
	}

	public List<SysStudent> getStudent(SysStudent student) throws Exception {
		// TODO Auto-generated method stub
		String hql = "from SysStudent where studentId='" + student.getStudentId() + "'";
		return studentdao.find(hql);
	}

	/**
	 * 获取最新一批进入实习的年级
	 * @return String
	 * @throws Exception
	 */
	public List<String> getLatestGrade() throws Exception
	{
		String hql = "select max(studentGrade) from SysStudent";
		return studentdao.find(hql);
	}
}
