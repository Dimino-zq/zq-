package edu.hfu.auth.dao.sysset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.auth.dao.base.BaseDaoImpl;
import edu.hfu.auth.entity.SysDepart;
import edu.hfu.auth.entity.SysPost;

@Repository
public class PostDao {
	@Resource
	private BaseDaoImpl dao;
	
	public List<SysPost> getPostByCons(SysPost post) throws Exception {
		Map<String, Object> map=dao.beanToHql(post);
		return dao.find((String)map.get("hql"), ((List<Object>)map.get("param")).toArray());
	}
	
	public List<SysPost> getPostByName(SysPost post, Integer pageNo, Integer maxResults) throws Exception{
		String hql="from SysPost where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if (null!=post) {
			if (null!=post.getPostName()&&!"".equals(post.getPostName())) {
				hql+=" and postName like ?"+(index++);
				params.add('%'+post.getPostName()+'%');
			}
			
			if (null!=post.getPostLvl()) {
				hql+=" and postLvl =?"+(index++);
				params.add(post.getPostLvl());
			}
		}
		hql+="order by postLvl,postId";
//		LOG.debug(hql);
		return dao.findByPage(hql,params.toArray(), pageNo,maxResults);
	}
	

	public Integer getPostByNameCount(SysPost post) throws Exception {
		String hql="from SysPost where 1=1 ";
		int index=0;
		List<Object> params=new ArrayList<Object>();
		if (null!=post) {
			if (null!=post.getPostName()&&!"".equals(post.getPostName())) {
				hql+=" and postName like ?"+(index++);
				params.add('%'+post.getPostName()+'%');
			}
			
			if (null!=post.getPostLvl()&&!"".equals(post.getPostLvl())) {
				hql+=" and postLvl =?"+(index++);
				params.add(post.getPostLvl());
			}
		}
		return dao.queryBeanCountByHql(hql, params.toArray());
	}


	public Integer savePost(SysPost post) throws Exception {
		Integer id=(Integer) dao.save(post);
		return id;
	}


	public void updPost(SysPost post) throws Exception {
		dao.update(post);
	}

	public boolean delPost(Integer postId) throws Exception {
		String hql="delete from SysPost where postId= ?0";
		Integer res=dao.bulkUpdate(hql, postId);
		if(res==1) {
			return true;
		}else {
		    return false;
	    }
	}

	public List<SysPost> getAllPost() throws Exception {
		String hql="from SysPost ";
		return dao.find(hql);
	}

	public List<SysPost> getPostByCon(SysPost postName) throws Exception {
		String hql="from SysPost where 1=1";
		List<Object> param=new ArrayList<Object>();
		if (null!=postName){
			if (null!=postName.getPostName()&&!"".equals(postName.getPostName())){
				hql+=" and postName like ?";
				param.add('%' +postName.getPostName() + '%');
			}
			if (null!=postName.getPostId()){
				int i=0;
				hql+=" and postId = ?"+(i++);
				param.add(postName.getPostId());
			}
			if (null!=postName.getPostLvl()){
				int i=0;
				hql+=" and postLvl = ?"+(i++);
				param.add(postName.getPostLvl());
			}
		}
		return dao.find(hql,param.toArray());
	}


	public SysPost getNameById(Integer postId) throws Exception {
		String sql="select postName from SysPost where postId= ?0 ";
		return (SysPost) dao.find(sql, postId);
	}
	
	public Integer getPostByName(String postName) throws Exception {
		String hql="from SysPost where postName = ?0 ";
		List<SysPost> ls=dao.find(hql,postName);
		if(ls!=null && ls.size()>0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public SysPost getPostByNameUpd(String postName) throws Exception {
		String hql="from SysPost where postName = ?0 ";
		List<SysPost> ls=dao.find(hql,postName);
		if(ls!=null && ls.size()>0) {
			return ls.get(0);
		}else {
			return null;
		}
	}
}
