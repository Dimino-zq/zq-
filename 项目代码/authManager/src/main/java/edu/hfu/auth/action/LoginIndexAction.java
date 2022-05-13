package edu.hfu.auth.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.auth.util.MD5Util;
import edu.hfu.auth.entity.SysStaff;
import edu.hfu.auth.entity.xmlBean.SysDictionary;
import edu.hfu.auth.service.sysset.DictionaryService;
import edu.hfu.auth.service.sysset.StaffManagerService;

@Controller
@RequestMapping(path = "/")
public class LoginIndexAction {
	private final Logger LOG = LoggerFactory.getLogger(LoginIndexAction.class);
	
	@Resource
	StaffManagerService staffManagerService;
	@Resource
	private DictionaryService dictionaryService;
	
	@RequestMapping(value={"/","/login"}, method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView gotoLoginIndex() {
		LOG.debug("进入登录页...");
		ModelAndView mod= new ModelAndView("/login.btl");
		mod.addObject("mess", "");
		
		return mod;
	}	
	
	@RequestMapping(value={"/timeout"}, method= {RequestMethod.GET,RequestMethod.POST})
	public String timeout() {
		return "/error.btl";
	}
	@RequestMapping(value={"/userLogin"}, method= {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView userLogin(SysStaff person,HttpSession session) {
		try {
			if (null==person||null==person.getUserCode()||null==person.getUserPass()) {
				return new ModelAndView("/login.btl");
			}else {				
				String password =MD5Util.string2MD5(person.getUserPass());
				person.setUserPass(password);
				SysStaff p=staffManagerService.getStaffByCode(person.getUserCode(), person.getUserPass(),"管理员");
				if (null==p) {
					LOG.error("用户名或密码错误或者不是管理员.");
					ModelAndView mod= new ModelAndView("/login.btl");
					mod.addObject("mess", "用户名或密码错误或者不是管理员");
					return mod;
				}				
				else {
					
					session.setAttribute("userCode", person.getUserCode());
					session.setAttribute("school", p.getSchool());
					LOG.debug("进入首页...");
					return new ModelAndView("/index.btl");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("登录错误", e);
			return new ModelAndView("/login.btl");
		}
	}
	
	@RequestMapping(value={"/loginOut"}, method= {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String loginOut(HttpSession session) {
		String person_name = String.valueOf(session.getAttribute("person_name"));
		session.invalidate();
		LOG.debug("用户 "+person_name+" 退出登录");
		return "success";		
	}
	
	@RequestMapping(value="/error", method=RequestMethod.GET)
    public String error(){
		LOG.debug("发生错误...");
		return "/error.btl";
	}
	@RequestMapping(value={"/userregistration"}, method= {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView userregistration() {
		ModelAndView mod= new ModelAndView("/userregistration.btl");
		List<SysDictionary> ls=dictionaryService.getDictonaryByType("职称分类");
		mod.addObject("dicts", ls);
		List<SysDictionary> os=dictionaryService.getDictonaryByType("教育程度");
		mod.addObject("dicto", os);
		List<SysDictionary> sys=dictionaryService.getDictonaryByType("managerSys");
		mod.addObject("gosys", sys);

		List<SysDictionary> postType=dictionaryService.getDictonaryByType("职务类型");
		mod.addObject("postType", postType);
		return mod;
	}
}
