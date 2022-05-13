package edu.hfu.train.service.teacher;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.dao.sysset.ReviewLogDao;
import edu.hfu.train.dao.teacher.TeacherCheckCompanyDao;

@Service
@Transactional
public class TeacherCheckCompanyService {

	@Resource
	TeacherCheckCompanyDao teacherCheckCompanyDao;
	@Resource
	ReviewLogDao reviewLogDao;

	public List<SysCompany> getTeacherCheckSysCompanyByCon(String datasource, String teacherNo, SysCompany sysCompa,
			int pageNo, int pageSize) throws Exception {
		/*
		 * // 根据老师id查询学生学号 List<Object> studentNo =
		 * teacherCheckCompanyDao.getStudentNo(teacherNo); for (int i = 0; i <
		 * studentNo.size(); i++) { System.out.println("=============1");
		 * System.out.println(studentNo.get(i)); }
		 */

		// 学号重新编排按照学号查询企业

		// TODO Auto-generated method stub
		return teacherCheckCompanyDao.getTeacherCheckSysCompanyByCon(datasource, sysCompa, pageNo, pageSize);
	}

	public Integer getTeacherCheckSysCompanyCountByCon(String datasource, String teacherNo, SysCompany sysCompa)
			throws Exception {
		// TODO Auto-generated method stub
		return teacherCheckCompanyDao.getTeacherCheckSysCompanyCountByCon(datasource, sysCompa);
	}

}
