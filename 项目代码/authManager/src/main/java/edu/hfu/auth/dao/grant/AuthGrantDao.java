package edu.hfu.auth.dao.grant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.auth.dao.base.BaseDaoImpl;
import edu.hfu.auth.entity.AuthGrant;
import edu.hfu.auth.entity.SysPost;

@Repository
public class AuthGrantDao {

	@Resource
	private BaseDaoImpl dao;

	@SuppressWarnings("unchecked")
	public List<AuthGrant> getGrantByCode(AuthGrant grant) throws Exception {
		Map<String, Object> map = dao.beanToHql(grant);
		return dao.find((String) map.get("hql"), ((List<Object>) map.get("param")).toArray());
	}

	public List<AuthGrant> getGrantByCode(AuthGrant grant, int pageNo, int maxResults) throws Exception {
		String hql="from AuthGrant where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if (null!=grant) {
			if (null!=grant.getGrantCode()&&!"".equals(grant.getGrantCode())) {
				hql+=" and grantCode like ?"+(index++);
				params.add('%'+grant.getGrantCode()+'%');
			}

			if (null != grant.getGrantName() && !"".equals(grant.getGrantName())) {
				hql += " and grantName like ?" + (index++);
				params.add('%' + grant.getGrantName() + '%');
			}
			if (null != grant.getBelongSys() && !"".equals(grant.getBelongSys())) {
				hql += " and belongSys = ?" + (index++);
				params.add(grant.getBelongSys());
			}
		}
		hql += "order by grantCode,grantId";
		return dao.findByPage(hql, params.toArray(), pageNo, maxResults);
	}

	public Integer getGrantByCodeCount(AuthGrant grant) throws Exception {
		String hql="from AuthGrant where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if (null!=grant) {
			if (null!=grant.getGrantCode()&&!"".equals(grant.getGrantCode())) {
				hql+=" and grantCode like ?"+(index++);
				params.add('%'+grant.getGrantCode()+'%');
			}

			if (null != grant.getGrantName() && !"".equals(grant.getGrantName())) {
				hql += " and grantName like ?" + (index++);
				params.add('%' + grant.getGrantName() + '%');
			}
			
			if (null != grant.getBelongSys() && !"".equals(grant.getBelongSys())) {
				hql += " and belongSys = ?" + (index++);
				params.add(grant.getBelongSys());
			}
		}
		return dao.queryBeanCountByHql(hql, params.toArray());
	}
	/**
	 * 通过sql 获取父级代码 名称
	 * @param grant
	 * @param pageNo
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getGrantByCodeSql(AuthGrant grant, int pageNo, int maxResults) throws Exception {
		String sql="SELECT a.grantId as grantId,a.grantCode as grantCode,a.grantName as grantName, "
				+ "a.belongSys as belongSys,a.grantLvl as grantLvl,a.parentGrantCode as parentGrantCode, "
				+ "b.grantName as parentGrantName " + 
				"FROM authgrant a  LEFT JOIN authgrant b on a.parentGrantCode=b.grantCode  where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if (null!=grant) {
			if (null!=grant.getGrantCode()&&!"".equals(grant.getGrantCode())) {
				sql+=" and a.grantCode like ?"+(index++);
				params.add('%'+grant.getGrantCode()+'%');
			}

			if (null != grant.getGrantName() && !"".equals(grant.getGrantName())) {
				sql += " and a.grantName like ?" + (index++);
				params.add('%' + grant.getGrantName() + '%');
			}
			if (null != grant.getBelongSys() && !"".equals(grant.getBelongSys())) {
				sql += " and a.belongSys = ?" + (index++);
				params.add(grant.getBelongSys());
			}
		}
		sql += " order by a.grantCode,a.grantId";
		return dao.findBySQL(sql, params.toArray(),null, pageNo, maxResults);
	}
	
	public int getGrantByCodeCountSql(AuthGrant grant) throws Exception {
		String sql="SELECT count(*) as codeCount FROM authgrant a  LEFT JOIN authgrant b on a.parentGrantCode=b.grantCode  where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if (null!=grant) {
			if (null!=grant.getGrantCode()&&!"".equals(grant.getGrantCode())) {
				sql+=" and a.grantCode like ?"+(index++);
				params.add('%'+grant.getGrantCode()+'%');
			}

			if (null != grant.getGrantName() && !"".equals(grant.getGrantName())) {
				sql += " and a.grantName like ?" + (index++);
				params.add('%' + grant.getGrantName() + '%');
			}
			if (null != grant.getBelongSys() && !"".equals(grant.getBelongSys())) {
				sql += " and a.belongSys = ?" + (index++);
				params.add(grant.getBelongSys());
			}
		}
		List<Map<String,Object>> ls=dao.findBySQL(sql, params.toArray(),null);
		return Integer.parseInt(String.valueOf(ls.get(0).get("codeCount")));
	}

	public Integer saveGrant(AuthGrant grant) throws Exception {
		Integer id = (Integer) dao.save(grant);
		return id;
	}

	public boolean delGrant(Integer grantId) throws Exception {
		String hql = "delete AuthGrant where grantId=?0";
		int res = dao.bulkUpdate(hql, grantId);
		if (res == 1) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 
	 * @param parentGrant
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getGrantCodeCount(String parentGrant) throws Exception {
		String hql="select grantCode as id,grantName  as text from AuthGrant where 1=1 ";
		int index=0;
		List<Object> param=new ArrayList<Object>();
		if (null!=parentGrant&&!"".equals(parentGrant)) {
			hql+=" and parentGrantCode=?"+(index++);
			param.add(parentGrant);
		} else {
			hql += " and parentGrantCode is null";
		}
//		hql+=" order by grantCode";
		return dao.findMap(hql, param.toArray());
	}


	public List<AuthGrant> getGrantById(String grantCode) throws Exception {
		String hql = "from AuthGrant where grantCode in (" + grantCode + ") ";
		return dao.find(hql);
	}


	/**
	 * 
	 * @param grantCode
	 * @return
	 * @throws Exception
	 */

	public Integer getGrantByCode(String grantCode) throws Exception {
		String hql="from AuthGrant where grantCode = ?0 ";
		List<AuthGrant> ls=dao.find(hql,grantCode);
		if(ls!=null && ls.size()>0) {
			return 1;
		} else {
			return 0;
		}
	}

	public Integer getGrantByName(String grantName) throws Exception {
		String hql="from AuthGrant where grantName = ?0 ";
		List<AuthGrant> ls=dao.find(hql,grantName);
		if(ls!=null && ls.size()>0) {
			return 1;
		} else {
			return 0;
		}
	}

	public void updSysPost(SysPost post) throws Exception {
		dao.update(post);
	}

	public SysPost getPostCon(Integer postId) throws Exception {
		String hql = "from SysPost where 1=1";
		List<Object> param = new ArrayList<Object>();
		if (null != postId) {
			hql += " and postId = ?0";
			param.add(postId);
		}
		return (SysPost) dao.find(hql, param.toArray()).get(0);
	}

	/**
	 * 获取某个系统的所有权限
	 * @param grantCodeLv1
	 * @return
	 * @throws Exception
	 */
	public List<AuthGrant> getAllGrantByParentCode(String  parentCode) throws Exception {
		String hql="from AuthGrant where grantCode like ?0";
		List<AuthGrant> ls=dao.find(hql,parentCode+'%');
		return ls;
	}
	
	public List<AuthGrant> getAllGrantCode() throws Exception {
		String hql="select grantCode from AuthGrant";
		return dao.find(hql);
	}
	/**
	 * 获取系统默认权限
	 * @return
	 * @throws Exception
	 */
	public List<AuthGrant> getDefaultGrantCode() throws Exception {
		String hql="from AuthGrant where isDefault=?0";
		return dao.find(hql,true);
	}
	
	public List<AuthGrant> getUpParentGrantCode(Integer grantLvl,String belongSys) throws Exception {
		String hql="from AuthGrant where  grantLvl = ?0 and belongSys=?1";
		return dao.find(hql,grantLvl,belongSys);
	}
	
	
}
