package edu.hfu.auth.dao.sysset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import edu.hfu.auth.dao.base.BaseDaoImpl;
import edu.hfu.auth.entity.SysSchool;
import edu.hfu.auth.entity.SysStaff;


@Repository
public class SchoolInformationDao {
	private final Logger LOG = LoggerFactory.getLogger(SchoolInformationDao.class);
	@Resource
	private BaseDaoImpl dao;
	
	@SuppressWarnings("unchecked")
	public List<SysSchool> getSchoolInformation(SysSchool school) throws Exception {

		Map<String, Object> map=dao.beanToHql(school);
		return dao.find((String)map.get("hql"),((List<Object>)map.get("param")).toArray());
	}
	public List<SysSchool> getSchoolByName(SysSchool school) throws Exception {

		String hql="from SysSchool where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if (null!=school) {
			if (null!=school.getSchoolName()&&!"".equals(school.getSchoolName())) {
				hql+=" and schoolName=?"+(index++);
				params.add(school.getSchoolName());
			}
		}
		return dao.find(hql,params.toArray());
	}
	public Integer getSchoolByNameCount(SysSchool school) throws Exception {
		String hql="from SysSchool where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if (null!=school) {
			if (null!=school.getSchoolName()&&!"".equals(school.getSchoolName())) {
				hql+=" and schoolName=?"+(index++);
				params.add(school.getSchoolName());
			}
		}
		return dao.queryBeanCountByHql(hql, params.toArray());
	}
		//修改
		public void updSchool(SysSchool school) throws Exception {
			dao.update(school);
		}
		
		public SysSchool getSchool(SysSchool school) throws Exception {
			String hql="from SysSchool where 1 = 1 ";	
			List<SysSchool> ls = dao.find(hql);
			System.out.println(ls.get(0));
				 return ls.get(0);

		}
}

