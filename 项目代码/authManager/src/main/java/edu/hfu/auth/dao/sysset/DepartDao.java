package edu.hfu.auth.dao.sysset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.auth.dao.base.BaseDaoImpl;
import edu.hfu.auth.entity.AuthGrant;
import edu.hfu.auth.entity.SysDepart;
import edu.hfu.auth.entity.SysSchool;
import edu.hfu.auth.entity.SysStaff;

@Repository
public class DepartDao{
	
	@Resource
	private BaseDaoImpl dao;
	
	@SuppressWarnings("unchecked")
	public List<SysDepart> getDepart(SysDepart depart) throws Exception {
		Map<String, Object> map=dao.beanToHql(depart);
		return dao.find((String)map.get("hql"), ((List<Object>)map.get("param")).toArray());
	}

	public List<SysDepart> getDepartByAddr(SysDepart depart, int pageNo, int maxResults) throws Exception {
		String hql="select a from SysDepart a left join a.parentDepart b ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if(null!=depart) {
			if(null!=depart.getDepartAddr()&&!"".equals(depart.getDepartAddr())) {
				hql+=(index==0?"where":"and")+ " a.departAddr like ?"+(index++);
				params.add('%'+depart.getDepartAddr()+'%');
			}
			if(null!=depart.getDepartExplain()&&!"".equals(depart.getDepartExplain())) {
				hql+=(index==0?"where":"and")+"  a.departExplain like ?"+(index++);
				params.add('%'+depart.getDepartExplain()+'%');
			}
			if(null!=depart.getDepartLevel()) {
				hql+=(index==0?"where":"and")+"  a.departLevel=?"+(index++);
				params.add(depart.getDepartLevel());
			}
			if(null!=depart.getDepartPhone()&&!"".equals(depart.getDepartPhone())) {
				hql+=(index==0?"where":"and")+"  a.departPhone like ?"+(index++);
				params.add('%'+depart.getDepartPhone()+'%');
			}
			if(null!=depart.getDepartName()&&!"".equals(depart.getDepartName())) {
				hql+=(index==0?"where":"and")+"  (a.departName like ?"+(index++)+" or b.departName like ?"+(index++)+" )";
				params.add('%'+depart.getDepartName()+'%');
				params.add('%'+depart.getDepartName()+'%');
			}
		}
		hql+=" order by a.departLevel,a.departId";
		return dao.findByPage(hql,params.toArray(), pageNo,maxResults);
	}

	public Integer getDepartByAddrCount(SysDepart depart) throws Exception {
		String hql="from SysDepart a left join a.parentDepart b ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if(null!=depart) {
			if(null!=depart.getDepartAddr()&&!"".equals(depart.getDepartAddr())) {
				hql+=(index==0?"where":"and")+ " a.departAddr like ?"+(index++);
				params.add('%'+depart.getDepartAddr()+'%');
			}
			if(null!=depart.getDepartExplain()&&!"".equals(depart.getDepartExplain())) {
				hql+=(index==0?"where":"and")+"  a.departExplain like ?"+(index++);
				params.add('%'+depart.getDepartExplain()+'%');
			}
			if(null!=depart.getDepartLevel()) {
				hql+=(index==0?"where":"and")+"  a.departLevel=?"+(index++);
				params.add(depart.getDepartLevel());
			}
			if(null!=depart.getDepartPhone()&&!"".equals(depart.getDepartPhone())) {
				hql+=(index==0?"where":"and")+"  a.departPhone like ?"+(index++);
				params.add('%'+depart.getDepartPhone()+'%');
			}
			if(null!=depart.getDepartName()&&!"".equals(depart.getDepartName())) {
				hql+=(index==0?"where":"and")+"  (a.departName like ?"+(index++)+" or b.departName like ?"+(index++)+" )";
				params.add('%'+depart.getDepartName()+'%');
				params.add('%'+depart.getDepartName()+'%');
			}
		}
		return dao.queryBeanCountByHql(hql, params.toArray());
	}
	
	
	public Integer saveDepart(SysDepart depart) throws Exception {
		Integer id=(Integer) dao.save(depart);
		return id;
	}

	public void updDepart(SysDepart depart) throws Exception {
		dao.update(depart);
	}

	public boolean delDepart(Integer departId) throws Exception {
		String hql="delete SysDepart where departId=?0";
		int res=dao.bulkUpdate(hql, departId);
		if(res==1) {
			return true;
		}else  {
			return false;
		}
	}


	public List<SysDepart> getDepartByLvl(Integer departLvl) throws Exception {
		String hql="from SysDepart where 1=1";
		List<Object> param=new ArrayList<Object>();
        if (null!=departLvl){
        	hql+=" and departLevel = ?0";
        	param.add(departLvl);
        }
		return dao.find(hql,param.toArray());
	}
	
	public List<SysDepart> getDepartByLvl(Integer departLvl,String departType) throws Exception {
		String hql="from SysDepart where 1=1";
		List<Object> param=new ArrayList<Object>();
		int index=0;
        if (null!=departLvl){
        	hql+=" and departLevel = ?"+(index++);
        	param.add(departLvl);
		}
        if (null!=departType&&!"".equals(departType)){
        	hql+=" and departType = ?"+(index++);
        	param.add(departType);
		}
		return dao.find(hql,param.toArray());
	}
	
	public List<SysDepart> getDepartByLv2(Integer departLv2) throws Exception {
		String hql="from SysDepart as s  where ";
		List<Object> param=new ArrayList<Object>();
		if (null!=departLv2){
			hql+=" s.parentDepart.departId= ?0";
			param.add(departLv2);
		}
		List<SysDepart> ls=dao.find(hql,param.toArray());
		return ls;
	}
	public Integer getDepartByName(String departName) throws Exception {
		String hql="from SysDepart where departName = ?0 ";
		List<SysDepart> ls=dao.find(hql,departName);
		if(ls!=null && ls.size()>0) {
			return 1;
		}else {
			return 0;
		}
	}
	public SysDepart getDepartByNameUpd(String departName) throws Exception {
		String hql="from SysDepart where departName = ?0 ";
		List<SysDepart> ls=dao.find(hql,departName);
		if(ls!=null && ls.size()>0) {
			return ls.get(0);
		}else {
			return null;
		}
	}
	
	public List<SysDepart> getDepartId(Integer parentDepart_departId) throws Exception {
		String hql="from SysDepart where 1=1";
		List<Object> param=new ArrayList<Object>();
        if (null!=parentDepart_departId){
				hql+=" and parentDepart.departId = ?0";
				param.add(parentDepart_departId);
			}
        List<SysDepart> ls=dao.find(hql,param.toArray());
		return ls;
	}
	
	/**
	 * 条件查询，不分页
	 * 条件有：系部地址，系部说明，系部级别，系部电话，系部名称
	 * @author Shaocc
	 * @param depart
	 * @return
	 * @throws Exception
	 */
	public List<SysDepart> getDept(SysDepart depart) throws Exception {
		String hql="from SysDepart where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if(null!=depart) {
			if(null!=depart.getDepartAddr()&&!"".equals(depart.getDepartAddr())) {
				hql+=" and departAddr like ?"+(index++);
				params.add('%'+depart.getDepartAddr()+'%');
			}
			if(null!=depart.getDepartExplain()&&!"".equals(depart.getDepartExplain())) {
				hql+=" and departExplain like ?"+(index++);
				params.add('%'+depart.getDepartExplain()+'%');
			}
			if(null!=depart.getDepartLevel()&&!"".equals(depart.getDepartLevel())) {
				hql+=" and departLevel=?"+(index++);
				params.add(depart.getDepartLevel());
			}
			if(null!=depart.getDepartPhone()&&!"".equals(depart.getDepartPhone())) {
				hql+=" and departPhone like ?"+(index++);
				params.add('%'+depart.getDepartPhone()+'%');
			}
			if(null!=depart.getDepartName()&&!"".equals(depart.getDepartName())) {
				hql+=" and departName like ?"+(index++);
				params.add('%'+depart.getDepartName()+'%');
			}
		}
		hql+="order by departId";
		return dao.find(hql,params.toArray());
	}
}
