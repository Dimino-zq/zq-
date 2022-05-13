package edu.hfu.auth.action.sysset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hanb.filterJson.annotation.PowerJsonFilter;
import com.hanb.filterJson.annotation.PowerJsonFilters;

import edu.hfu.auth.entity.AuthGrant;
import edu.hfu.auth.entity.SysPost;
import edu.hfu.auth.service.sysset.PostService;

@RestController
@RequestMapping(value="/sysset")
public class PostAction {
private final Logger LOG = LoggerFactory.getLogger(PostAction.class);
	
	@Resource
	PostService postService;
	
	@RequestMapping(value="/initPost", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView initPost(){
		ModelAndView mod= new ModelAndView("/sysset/post.btl");
		return mod;
	}
	

	/**
	 * 查询数据字典内容
	 * @return
	 */
	@RequestMapping(value="/queryPost", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysPost.class, include =  { "postId" ,"postName","parentPost","postExplain","postLvl","grants"}),
		@PowerJsonFilter(clazz = AuthGrant.class, include =  { "grantCode"})})
	public Map<String,Object> queryPost(SysPost post,Integer pageNo,Integer maxResults) {
		LOG.debug("postId:"+post.getPostId());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			List<SysPost> rows=postService.getPostByName(post, pageNo, maxResults);
			Integer total=postService.getPostByNameCount(post);
			String mess="succ";
			rtnMap.put("total", total);
			rtnMap.put("rows", rows);
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("total", 0);
			rtnMap.put("rows", null);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
	/**
	 * 增加职务内容
	 * @return
	 */
	@RequestMapping(value="/savePost", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> savePost(SysPost post) {
		LOG.debug("postName:"+post.getPostName());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			String mess;
			if(post.getPostLvl()==1) {
				post.setParentPost(null);
			}
			if(null!=post.getPostName()&&!"".equals(post.getPostName())) {
			    mess="succ";
			}else {
				mess="nameError";
			}
			
			Integer total=postService.savePost(post);
			rtnMap.put("total", total);
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("total", 0);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
	/**
	 * 修改职务内容
	 * @return
	 */
	@RequestMapping(value="/updatePost", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> updatePost(SysPost post) {
		System.out.println("123213");
		System.out.println(post);
		LOG.debug("postId:"+post.getPostId());
		LOG.debug("parentPost.postId:"+post.getParentPost().getPostId());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			String mess;
			if(post.getPostLvl()==1) {
				post.setParentPost(null);
			}
			
			if(null!=post.getPostName()&&!"".equals(post.getPostName())) {
				postService.updPost(post);
			    mess="succ";
			}else {
				mess="nameError";
			}
			
			Integer total=postService.updPost(post);
			rtnMap.put("total", total);
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("total", null);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
	/**
	 * 删除职务内容
	 * @return
	 */
	@RequestMapping(value="/deletePost", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> deletePost(Integer postId) {
		LOG.debug("postId:"+postId);
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			postService.delPost(postId);
			String mess="succ";
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			String mess=e.toString();
			if (e.toString().equals("javax.persistence.PersistenceException: org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				mess="已拥有下级部门，请先删除下级部门";
			}
			rtnMap.put("mess", mess);
		}
		return rtnMap;
	}
	
	
	/**
	 * 获取职务名称
	 * @return
	 */
	@RequestMapping(value="/getAllPostName", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> getAllPostName(Integer parentLvl){
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			List<SysPost> rows=postService.getPostByCon(parentLvl);
			String mess="succ";
			rtnMap.put("rows", rows);
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("rows", 0);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
}
