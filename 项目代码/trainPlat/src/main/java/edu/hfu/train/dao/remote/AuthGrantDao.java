package edu.hfu.train.dao.remote;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.hfu.train.bean.AuthGrant;
import edu.hfu.train.bean.SysDepart;
import edu.hfu.train.bean.SysMajor;
import edu.hfu.train.bean.SysStaff;

@FeignClient(url = "${auth.server.url}",name="userGrantDao",fallbackFactory =AuthGrantFallbackFactory.class)
public interface AuthGrantDao {

	@RequestMapping(value = "/getGrantBySysCode",method = {RequestMethod.POST})
	public List<AuthGrant> getAllGrant(@RequestHeader("Authorization") String authorization,@RequestParam(value = "grantCode") String grantCode);
	//请求用户 密码
	@RequestMapping(value = "/userReqAuth",method = {RequestMethod.POST})
	public Map<String,Object> userReqAuth(@RequestParam(value = "userCode") String userCode ,@RequestParam(value = "sysGrantCode") String sysGrantCode);
    // 获取1级部门所有信息
	@RequestMapping(value = "/getDepartByLvl",method = {RequestMethod.POST})
	public List<SysDepart> getAllDepart(@RequestHeader("Authorization") String authorization);
	
	// 获取1级教育部门所有信息
	@RequestMapping(value = "/getEduDepartByLvl",method = {RequestMethod.POST})
	public List<SysDepart> getAllEduDepart(@RequestHeader("Authorization") String authorization);
		
	//获取二级部门
	@RequestMapping(value = "/getDepartByLv2",method = {RequestMethod.POST})
	public List<SysDepart> getDepartByLvl2(@RequestHeader("Authorization") String authorization,@RequestParam(value = "departId") Integer departId);

	// 获取所有院系
	@RequestMapping(value = "/getEduDept",method = {RequestMethod.POST})
	public List<SysDepart> getAllEduDept(@RequestHeader("Authorization") String authorization);

	// 获取所有专业
	@RequestMapping(value = "/getMajorByDept",method = {RequestMethod.POST})
	public List<SysMajor> getMajorByDept(@RequestHeader("Authorization") String authorization,@RequestParam(value = "departId") Integer departId);

	
	//请求部门相关信息
	@RequestMapping(value = "/getDepartInfo",method = {RequestMethod.POST})
	public Map<String,Object> getDepartInfo(@RequestHeader("Authorization") String authorization,@RequestParam(value = "departName") String departName, @RequestParam(value = "cycleDate") String cycleDate);
	//请求员工信息userCodes为逗号分隔
	@RequestMapping(value = "/getStaffInfoByCode",method = {RequestMethod.POST})
	public List<SysStaff> getStaffInfoByCode(@RequestHeader("Authorization") String authorization,@RequestParam(value = "userCodes") String userCodes);
	
	@RequestMapping(value = "/updUserPassword",method = {RequestMethod.POST})
	public Integer updUserPassword(@RequestHeader("Authorization") String authorization,@RequestParam(value = "userCode") String userCode,@RequestParam(value = "newPass") String newPass);
	
	@RequestMapping(value = "/updUserByCode",method = {RequestMethod.POST})
	public String updUserByCode(@RequestHeader("Authorization") String authorization,@RequestParam(value = "staffId") Integer staffId,
			@RequestParam(value = "userCode") String userCode,
			@RequestParam(value = "staffName") String staffName,
			@RequestParam(value = "staffBirthDay") String staffBirthDay,
			@RequestParam(value = "staffNational") String staffNational,
			@RequestParam(value = "staffEducation") String staffEducation,
			@RequestParam(value = "entryDate") String entryDate,
			@RequestParam(value = "staffPhone") String staffPhone,
			@RequestParam(value = "staffAddr") String staffAddr,
			@RequestParam(value = "staffSex") String staffSex,
			@RequestParam(value = "staffTitle") String staffTitle,
			@RequestParam(value = "departId") Integer departId,
			@RequestParam(value = "parentDepartId") Integer parentDepartId,
			@RequestParam(value = "postIds") String postIds);
	
	
	@RequestMapping(value = "/getTeacherByDepart",method = {RequestMethod.POST})
	public List<SysStaff> getTeacherByDepart(@RequestHeader("Authorization") String authorization,@RequestParam(value = "departName") String departName);
	
	
	
}
