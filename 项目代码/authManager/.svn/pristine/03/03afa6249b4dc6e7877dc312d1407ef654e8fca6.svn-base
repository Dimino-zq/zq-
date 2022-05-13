package edu.hfu.auth.service.sysset;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.auth.dao.sysset.PostDao;
import edu.hfu.auth.entity.AuthGrant;
import edu.hfu.auth.entity.SysDepart;
import edu.hfu.auth.entity.SysPost;

@Service
@Transactional
public class PostService {
	@Resource
	PostDao dao;
	
	public List<SysPost> getPostByName(SysPost post, Integer pageNo, Integer maxResults) throws Exception{
		return dao.getPostByName(post,pageNo,maxResults);
	}
	
	public Integer getPostByNameCount(SysPost post) throws Exception{
		return dao.getPostByNameCount(post);
	}

	public Integer savePost(SysPost post) throws Exception {
		Integer i1=dao.getPostByName(post.getPostName());
		if(i1==1) {
			return -1;
		}
		return dao.savePost(post);
	}

	public Integer updPost(SysPost post) throws Exception {
		SysPost i1=getPostByNameUpd(post.getPostName());
		if(i1!=null&&!i1.getPostId().equals(post.getPostId())) {
			return -1;
		}else {
			if (i1 == null) {
				i1 = new SysPost();
			}
			i1.setPostId(post.getPostId());
			i1.setPostName(post.getPostName());
			i1.setPostLvl(post.getPostLvl());
			i1.setPostExplain(post.getPostExplain());
			i1.setParentPost(post.getParentPost());
			dao.updPost(i1);
			return 0;
		}
	}

	public boolean delPost(Integer postId) throws Exception {
		return dao.delPost(postId);
	}
	/**
	 * 获取所有职务信息
	 * @return
	 * @throws Exception
	 */
	public List<SysPost> getAllPost() throws Exception{
		return dao.getAllPost();
	}

	public List<SysPost> getPostByCon(SysPost post) throws Exception {
		return dao.getPostByCon(post);
	}

	public List<SysPost> getPostByCon(Integer postLvl) throws Exception {
		SysPost post=new SysPost();
		post.setPostLvl(postLvl);
        return dao.getPostByCon(post);   
	}
	
	public SysPost getPostByNameUpd(String postName) throws Exception {
		return dao.getPostByNameUpd(postName);
	}
}
