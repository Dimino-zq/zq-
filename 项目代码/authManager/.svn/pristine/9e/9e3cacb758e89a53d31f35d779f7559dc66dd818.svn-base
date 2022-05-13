package edu.hfu.auth.service.sysset;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.auth.dao.sysset.SchoolInformationDao;
import edu.hfu.auth.entity.SysSchool;


@Service
@Transactional
public class SchoolInformationService {

	@Resource
	SchoolInformationDao SchoolInformationDao;
	public List<SysSchool> getSchoolByName(SysSchool school) throws Exception {
		return SchoolInformationDao.getSchoolByName(school);
	}
	
	public Integer getSchoolByNameCount(SysSchool school) throws Exception {

		return SchoolInformationDao.getSchoolByNameCount(school);
	}
	//保存
		public void updSchool(SysSchool school) throws Exception {

			SchoolInformationDao.updSchool(school);	
		}

		
		
		public SysSchool getSchool(SysSchool school) throws Exception {
			return SchoolInformationDao.getSchool(school);
		}
		
	
}
