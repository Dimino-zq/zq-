package edu.hfu.train.dao.student;

import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.base.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class StudentSecApplicationDao {
    @Resource
    BaseDaoImpl baseDaoImpl;

    /**
     * 根据变更申请表id查找申请表,未查找到数据是返回null
     * @param secApplicationId
     * @return
     * @throws Exception
     */
    public StudentSecApplication getSecApplicationById(Integer secApplicationId) throws Exception
    {
        return baseDaoImpl.get(StudentSecApplication.class, secApplicationId);
    }

    /**
     * 根据学生id查找变更申请表
     * @param student
     * @return
     * @throws Exception
     */
    public List<StudentSecApplication> getSecApplication(SysStudent student) throws Exception
    {
        String hql = "from StudentSecApplication where application.student.studentId = ?0 order by createDate";
        return baseDaoImpl.find(hql, student.getStudentId());
    }

    /**
     * 保存一条变更申请表数据
     * @param secApplication
     * @throws Exception
     */
    public Integer saveSecApplication(StudentSecApplication secApplication) throws Exception
    {
        return (Integer) baseDaoImpl.save(secApplication);
    }

    /**
     * 根据变更申请表id删除申请表
     * @param secApplicationId
     * @throws Exception
     */
    public void deleteSecApplication(Integer secApplicationId) throws Exception
    {
        String hql = "delete StudentSecApplication where secApplyId=?0";
        baseDaoImpl.bulkUpdate(hql,secApplicationId);
    }

    /**
     * 更新变更申请表的数据
     * @param secApplication
     * @throws Exception
     */
    public void updateSecApplication(StudentSecApplication secApplication) throws Exception
    {
        //baseDaoImpl.clear();
        baseDaoImpl.update(secApplication);
    }
}
