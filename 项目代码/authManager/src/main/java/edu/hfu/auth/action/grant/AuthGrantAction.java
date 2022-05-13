package edu.hfu.auth.action.grant;

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
import edu.hfu.auth.entity.SysDepart;
import edu.hfu.auth.service.grant.AuthGrantService;



@RestController//返回的是杰森串
@RequestMapping(value="/grant")
public class AuthGrantAction {
	private final Logger LOG = LoggerFactory.getLogger(AuthGrantAction.class);
	@Resource
	AuthGrantService authGrantService;
	
	
	@RequestMapping(value="/initAuthGrant", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView initAuthGrant(){
		
		ModelAndView mod= new ModelAndView("/grant/authGrant.btl");
		
		return mod;
	}
	
	/**
	 * 查询权限管理内容
	 * @return
	 */
	@RequestMapping(value="/queryAuthGrant", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> queryAuthGrant(AuthGrant grant,Integer pageNo,Integer maxResults) {
		LOG.debug("grantCode:"+grant.getGrantCode());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			List<Map<String,Object>> rows=authGrantService.getGrantByCodeSql(grant, pageNo, maxResults);
			Integer total=authGrantService.getGrantByCodeCountSql(grant);
			String mess="succ";
			rtnMap.put("total", total);
			rtnMap.put("rows", rows);
			rtnMap.put("mess", mess);
			return rtnMap;
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("total", 0);
			rtnMap.put("rows", null);
			rtnMap.put("mess", e.toString());
			return null;
		}
		
	}
	
	/**
	 * 增加权限管理内容
	 * @return
	 */
	@RequestMapping(value="/saveAuthGrant", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> saveAuthGrant(AuthGrant grant) {
		LOG.debug("grantCode:"+grant.getGrantCode());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			Integer total=authGrantService.saveGrant(grant);
			String mess="succ";
			rtnMap.put("total", total);
			rtnMap.put("mess", mess);
		}catch(Exception e){
			e.printStackTrace();
			rtnMap.put("total", 0);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
	/**
	 * 删除权限管理内容
	 * @return
	 */
	@RequestMapping(value="/delAuthGrant", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> delAuthGrant(Integer grantId){
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			boolean total=authGrantService.delGrant(grantId);
			String mess="succ";
			rtnMap.put("total", total);
			rtnMap.put("mess", mess);
		}catch(Exception e) {
			e.printStackTrace();
			rtnMap.put("total", 0);
			String grantZhanYong="PersistenceException";
			int zhanYong = e.toString().indexOf(grantZhanYong);
			if(zhanYong!=-1) {
				rtnMap.put("mess", "该权限已被占用，无法删除");
			}else {
				rtnMap.put("mess", e.toString());
			}
			
		}
		return rtnMap;
	}
	/**
	 * 判断权限管理编号
	 * @return
	 */
	@RequestMapping(value="/queryGrantCode", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> queryGrantCode(String grantCode) {
		Map<String,Object> rtnMap=new HashMap<>();
		try {	
			Integer authgrants=authGrantService.getGrantByCode(grantCode);
			if (1==authgrants) {	
				String mess="1";
				rtnMap.put("mess", mess);
			}else {
				String	mess="0";
				rtnMap.put("mess", mess);
			} 			
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	/**
	 * 判断权限管理名字
	 * @return
	 */
	@RequestMapping(value="/queryGrantName", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> queryGrantName(String grantName) {
		Map<String,Object> rtnMap=new HashMap<>();
		try {	
			Integer authgrants=authGrantService.getGrantByName(grantName);
			if (1==authgrants) {	
				String mess="1";
				rtnMap.put("mess", mess);
			}else {
				String	mess="0";
				rtnMap.put("mess", mess);
			} 			
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
	/**
	 * 获取父级权限编号
	 * @return
	 */
	@RequestMapping(value="/getUpParentGrantCode", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = AuthGrant.class, include =  { "grantName","grantId" ,"grantCode"})})
	public Map<String,Object> getUpParentGrantCode(Integer parentLvl,String belongSys){
		 Map<String,Object> rtnMap=new HashMap<>();
		try {
			
			List<AuthGrant> rows=authGrantService.getUpParentGrantCode(parentLvl,belongSys);
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
