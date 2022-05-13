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
import edu.hfu.auth.entity.SysMajor;
import edu.hfu.auth.service.sysset.MajorService;

/**
 * @author Shaocc
 *
 */
@RestController
@RequestMapping(value="/sysset")
public class MajorAction {
	
	@Resource
	MajorService majorService;
	private final Logger LOG = LoggerFactory.getLogger(MajorAction.class);
	
	@RequestMapping(value="/initMajor", method= {RequestMethod.GET,RequestMethod.POST})
   //返回路径和页面用ModelAndView
	public ModelAndView initDictionary(){
		ModelAndView mod= new ModelAndView("/sysset/major.btl");
		return mod;
	}
	
	
	/**
	 * 查询专业信息内容
	 * @return
	 */
	@RequestMapping(value="/queryMajor", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysMajor.class, include =  { "majorId" ,"majorName","depart","majorExplain"}),
		@PowerJsonFilter(clazz = SysDepart.class, include =  {"departId","departName"})})
	public Map<String,Object> queryMajor(SysMajor major,SysDepart depart,Integer pageNo,Integer maxResults)  throws Exception{
		LOG.debug("majorId:"+major.getMajorId());
		Map<String,Object> rtnMap=new HashMap<>();
		if(major!=null)
			major.setDepart(depart);
		try {
			List<SysMajor> rows=majorService.getMajorByCon(major, pageNo, maxResults);
			Integer total=majorService.getMajorByConCount(major);
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
	 * 增加专业内容
	 * @return
	 */
	@RequestMapping(value="/saveMajor", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> saveMajor(SysMajor major,Integer departId,HttpSession session) throws Exception {
		Map<String,Object> rtnMap=new HashMap<>();
		System.out.println(departId);
		SysDepart depart = new SysDepart();
		depart.setDepartId(departId);
		try {
			major.setDepart(depart);
			Integer total=majorService.saveMajor(major);
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
	 * 修改专业内容
	 * @return
	 */
	@RequestMapping(value="/updateMajor", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> updateMajor(SysMajor major,Integer departId,HttpSession session) throws Exception {
		LOG.debug("majorId:"+major.getMajorId());
		Map<String,Object> rtnMap=new HashMap<>();
		System.out.println(departId);
		SysDepart depart = new SysDepart();
		depart.setDepartId(departId);
		try {
			major.setDepart(depart);
			majorService.updMajor(major);
			Integer total=majorService.updMajor(major);
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
	 * 删除专业内容
	 * @return
	 */
	 @RequestMapping(value="/delMajor", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> delMajor(SysMajor major) throws Exception {
		LOG.debug("majorId:"+major.getMajorId());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			majorService.delMajor(major.getMajorId());
			String mess="succ";
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			String mess=e.toString();
			rtnMap.put("mess", mess);
		}
		return rtnMap;
	}
	
	 
}
