package edu.hfu.auth.action.sysset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hanb.filterJson.annotation.PowerJsonFilter;
import com.hanb.filterJson.annotation.PowerJsonFilters;

import edu.hfu.auth.entity.SysDepart;
import edu.hfu.auth.entity.SysSchool;
import edu.hfu.auth.service.sysset.DepartService;
@RestController
@RequestMapping(value="/sysset")
public class DepartAction {
	
	@Resource
	DepartService departService;
	private final Logger LOG = LoggerFactory.getLogger(DepartAction.class);
	
	@RequestMapping(value="/initDepart", method= {RequestMethod.GET,RequestMethod.POST})
   //返回路径和页面用ModelAndView
	public ModelAndView initDictionary(){
		ModelAndView mod= new ModelAndView("/sysset/depart.btl");
		return mod;
	}
	
	
	/**
	 * 查询部门信息内容
	 * @return
	 */
	@RequestMapping(value="/queryDepart", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysDepart.class, include =  { "departId" ,"departName","departCharge","departAddr","departPhone","departLevel","school","departType","departExplain","parentDepart"}),
		@PowerJsonFilter(clazz = SysSchool.class, include =  {"schoolName"})})
	public Map<String,Object> queryDepart(SysDepart depart,Integer pageNo,Integer maxResults)  throws Exception{
		LOG.debug("departId:"+depart.getDepartId());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			List<SysDepart> rows=departService.getDepartByAddr(depart, pageNo, maxResults);
			Integer total=departService.getDepartByAddrCount(depart);
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
	 * 增加部门内容
	 * @return
	 */
	@RequestMapping(value="/saveDepart", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> saveDepart(SysDepart depart,HttpSession session) throws Exception {
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			if(depart.getDepartLevel()==1) {
				depart.setParentDepart(null);
			}
			SysSchool school=(SysSchool) session.getAttribute("school");
			depart.setSchool(school);
			Integer total=departService.saveDepart(depart);
			String mess="succ";
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
	 * 修改部门内容
	 * @return
	 */
	@RequestMapping(value="/updateDepart", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> updateDepart(SysDepart depart,HttpSession session) throws Exception {
		LOG.debug("departId:"+depart.getDepartId());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			if(depart.getDepartLevel()==1) {
				depart.setParentDepart(null);
			}
			SysSchool school=(SysSchool) session.getAttribute("school");
			depart.setSchool(school);
			departService.updDepart(depart);
			Integer total=departService.updDepart(depart);
			String mess="succ";
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
	 * 删除部门内容
	 * @return
	 */
	 @RequestMapping(value="/delDepart", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> delDepart(SysDepart depart) throws Exception {
		LOG.debug("departId:"+depart.getDepartId());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			departService.delDepart(depart.getDepartId());
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
	 * 获取上级部门名称
	 * @return
	 */
	@RequestMapping(value="/getUpDepartName", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysDepart.class, include =  { "departId" ,"departName"})})
	public Map<String,Object> getUpDepartName(Integer parentLvl){
		 Map<String,Object> rtnMap=new HashMap<>();
		try {
			List<SysDepart> rows=departService.getDepartByLvl(parentLvl);
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
	 
	 @RequestMapping(value="/getDepartOne", method= {RequestMethod.GET,RequestMethod.POST})
	 @PowerJsonFilters({@PowerJsonFilter(clazz =SysDepart.class, include =  { "departId","departName"}),
			  })
		public Map<String,Object> getDepartOne(Integer departLevel) {
			Map<String,Object> rtnMap=new HashMap<>();
			try {
				List<SysDepart> rows=departService.getDepartByLvl(1);
				String mess="succ";
				rtnMap.put("departs", rows);
				rtnMap.put("mess", mess);
			} catch (Exception e) {
				e.printStackTrace();
				rtnMap.put("rows", null);
				rtnMap.put("mess", e.toString());
			}
			return rtnMap;
		}
	 
	 
	 @RequestMapping(value="/getDepartTwo", method= {RequestMethod.GET,RequestMethod.POST})
		@PowerJsonFilters({@PowerJsonFilter(clazz = SysDepart.class, include =  { "departId" ,"departName"})})
		public Map<String,Object> getDepartTwo(Integer departId){
			 Map<String,Object> rtnMap=new HashMap<>();
			try {
				List<SysDepart> rows=departService.getDepartId(departId);
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
	 
	 @RequestMapping(value="/getOneDepart", method= {RequestMethod.GET,RequestMethod.POST})
	 @PowerJsonFilters({@PowerJsonFilter(clazz =SysDepart.class, include =  { "departId","departName"}),
			  })
		public Map<String,Object> getOneDepart(Integer departLevel) {
			Map<String,Object> rtnMap=new HashMap<>();
			try {
				List<SysDepart> rows=departService.getDepartByLvl(1);
				String mess="succ";
				rtnMap.put("departs", rows);
				rtnMap.put("mess", mess);
			} catch (Exception e) {
				e.printStackTrace();
				rtnMap.put("rows", null);
				rtnMap.put("mess", e.toString());
			}
			return rtnMap;
		}
	 @RequestMapping(value="/getTwoDepart", method= {RequestMethod.GET,RequestMethod.POST})
		@PowerJsonFilters({@PowerJsonFilter(clazz = SysDepart.class, include =  { "departId" ,"departName"})})
		public Map<String,Object> getTwoDepart(Integer departId){
			 Map<String,Object> rtnMap=new HashMap<>();
			try {
				List<SysDepart> rows=departService.getDepartId(departId);
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
	 
	 /**
	  * 查询所有的系，部门级别为1，名称中有“系”
	  * @author Shaocc
	  */
	 @RequestMapping(value="/getDept", method= {RequestMethod.GET,RequestMethod.POST})
	 @PowerJsonFilters({@PowerJsonFilter(clazz = SysDepart.class, include =  { "departId" ,"departName"})})
	 public Map<String,Object> getDept()  throws Exception{
		 SysDepart depart = new SysDepart();
		 depart.setDepartName("系");
		 depart.setDepartLevel(1);
		 depart.setDepartType("教学");
		 Map<String,Object> rtnMap=new HashMap<>();
		 try {
			 List<SysDepart> rows=departService.getDept(depart);
			 String mess="succ";
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
}
