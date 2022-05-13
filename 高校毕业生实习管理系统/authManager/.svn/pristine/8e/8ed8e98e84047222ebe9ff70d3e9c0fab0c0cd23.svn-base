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
import edu.hfu.auth.service.sysset.PostGrantService;
import edu.hfu.auth.service.sysset.PostService;


@RestController//返回的都是json结构
@RequestMapping(value="/postgrant")
public class PostGrantAction {
	private final Logger LOG = LoggerFactory.getLogger(PostGrantAction.class);
	
	@Resource
	PostService postService;
	
	@Resource
	PostGrantService postGrantService;
	
	@RequestMapping(value="/initPostGrant", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView initDictionary(){
		ModelAndView mod= new ModelAndView("/grant/postGrant.btl");
		return mod;
	}
	
	
	
	/**
	 * 查询职务权限分配内容
	 * @return
	 */
	@RequestMapping(value="/queryPostGrant", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysPost.class, include =  { "postId" ,"postName",
			"parentPost","postExplain","postLvl","grants:{grantCode,grantLvl}"}),
		@PowerJsonFilter(clazz = AuthGrant.class, include =  { "grantCode","grantLvl"})})
	public Map<String,Object> queryPostGrant(SysPost post,Integer pageNo,Integer maxResults) {
		LOG.debug("postName:"+post.getPostName());
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
	
	@RequestMapping(value="/getAllPost", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz =SysPost.class, include =  { "postId","postName"})
		  })
	public Map<String,Object> getAllPost(SysPost post) {
		Map<String,Object> rtnMap =new HashMap<>();
		try {
			List<SysPost> rows=postService.getPostByCon(post);
			String mess="succ";
			rtnMap.put("posts", rows);
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("rows", null);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}


	@RequestMapping(value="/getPost", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz =SysPost.class, include =  { "postId","postName"})
	  })
	public Map<String,Object> getPost(SysPost post) {
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			List<SysPost> rows=postService.getPostByCon(post);
			String mess="succ";
			rtnMap.put("posts", rows);
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("rows", null);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
	@RequestMapping(value="/getAllGrant", method= {RequestMethod.GET,RequestMethod.POST})
	public List<Map<String, Object>> getAllGrant() {
		try {
			List<Map<String, Object>> rtnMaps=postGrantService.getAuthCodeTree();
			return rtnMaps;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/saveGrants", method= {RequestMethod.GET,RequestMethod.POST})
	public String saveGrants(String grantCode,Integer postId) {
		try {
			return postGrantService.saveGrants(grantCode,postId);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	@RequestMapping(value="/getAllGrantCode", method= {RequestMethod.GET,RequestMethod.POST})
	public List<AuthGrant> getAllGrantCode(){
		try {
			return postGrantService.getGrantCode();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
