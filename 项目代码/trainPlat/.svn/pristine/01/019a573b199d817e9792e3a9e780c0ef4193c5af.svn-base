package edu.hfu.train.action.sysset;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import edu.hfu.train.bean.SysLevel;

import edu.hfu.train.service.sysset.SyslevelService;

@RestController
@RequestMapping(value = "/syslevel")
public class SyslevelAction {
	private final Logger LOG = LoggerFactory.getLogger(SysLevel.class);
	@Resource
	SyslevelService levelservice;
	
	@RequestMapping(value = "/gotolevel", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoCompany() {
		ModelAndView mod = new ModelAndView("sysset/syslevel.btl");
		return mod;
	}
	
	@RequestMapping(value = "/getAllLevel", method = { RequestMethod.GET, RequestMethod.POST })
	public List<SysLevel> getAlllevel() {

		try {
			return levelservice.getAlllevel(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//保存
	@RequestMapping(value = "/saveLevel", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveSysLevel(SysLevel syslevel) {
		String mess = "";

		try {
			mess=levelservice.saveSysLevel(syslevel);

		} catch (Exception e) {
			e.printStackTrace();
			mess = e.toString();
		}
		return mess;
	}
	//修改
	@RequestMapping(value = "/updLevel", method = { RequestMethod.GET, RequestMethod.POST })
	public String updSysLevel(SysLevel syslevel) {
		String mess = "";

		try {
			mess = levelservice.updSysLevel(syslevel);

		} catch (Exception e) {
			e.printStackTrace();
			mess = e.toString();
		}
		return mess;
	}
	//删除
	@RequestMapping(value = "/deleteLevel", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteSysLevel(SysLevel syslevel) {
		String mess = "";
		try {
			mess = levelservice.deleteSysLevel(syslevel);
		} catch (Exception e) {
			e.printStackTrace();
			mess = e.toString();
		}
		return mess;
	}

}
