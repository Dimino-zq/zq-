package edu.hfu.auth.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hanb.filterJson.annotation.PowerJsonFilter;
import com.hanb.filterJson.annotation.PowerJsonFilters;

import edu.hfu.auth.entity.AuthGrant;
import edu.hfu.auth.entity.SysDepart;
import edu.hfu.auth.entity.SysMajor;
import edu.hfu.auth.entity.SysPost;
import edu.hfu.auth.entity.SysStaff;
import edu.hfu.auth.service.grant.AuthGrantService;
import edu.hfu.auth.service.sysset.DepartService;
import edu.hfu.auth.service.sysset.MajorService;
import edu.hfu.auth.service.sysset.PostService;
import edu.hfu.auth.service.sysset.StaffManagerService;
import edu.hfu.auth.util.CacheData;
import edu.hfu.auth.util.DesEncrypt;
import edu.hfu.auth.util.FormatUtil;

@RestController
@RequestMapping(path = "/")
public class ReqAuthAction {
	private final Logger LOG = LoggerFactory.getLogger(LoginIndexAction.class);
	@Resource
	StaffManagerService staffManagerService;
	@Resource
	AuthGrantService authGrantService;
	@Resource
	DepartService departService;
	@Resource
	PostService postService;
	@Resource
	MajorService majorService;
	
	@RequestMapping(path = "/userReqAuth",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysStaff.class, include =  { "userCode" ,"staffName",
		      "poststr","userPass","staffTitle","staffSex","staffPhone","staffAddr","entryDate",
		      "staffEducation","staffNational","staffBirthDay","postId","staffId","postType"}),
		      @PowerJsonFilter(clazz = SysDepart.class, include =  {"departId","departName"}),
		      @PowerJsonFilter(clazz = AuthGrant.class, include =  {"grantCode","grantName","parentGrantCode"})})
	public Map<String,Object> userReqAuth(String userCode,String sysGrantCode){
		Map<String,Object> map=new  HashMap<>();
		try {
			//解密
			String nonce=DesEncrypt.getDesString(userCode);
			if (null==nonce) {
				map.put("mess", "用户请求信息解密失败");
			}else {
				userCode=nonce.substring(24);
				LOG.debug("用户"+userCode+"请求登录");
				SysStaff p=staffManagerService.getStaffByCode(userCode, null,"用户");
				if (null==p) {
					map.put("mess", "用户"+userCode+"不存在");
				}else {
					map.put("mess", "succ");
					map.put("user", p);
					map.put("depart", p.getDepart().getDepartName());
					map.put("departId", p.getDepart().getDepartId());
					if (null!=p.getDepart().getParentDepart()) {
						map.put("parentDepart", p.getDepart().getParentDepart().getDepartName());
						map.put("parentDepartID", p.getDepart().getParentDepart().getDepartId());
					}else {
						map.put("parentDepart",  p.getDepart().getDepartName());
						map.put("parentDepartID",  p.getDepart().getDepartId());
					}
					map.put("grants", p.getGrants(sysGrantCode));
					String  accessCode =CacheData.getAuthAccessCode(userCode);
					map.put("accessCode", accessCode);
				}
			}
		} catch (Exception e) {
			LOG.error("userReqAuth:", e);
			map.put("mess", e.toString());
		}
		return map;
	}
	/**
	 * 根据第一级代码 获取该代码下的所有权限
	 * @param grantCode
	 * @return
	 */
	@RequestMapping(path = "/getGrantBySysCode",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = AuthGrant.class, include =  {"grantCode","grantName","parentGrantCode"})})
	public List<AuthGrant> getGrantBySysCode(String grantCode){
		try {
			return authGrantService.getAllGrantByParentCode(grantCode);
		} catch (Exception e) {
			LOG.error("getGrantBySysCode:", e);
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(path = "/getGrantByUserCode",method= {RequestMethod.GET,RequestMethod.POST})
	public List<AuthGrant> getGrantByUserCode(String userCode,String sysGrantCode){
		try {
			SysStaff p=staffManagerService.getStaffByCode(userCode, null,"用户");
			if (null!=p)
				return p.getGrants(sysGrantCode);
			else
				return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取1级部门
	 * @return
	 */
	@RequestMapping(path = "/getDepartByLvl",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilter(clazz = SysDepart.class, include =  {"departId","departName"})
	public List<SysDepart> getDepartByLvl(){
		try {
			return departService.getDepartByLvl(1);
		} catch (Exception e) {
			LOG.error("getDepartByLvl:", e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取1级教学口部门
	 * @return
	 */
	@RequestMapping(path = "/getEduDepartByLvl",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilter(clazz = SysDepart.class, include =  {"departId","departName"})
	public List<SysDepart> getEduDepartByLvl(){
		try {
			return departService.getDepartByLvl(1,"教学");
		} catch (Exception e) {
			LOG.error("getDepartByLvl:", e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取二级部门
	 * @return
	 * */
	@RequestMapping(path = "/getDepartByLv2",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilter(clazz = SysDepart.class, include =  {"departId","departName"})
	public List<SysDepart> getDepartByLvl2(Integer departId){
		try {
		    LOG.debug("depatid:"+departId);
			return departService.getDepartByLv2(departId);
		} catch (Exception e) {
			LOG.error("getDepartByLv2:", e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取所有院系
	 * @author Shaocc
	 * @return
	 */
	@RequestMapping(path = "/getEduDept",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilter(clazz = SysDepart.class, include =  {"departId","departName"})
	public List<SysDepart> getEduDept(){
		 SysDepart depart = new SysDepart();
		 depart.setDepartName("系");
		 depart.setDepartLevel(1);
		 depart.setDepartType("教学");
		try {
			return 	departService.getDept(depart);
		} catch (Exception e) {
			LOG.error("getEduDept:", e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据院系获取专业
	 * @author Shaocc
	 * @return
	 */
	@RequestMapping(path = "/getMajorByDept",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilter(clazz = SysMajor.class, include =  {"majorId","majorName"})
	public List<SysMajor> getMajorByDept(Integer departId){
		 SysDepart depart = new SysDepart();
		 depart.setDepartId(departId);
		 SysMajor major = new SysMajor();
		 major.setDepart(depart);
		try {
			return 	majorService.getMajorByCon(major);
		} catch (Exception e) {
			LOG.error("getMajorByDept:", e);
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(path = "/getDepartInfo",method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> getDepartInfo(String departName,String cycleDate){
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			SysDepart depart=departService.getDepartByName(departName);
			Integer allStaff=staffManagerService.getStaffByDepart(departName, null);
			Date beginDate=FormatUtil.strToDate(cycleDate, "yyyy-MM-dd");
			Integer screStaff=staffManagerService.getStaffByDepart(departName, beginDate);
			map.put("departName", depart.getDepartName());
			map.put("departType", depart.getDepartType());
			map.put("departCharge", depart.getDepartCharge());
			map.put("allStaff", allStaff);
			map.put("screStaff", screStaff);
			map.put("mess", "succ");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mess", e.toString());
		}
		return map;
	}
	/**
	 * 用于实训平台获取 所学专业的所有老师
	 * @param departName
	 * @return
	 */
	@RequestMapping(path = "/getTeacherByDepart",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysStaff.class, include =  {"userCode","staffName","staffTitle"})})
	public List<SysStaff> getTeacherByDepart(String departName){
		try {
			List<SysStaff> ls=staffManagerService.getStaffByDepart(departName);
			return ls;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 用于科研系统 获取本部门的 所有老师
	 * @param departName
	 * @return
	 */
	@RequestMapping(path = "/getStaffByDepart",method= {RequestMethod.GET,RequestMethod.POST})
	public List<Map<String,Object>> getStaffByDepart(String staffName,String departName,int pageNo,int pageSize){
		try {
			List<SysStaff> staffs=staffManagerService.getStaffByDepart(staffName,departName,pageNo, pageSize);
			List<Map<String,Object>> ls=new ArrayList<>();
			for(SysStaff staff:staffs) {
				Map<String,Object> map=new HashMap<>();
				map.put("userCode", staff.getUserCode());
				map.put("staffName", staff.getStaffName());
				map.put("staffTitle", staff.getStaffTitle());
				map.put("poststr", staff.getPoststr());
				map.put("staffEducation", staff.getStaffEducation());
				map.put("phone", staff.getStaffPhone());
				map.put("staffDepart", staff.getDepart().getDepartName());
				if(null!=staff.getDepart().getParentDepart()) {
					map.put("staffParentDepart", staff.getDepart().getParentDepart().getDepartName());
				}
				ls.add(map);
			}
			
			return ls;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping(path = "/getStaffByDepartCount",method= {RequestMethod.GET,RequestMethod.POST})
	public int getStaffByDepartCount(String staffName,String departName) {
		try {
			return staffManagerService.getStaffByDepartCount(staffName,departName);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	@RequestMapping(path = "/getStaffInfoByCode",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysStaff.class, include =  {"userCode","entryDate","staffTitle"})})
	public List<SysStaff> getStaffInfoByCode(String userCodes){
		try {
			String[] usCodes=userCodes.split(",");
			return staffManagerService.getStaffByCode(usCodes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//所有应参与达标的人数
	@RequestMapping(path = "/getAllScreStaff",method= {RequestMethod.GET,RequestMethod.POST})
	public Integer getAllScreStaff(String cycleDate) {
		try {
			Date beginDate=FormatUtil.strToDate(cycleDate, "yyyy-MM-dd");
			return staffManagerService.getAllStaff(beginDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	@RequestMapping(path = "/getAllSysPost",method= {RequestMethod.GET,RequestMethod.POST})
	@PowerJsonFilters({@PowerJsonFilter(clazz = SysPost.class, include =  {"postId","postName"})})
	public List<SysPost> getAllSysPost(){
		try {
			List<SysPost> posts=postService.getAllPost();
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(path = "/updUserPassword",method= {RequestMethod.POST})
	public Integer updUserPassword(String userCode,String newPass ) {
		try {
			return staffManagerService.updUserPassword(userCode, newPass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 
	 * 修改人员内容
	 * @return
	 */	
	@RequestMapping(value="/updUserByCode", method= {RequestMethod.GET,RequestMethod.POST})
	public String updUserByCode(Integer staffId	,String userCode,String staffName,String staffBirthDay
			,String staffNational,String staffEducation,String entryDate,String staffPhone
			,String staffAddr,String staffSex,String staffTitle,Integer departId,
			Integer parentDepartId,String postIds, String postType){
		try {	
			LOG.debug("code:"+userCode);
			LOG.debug("postIds:"+postIds);
			SysStaff staff=new SysStaff();
			staff.setStaffId(staffId);
			staff.setUserCode(userCode);
			staff.setStaffName(staffName);
			staff.setStaffBirthDay(FormatUtil.strToDate(staffBirthDay, "yyyy-MM-dd"));
			staff.setStaffNational(staffNational);
			staff.setStaffEducation(staffEducation);
			staff.setEntryDate(FormatUtil.strToDate(entryDate, "yyyy-MM-dd"));
			staff.setStaffPhone(staffPhone);
			staff.setStaffAddr(staffAddr);
			staff.setStaffSex(staffSex);
			staff.setStaffTitle(staffTitle);
			staff.setPostType(postType);
			String[] tmp=postIds.split(",");
			Integer[] postss= new Integer[tmp.length];
			for(int i=0;i<tmp.length;i++) {
				postss[i]=Integer.parseInt(tmp[i]);
			}
			String mess="succ";
			mess=staffManagerService.updStaff(staff,departId,parentDepartId,postss);
			return mess;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
	@RequestMapping(value="/updStaffGrant", method= {RequestMethod.POST})
	public String updStaffGrant(String userCode,String grantCodes) {
		try {
			return staffManagerService.saveGrants(grantCodes,userCode);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
