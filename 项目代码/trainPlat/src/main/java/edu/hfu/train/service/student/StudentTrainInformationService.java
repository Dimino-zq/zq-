package edu.hfu.train.service.student;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.SysRecruitPlan;
import edu.hfu.train.dao.student.StudentTrainInformationDao;

@Service
@Transactional
public class StudentTrainInformationService {
	@Resource
	StudentTrainInformationDao studentTrainInformationDao;

	public List<SysRecruitPlan> getStudentTrainInformationByCon(String searchText,int pageNo,int pageSize) throws Exception {
		return studentTrainInformationDao.getStudentTrainInformationByCon(searchText,pageNo,pageSize);
	}
	public List<SysRecruitPlan> getAllInformationByCon(int pageNo,int pageSize) throws Exception {
		return studentTrainInformationDao.getAllInformationByCon(pageNo,pageSize);
	}
	public Integer getStudentTrainInformationCountByCon(String searchText) throws Exception {
		return studentTrainInformationDao.getStudentTrainInformationCountByCon(searchText);
	}
	public Integer getAllInformationCountByCon() throws Exception {
		return studentTrainInformationDao.getAllInformationCountByCon();
	}
}
