package edu.hfu.auth.service.sysset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import edu.hfu.auth.dao.grant.AuthGrantDao;
import edu.hfu.auth.dao.sysset.StaffManagerDao;
import edu.hfu.auth.entity.AuthGrant;
import edu.hfu.auth.entity.SysDepart;
import edu.hfu.auth.entity.SysPost;
import edu.hfu.auth.entity.SysStaff;
import edu.hfu.auth.util.MD5Util;

@Service
@Transactional
public class StaffManagerService {
	@Resource
	StaffManagerDao staffManagerDao;
	@Resource
	AuthGrantDao authGrantDao;
	public SysStaff getStaffByCode(String userCode,String userPass,String admin) throws Exception {
		List<SysStaff> ls=staffManagerDao.getStaffByCode(userCode, userPass,admin);
		if (null!=ls&&ls.size()>0) {
			return ls.get(0);
		}else {
			return null;
		}	
	};
	
	public List<SysStaff> getStaff(SysStaff staff,Integer departId,Integer parentDepartId, int pageNo, int maxResults) throws Exception {
		return staffManagerDao.getStaff(staff,departId,parentDepartId, pageNo, maxResults);
	}
	public Integer getStaffCount(SysStaff staff,Integer departId,Integer parentDepartId, int pageNo, int maxResults) throws Exception {
		return staffManagerDao.getStaffCount(staff, departId, parentDepartId);
	}
	
	public String saveStaff(SysStaff staff ,Integer departId,Integer parentDepartId,Integer[] postIds) throws Exception {
		if (getStaffByCode(staff.getUserCode()) != null) {
			return "error";
		} else {
			// 增加部门
			SysDepart de = new SysDepart();
			if (null != departId) {
				de.setDepartId(departId);
			}else {
				de.setDepartId(parentDepartId);
			}
			staff.setDepart(de);
			// 增加 职务
			if (null != postIds) {
				List<SysPost> posts = new ArrayList<SysPost>();
				for (Integer id : postIds) {
					SysPost pt = new SysPost();
					pt.setPostId(id);
					posts.add(pt);
				}
				staff.setPosts(posts);
			}
			if ("用户".equals(staff.getAdmin())) {//为该用户增加默认权限
				//获取默认权限
				List<AuthGrant>  grants=authGrantDao.getDefaultGrantCode();
				staff.setGrants(grants);
			}
			staffManagerDao.saveStaff(staff);
			return "succ";
		}
	}
	
	public String updStaff(SysStaff staff,Integer departId,Integer parentDepartId,Integer[] postIds) throws Exception {
		SysStaff st=staffManagerDao.getStaffById(staff.getStaffId());
		if (null!=st) {
			// 修改部门
			SysDepart de = new SysDepart();
			if (null != departId) {
				de.setDepartId(departId);
			}else {
				de.setDepartId(parentDepartId);
			}
			st.setDepart(de);
			// 修改 职务
			if (null != postIds) {
				List<SysPost> posts = new ArrayList<SysPost>();
				for (Integer id : postIds) {
					SysPost pt = new SysPost();
					pt.setPostId(id);
					posts.add(pt);
				}
				st.setPosts(posts);
			}	
			st.setUserCode(staff.getUserCode());
			st.setStaffAddr(staff.getStaffAddr());
			st.setStaffEducation(staff.getStaffEducation());
			st.setStaffName(staff.getStaffName());
			st.setStaffNational(staff.getStaffNational());
			st.setStaffPhone(staff.getStaffPhone());
			st.setStaffSex(staff.getStaffSex());
			st.setStaffTitle(staff.getStaffTitle());
			st.setStaffBirthDay(staff.getStaffBirthDay());
			st.setStaffId(staff.getStaffId());	
			st.setEntryDate(staff.getEntryDate());
			st.setPostType(staff.getPostType());
			staffManagerDao.updStaff(st);
			return "succ";
		}else {
			return "未找到对应记录";
		}
		
		
	}
	
	public boolean delStaff(Integer staffId) throws Exception {	
		return staffManagerDao.delStaff(staffId);
	}

	public SysStaff  getStaffByCode(String userCode) throws Exception {
		return staffManagerDao.getStaffByCode(userCode);	
	}	
	//根据代码获取
	public List<SysStaff> getStaffByCode(String[] userCodes)throws Exception {
		return staffManagerDao.getStaffByCode(userCodes);
	}
	
	public List<AuthGrant> getGrantId(String grantCode) throws Exception{
		return authGrantDao.getGrantById(grantCode);
	}
	/**
	 * 根据员工id更新权限
	 * @param grantCode
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public String saveGrants(String grantCode,Integer staffId) throws Exception{		
		SysStaff staff=staffManagerDao.getStaffById(staffId);
		if(null == grantCode || "".equals(grantCode)) {
			staff.setGrants(null);
		}else {
			List<AuthGrant> grantIds=getGrantId(grantCode);
			staff.setGrants(grantIds);
		}
		staffManagerDao.updStaff(staff);
		return "succ";
	}
	/**
	 * 根据员工代码更新权限
	 * @param grantCode
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public String saveGrants(String grantCodes,String userCode) throws Exception{		
		SysStaff staff=staffManagerDao.getStaffByCode(userCode);
		if(null == grantCodes || "".equals(grantCodes)) {
			staff.setGrants(null);
		}else {
			List<AuthGrant> grantIds=getGrantId(grantCodes);
			staff.setGrants(grantIds);
		}
		staffManagerDao.updStaff(staff);
		return "succ";
	}
	
	public Integer getStaffByDepart(String departName,Date beginDate)throws Exception{
		return staffManagerDao.getStaffByDepart(departName, beginDate);
	}
	
	public List<SysStaff> getStaffByDepart(String departName)throws Exception{
		return staffManagerDao.getStaffByDepart(departName);
	}
	public List<SysStaff> getStaffByDepart(String staffName,String departName,int pageNo,int pageSize)throws Exception{
		return staffManagerDao.getStaffByDepart(staffName,departName,pageNo, pageSize);
	}
	
	public int getStaffByDepartCount(String staffName,String departName)throws Exception{
		return staffManagerDao.getStaffByDepartCount(staffName,departName);
	}
	public Integer getAllStaff(Date beginDate)throws Exception{
		return staffManagerDao.getAllStaff(beginDate);
	}
	//测试
	public String getStaff(SysStaff staff, Integer departId, Integer parentDepartId) throws Exception{
		return null;
	}
	/**
	 * 修改密码
	 * @param userCode
	 * @param newPass
	 * @return
	 * @throws Exception
	 */
	public Integer updUserPassword(String userCode,String newPass)throws Exception{
		return staffManagerDao.updUserPassword(userCode, newPass);
	}

}
