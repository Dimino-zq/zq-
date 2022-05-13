package edu.hfu.auth.action.sysset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hanb.filterJson.annotation.PowerJsonFilter;
import com.hanb.filterJson.annotation.PowerJsonFilters;

import edu.hfu.auth.util.MD5Util;

import edu.hfu.auth.entity.AuthGrant;
import edu.hfu.auth.entity.SysDepart;
import edu.hfu.auth.entity.SysPost;
import edu.hfu.auth.entity.SysSchool;
import edu.hfu.auth.entity.SysStaff;
import edu.hfu.auth.entity.xmlBean.SysDictionary;
import edu.hfu.auth.service.sysset.SchoolInformationService;
import edu.hfu.auth.service.sysset.DictionaryService;
import edu.hfu.auth.service.sysset.PostGrantService;

import edu.hfu.auth.service.sysset.StaffManagerService;
import javax.servlet.http.HttpSession;
@RestController
@RequestMapping(value="/sysset")
public class StaffManagerAction {	
	@Resource
	StaffManagerService staffManagerService;
	@Resource
	SchoolInformationService schoolInformationService;
	@Resource
	PostGrantService postGrantService;
	@Resource
	private DictionaryService dictionaryService;
	@Value("${register.sucess.url}")
	private String registerSucessToUrl;
	

	private final Logger LOG = LoggerFactory.getLogger(StaffManagerAction.class);
	
	@RequestMapping(value="/initStaff", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView initStaff(){
		ModelAndView mod= new ModelAndView("/sysset/staffManager.btl");
		List<SysDictionary> ls=dictionaryService.getDictonaryByType("职称分类");
		mod.addObject("dicts", ls); 
		List<SysDictionary> os=dictionaryService.getDictonaryByType("教育程度");
		mod.addObject("dicto", os);
		List<SysDictionary> postType=dictionaryService.getDictonaryByType("职务类型");
		mod.addObject("postType", postType);
		return mod;
	}
	
	/**
	 * 查询人员内容
	 * @return
	 */
	@RequestMapping(value="/queryStaff", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysStaff.class, include =  { "staffId","userCode","userType","userPass","staffName","staffBirthDay",
			"staffNational","staffEducation","staffMarry","idCard","entryDate","staffHealth","postId","postType",
			"staffPhone","staffAddr","staffSex","depart:{departId,departName,parentDepart:{departId,departName}}","staffTitle",
			"poststr","school:{schoolName}","grants:{grantCode,grantLvl}",
			"posts:{postName,postId,grants:{grantCode,grantLvl}}"}),
		@PowerJsonFilter(clazz =SysDepart.class, include =  {"departId","departName","parentDepart"}),
		@PowerJsonFilter(clazz =SysPost.class, include =  {}),
	    @PowerJsonFilter(clazz =AuthGrant.class, include =  {}),
        @PowerJsonFilter(clazz =SysSchool.class, include =  {})
		  })	
	public Map<String,Object> queryStaff(SysStaff staff,Integer departId,Integer parentDepartId,Integer pageNo,Integer maxResults) {
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			List<SysStaff> rows=staffManagerService.getStaff(staff, departId, parentDepartId,pageNo, maxResults);
			Integer total=staffManagerService.getStaffCount(staff,departId, parentDepartId,pageNo, maxResults);
			String mess="succ";
			rtnMap.put("total", total);
			rtnMap.put("rows", rows);
			mess=staffManagerService.getStaff(staff,departId,parentDepartId);
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
	 * 注册人员内容
	 * @return
	 */	
	@RequestMapping(value="/registerStaff", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> registerStaff(SysStaff staff,Integer departId,Integer parentDepartId,Integer[] postIds,SysSchool school) {
		LOG.debug("postIds:"+postIds);
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			String password =MD5Util.string2MD5(staff.getUserPass());
			staff.setUserPass(password);
			SysSchool rows= schoolInformationService.getSchool(school);
			staff.setSchool(rows);
			//默认空的
			String mess="";
			mess=staffManagerService.saveStaff(staff, departId,parentDepartId, postIds);
			rtnMap.put("mess", mess);
			rtnMap.put("registerSucessToUrl", registerSucessToUrl);
		}catch (ConstraintViolationException con){
			con.printStackTrace();
			rtnMap.put("mess", "error");
		}
		catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
	/**
	 * 增加人员内容
	 * @return
	 */	
	@RequestMapping(value="/saveStaff", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> saveStaff(SysStaff staff,Integer departId,Integer parentDepartId,Integer[] postIds,HttpSession session) {
		Map<String,Object> rtnMap=new HashMap<>();
		try {			
			String password=MD5Util.string2MD5(staff.getUserPass());
			staff.setUserPass(password);
			SysSchool school=(SysSchool) session.getAttribute("school");
			staff.setSchool(school);			
			String mess="";

			mess=staffManagerService.saveStaff(staff,departId,parentDepartId, postIds);	
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
		
	/**
	 * 修改人员内容
	 * @return
	 */	
	@RequestMapping(value="/updateStaff", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> updateStaff(SysStaff staff,Integer departId,Integer parentDepartId,Integer[] postIds) {
		Map<String,Object> rtnMap=new HashMap<>();
		String mess="";
		try {	
			mess=staffManagerService.updStaff(staff,departId,parentDepartId,postIds);
			rtnMap.put("mess", mess);
		}catch(DataIntegrityViolationException e) {
			rtnMap.put("mess","用户代码重复");
		}catch (Exception e) {
			e.printStackTrace();
			mess=e.toString();
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}

	/**
	 * 删除人员内容
	 * @return
	 */	
	@RequestMapping(value="/deleteStaff", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> deleteStaff(Integer staffId) {
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			staffManagerService.delStaff(staffId);
			String mess="succ";
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
	/**
	 * 获取所有权限
	 * @return
	 */
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
	public String saveGrants(String grantCode,Integer staffId) {
		try {
			return staffManagerService.saveGrants(grantCode,staffId);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

}
