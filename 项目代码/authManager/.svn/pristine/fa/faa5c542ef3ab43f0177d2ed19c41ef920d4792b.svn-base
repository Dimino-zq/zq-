package edu.hfu.auth.service.grant;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.auth.dao.grant.AuthGrantDao;
import edu.hfu.auth.entity.AuthGrant;



@Service
@Transactional
public class AuthGrantService {
	@Resource
	AuthGrantDao authGrantDao;
	public List<AuthGrant> getGrantByCode(AuthGrant grant, int pageNo, int maxResults) throws Exception{
		return authGrantDao.getGrantByCode(grant, pageNo, maxResults);
	}
	public  Integer getGrantByCodeCount(AuthGrant grant) throws Exception {
		return authGrantDao.getGrantByCodeCount(grant);
	}
	/**
	 * 用sql 关联查询父级编号名称
	 * @param grant
	 * @param pageNo
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getGrantByCodeSql(AuthGrant grant, int pageNo, int maxResults) throws Exception{
		return authGrantDao.getGrantByCodeSql(grant, pageNo, maxResults);
	}
	public  Integer getGrantByCodeCountSql(AuthGrant grant) throws Exception {
		return authGrantDao.getGrantByCodeCountSql(grant);
	}
	
	public Integer saveGrant(AuthGrant grant) throws Exception {
		
		String uGrantCode=grant.getGrantCode1()+grant.getGrantCode2()+grant.getGrantCode3()+grant.getGrantCode4();
		grant.setGrantCode(uGrantCode);
		Integer i1=authGrantDao.getGrantByCode(grant.getGrantCode());
		
		if(i1==1) {
			return -1;
		}

		/*
		 * if(grant.getGrantCode().length()==4) {
		 * grant.setParentGrantCode(grant.getGrantCode().substring(0, 2)); }
		 * if(grant.getGrantCode().length()==6) {
		 * grant.setParentGrantCode(grant.getGrantCode().substring(0, 4)); }
		 * if(grant.getGrantCode().length()==9) {
		 * grant.setParentGrantCode(grant.getGrantCode().substring(0, 6)); }
		 */
		
		return authGrantDao.saveGrant(grant);
	}
	public boolean delGrant(Integer grantId) throws Exception {
		return authGrantDao.delGrant(grantId);
	}
	
	public Integer getGrantByCode(String grantCode) throws Exception {
		return authGrantDao.getGrantByCode(grantCode);
	}
	
	public Integer getGrantByName(String grantName) throws Exception {
		return authGrantDao.getGrantByName(grantName);
	}

	/**
	 * 
	 * @param parentCode
	 * @return
	 * @throws Exception
	 */
	public  List<AuthGrant> getAllGrantByParentCode(String parentCode) throws Exception{
		return authGrantDao.getAllGrantByParentCode(parentCode);
	}
	
	public List<AuthGrant> getUpParentGrantCode(Integer grantLvl,String belongSys) throws Exception {
		return authGrantDao.getUpParentGrantCode(grantLvl,belongSys);
	}
}
