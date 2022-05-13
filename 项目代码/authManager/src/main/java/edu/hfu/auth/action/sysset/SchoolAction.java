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

import edu.hfu.auth.entity.SysSchool;
import edu.hfu.auth.service.sysset.SchoolInformationService;

@RestController
@RequestMapping(value="/sysset")
public class SchoolAction {
	private final Logger LOG = LoggerFactory.getLogger(SchoolAction.class);
	
	@Resource
	SchoolInformationService schoolInformationService;
	
	@RequestMapping(value="/initSchool", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView initSchool(){
		ModelAndView mod= new ModelAndView("/sysset/schoolinformation.btl");
		return mod;
	}
	/**
	 * 查询学校信息内容
	 * @return
	 */
	@RequestMapping(value="/querySchool", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({
		  @PowerJsonFilter(clazz = SysSchool.class, include =  {"schoolId","schoolAddr","schoolName","schoolEmail",
		      "schoolExplain","schoolDate","schoolLead","schoolType"}),})
	public Map<String,Object> querySchool(SysSchool school) {
		//		LOG.debug("dictKey:"+dict.getDictKey());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			List<SysSchool> rows=schoolInformationService.getSchoolByName(school);
//			Integer total=schoolInformationService.getSchoolByNameCount(school);
			String mess="succ";
//			rtnMap.put("total", total);
			rtnMap.put("rows", rows);
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
//			rtnMap.put("total", null);
			rtnMap.put("rows", 0);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	/**
	 * 修改数据字典内容
	 * @return
	 */
	@RequestMapping(value="/updateSchool", method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({
		  @PowerJsonFilter(clazz = SysSchool.class, include =  {"schoolId","schoolAddr","schoolName","schoolEmail",
		      "schoolExplain","schoolDate","schoolLead","schoolType"}),})
	public Map<String,Object> updateSchool(SysSchool school) {

//		LOG.debug("dictKey:"+dict.getDictKey());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			schoolInformationService.updSchool(school);
//			Integer total=schoolInformationService.getSchoolByNameCount(school);
			String mess="succ";
//			rtnMap.put("total", total);
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
//			rtnMap.put("total", null);
			rtnMap.put("rows", 0);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
}
