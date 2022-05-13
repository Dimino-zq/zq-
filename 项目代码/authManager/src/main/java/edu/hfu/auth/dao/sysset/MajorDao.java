package edu.hfu.auth.dao.sysset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.auth.dao.base.BaseDaoImpl;
import edu.hfu.auth.entity.SysMajor;

/**
 * @author Shaocc
 *
 */
@Repository
public class MajorDao{
	
	@Resource
	private BaseDaoImpl dao;
	
	@SuppressWarnings("unchecked")
	public List<SysMajor> getMajor(SysMajor major) throws Exception {
		Map<String, Object> map=dao.beanToHql(major);
		return dao.find((String)map.get("hql"), ((List<Object>)map.get("param")).toArray());
	}

	/**
	 * 根据条件分页查询
	 * @param major
	 * @param pageNo
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List<SysMajor> getMajorByCon(SysMajor major, int pageNo, int maxResults) throws Exception {
		String hql="from SysMajor where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if(null!=major) {
			if(null!=major.getDepart().getDepartId()) {
				hql+=" and depart.departId = ?"+(index++);
				params.add(major.getDepart().getDepartId());
			}
			if(null!=major.getMajorName()&&!"".equals(major.getMajorName())) {
				hql+=" and majorName like ?"+(index++);
				params.add('%'+major.getMajorName()+'%');
			}
		}
		hql+="order by majorId";
		return dao.findByPage(hql,params.toArray(), pageNo,maxResults);
	}

	public Integer getMajorByConCount(SysMajor major) throws Exception {
		String hql="from SysMajor where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if(null!=major) {
			if(null!=major.getDepart().getDepartId()) {
				hql+=" and depart.departId = ?"+(index++);
				params.add(major.getDepart().getDepartId());
			}
			if(null!=major.getMajorName()&&!"".equals(major.getMajorName())) {
				hql+=" and majorName like ?"+(index++);
				params.add('%'+major.getMajorName()+'%');
			}
		}
		return dao.queryBeanCountByHql(hql, params.toArray());
	}
	
	/**
	 * 根据条件查询
	 * @param major
	 * @return
	 * @throws Exception
	 */
	public List<SysMajor> getMajorByCon(SysMajor major) throws Exception {
		String hql="from SysMajor where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if(null!=major) {
			if(null!=major.getDepart().getDepartId()) {
				hql+=" and depart.departId = ?"+(index++);
				params.add(major.getDepart().getDepartId());
			}
			if(null!=major.getMajorName()&&!"".equals(major.getMajorName())) {
				hql+=" and majorName like ?"+(index++);
				params.add('%'+major.getMajorName()+'%');
			}
		}
		hql+="order by majorId";
		return dao.find(hql,params.toArray());
	}
	
	public Integer saveMajor(SysMajor major) throws Exception {
		Integer id=(Integer) dao.save(major);
		return id;
	}

	public void updMajor(SysMajor major) throws Exception {
		dao.update(major);
	}

	public boolean delMajor(Integer majorId) throws Exception {
		String hql="delete SysMajor where majorId=?0";
		int res=dao.bulkUpdate(hql, majorId);
		if(res==1) {
			return true;
		}else  {
			return false;
		}
	}

	public Integer getMajorByName(String majorName) throws Exception {
		String hql="from SysMajor where majorName = ?0 ";
		List<SysMajor> ls=dao.find(hql,majorName);
		if(ls!=null && ls.size()>0) {
			return 1;
		}else {
			return 0;
		}
	}
	public SysMajor getMajorByNameUpd(String majorName) throws Exception {
		String hql="from SysMajor where majorName = ?0 ";
		List<SysMajor> ls=dao.find(hql,majorName);
		if(ls!=null && ls.size()>0) {
			return ls.get(0);
		}else {
			return null;
		}
	}
	

}

