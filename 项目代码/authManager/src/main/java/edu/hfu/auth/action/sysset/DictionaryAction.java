package edu.hfu.auth.action.sysset;

import java.util.ArrayList;
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

import edu.hfu.auth.entity.xmlBean.SysDictionary;
import edu.hfu.auth.service.sysset.DictionaryService;

@RestController
@RequestMapping(value="/sysset")
public class DictionaryAction {
	private final Logger LOG = LoggerFactory.getLogger(DictionaryAction.class);
	
	@Resource
	DictionaryService dictionaryService;
	
	@RequestMapping(value="/initDictionary", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView initDictionary(){
		ModelAndView mod= new ModelAndView("/sysset/dictionary.btl");
		return mod;
	}
	
	/**
	 * 查询数据字典内容
	 * @return
	 */
	@RequestMapping(value="/queryDictionary", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> queryDictionary(SysDictionary dict,Integer pageNo,Integer maxResults) {
		LOG.debug("dictKey:"+dict.getDictKey());
		Map<String,Object> rtnMap=new HashMap<>();
		try {
			List<SysDictionary> rows=new ArrayList<>();
			if (null==dict.getDictType()||dict.getDictType().equals("")) {
				rows=dictionaryService.getAllDictonary();
			}else {
				rows=dictionaryService.getDictonaryByType(dict.getDictType());
			}
			Integer total=rows.size();
			String mess="succ";
			rtnMap.put("total", total);
			rtnMap.put("rows", rows);
			rtnMap.put("mess", mess);
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("total", null);
			rtnMap.put("rows", 0);
			rtnMap.put("mess", e.toString());
		}
		return rtnMap;
	}
	
	@RequestMapping(value="/getBelongSys", method= {RequestMethod.GET,RequestMethod.POST})
	public List<SysDictionary> getBelongSys(){
		List<SysDictionary> rows=dictionaryService.getDictonaryByType("managerSys");
		return rows;
	}
	
	
	@RequestMapping(value="/queryDictByType", method= {RequestMethod.GET,RequestMethod.POST})
	public  List<SysDictionary> queryDictByType(String dictType){
		try {
			LOG.debug("查询字典类型："+dictType);
			if(null!=dictType) {
				List<SysDictionary> ls=dictionaryService.getDictonaryByType(dictType);
				return ls;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
