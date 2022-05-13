package edu.hfu.auth.service.sysset;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.auth.dao.grant.AuthGrantDao;
import edu.hfu.auth.entity.AuthGrant;
import edu.hfu.auth.entity.SysPost;

@Service
@Transactional
public class PostGrantService {
	@Resource 
	AuthGrantDao authGrantDao;

	public List<Map<String,Object>> getAuthCodeTree() throws Exception{
		List<Map<String,Object>>  ls_grants_lv1=authGrantDao.getGrantCodeCount(null);
		for(Map<String,Object> grant1:ls_grants_lv1) {
			List<Map<String,Object>> ls_grants_lv2=authGrantDao.getGrantCodeCount(String.valueOf(grant1.get("id")));
			for(Map<String,Object> grant2:ls_grants_lv2) {
				List<Map<String,Object>> ls_grants_lv3=authGrantDao.getGrantCodeCount(String.valueOf(grant2.get("id")));
				for(Map<String,Object> grant3:ls_grants_lv3) {
					List<Map<String,Object>> ls_grants_lv4=authGrantDao.getGrantCodeCount(String.valueOf(grant3.get("id")));
					grant3.put("children", ls_grants_lv4);
				}
				grant2.put("children", ls_grants_lv3);
			}
			grant1.put("children", ls_grants_lv2);
		}
		
		return ls_grants_lv1;
	}
	public List<AuthGrant> getGrantId(String grantCode) throws Exception{
		return authGrantDao.getGrantById(grantCode);
	}
	
	public String saveGrants(String grantCode,Integer postId) throws Exception{
		SysPost posts=authGrantDao.getPostCon(postId);
		if(null == grantCode || "".equals(grantCode)) {
			posts.setGrants(null);
		}else {
			List<AuthGrant> grantIds=getGrantId(grantCode);
			posts.setGrants(grantIds);
		}
		authGrantDao.updSysPost(posts);
		return "succ";
	}
	
	public List<AuthGrant> getGrantCode() throws Exception{
		return authGrantDao.getAllGrantCode();
	}
	
}
