package edu.hfu.train.service.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import edu.hfu.train.bean.AuthGrant;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.remote.AuthGrantDao;
import edu.hfu.train.service.sysset.SysStudentService;
import edu.hfu.train.util.CacheData;
import edu.hfu.train.util.DesEncrypt;
import edu.hfu.train.util.FormatUtil;
import edu.hfu.train.util.RandomNum;

@Component
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
	private final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);
	@Resource
	SysStudentService student;
	@Resource
	AuthGrantDao authGrantDao;

	@Value("${auth.grantCode}") String grantCode;//本系统的授权代码
	
	public List<AuthGrant> getAllGrant(){
		return authGrantDao.getAllGrant(CacheData.getAccessCode(),grantCode);
	}
	
	public Map<String,Object> userReqAuth(String userCode ,String sysGrantCode ){
		String nonce=RandomNum.createRandomString(24)+userCode;
		nonce=DesEncrypt.getEncString(nonce);
		return authGrantDao.userReqAuth(nonce,sysGrantCode);
	}
	@SuppressWarnings("unchecked")
	@Override
	public UserDetails loadUserByUsernameAndDomain(String userCode, String userType) throws UsernameNotFoundException,Exception {
		if (null!=userType) {
			UserDetails usd=null;
			if ("teacher".equals(userType)) {
				Map<String,Object> mp=userReqAuth(userCode,grantCode);
				if (null!=mp) {
					for (Map.Entry<String, Object> entry : mp.entrySet()) {
						LOG.debug("loadUserByUsername key= " + entry.getKey() + " and value= " + entry.getValue());
					}
					String mess=String.valueOf(mp.get("mess"));
					if ("succ".equals(mess)) {
						Map<String,Object> p=(Map<String, Object>) mp.get("user");
						SysStaff user=new SysStaff();
						user.setStaffId(Integer.parseInt(String.valueOf(p.get("staffId"))));
						user.setStaffDepart(String.valueOf(mp.get("depart")));
						user.setStaffParentDepart(String.valueOf(mp.get("parentDepart")));
						user.setPoststr(String.valueOf(p.get("poststr")));
						user.setPostId(String.valueOf(p.get("postId")));
						user.setStaffName(String.valueOf(p.get("staffName")));
						
						user.setDepartId(Integer.parseInt(String.valueOf(mp.get("departId"))));
						user.setParentDepartId(Integer.parseInt(String.valueOf(mp.get("parentDepartID"))));
						
						user.setUserCode(userCode);
						user.setStaffTitle(String.valueOf(p.get("staffTitle")));
						user.setUserPass(String.valueOf(p.get("userPass")));
						user.setStaffSex(String.valueOf(p.get("staffSex")));
						user.setStaffPhone(String.valueOf(p.get("staffPhone")));
						user.setStaffAddr(String.valueOf(p.get("staffAddr")));
						user.setStaffEducation(String.valueOf(p.get("staffEducation")));
						user.setStaffNational(String.valueOf(p.get("staffNational")));
						user.setEntryDate(FormatUtil.strToDate(String.valueOf(p.get("entryDate")), "yyyy-MM-dd"));
						user.setStaffBirthDay(FormatUtil.strToDate(String.valueOf(p.get("staffBirthDay")), "yyyy-MM-dd"));
						user.setAccessCode(DesEncrypt.getEncString(userCode+"@"+String.valueOf(mp.get("accessCode"))));
						
						List<Map<String,Object>> userGrants_ls=(List<Map<String,Object>>)mp.get("grants");
						List<AuthGrant> userGrants=new ArrayList<>();
						for(Map<String,Object> gt:userGrants_ls) {
							AuthGrant ag=new AuthGrant();
							ag.setGrantCode(String.valueOf(gt.get("grantCode")));
							ag.setGrantName(String.valueOf(gt.get("grantName")));
							ag.setParentGrantCode(String.valueOf(gt.get("parentGrantCode")));
							userGrants.add(ag);
						}
						List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
						boolean permit=false;
						for(AuthGrant grant:userGrants) {
							LOG.debug("grant:"+grant.getGrantCode()+",parentCode:"+grant.getParentGrantCode());
							if ((null==grant.getParentGrantCode()||
									"null".equals(grant.getParentGrantCode())||
									"".equals(grant.getParentGrantCode()))&&grant.getGrantCode().equals(grantCode)) {
								permit=true;
							}
							GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+grant.getGrantCode());
			                grantedAuthorities.add(grantedAuthority);
						}
						if (permit) {
							usd=new MyUserDetails(userCode,user.getUserPass(), grantedAuthorities,user,userType);
							return usd;
						}else {
							throw new RuntimeException("用户"+userCode+",无权访问");
						}
					}else {
						throw new RuntimeException(mess);
					}
				}else {
					throw new RuntimeException("username: " + userCode + " 不存在..");
				}
				
				
			}else if ("student".equals(userType)) {
				SysStudent stu =new SysStudent();
				stu.setStudentNo(userCode);
				List<SysStudent> ls=student.getStudentByCon(stu);
				if (null==ls||ls.size()==0) {
					throw new java.lang.RuntimeException("学号或密码不正确！");
				}else {
					SysStudent student=ls.get(0);
					//增加默认权限（以后添加）
					GrantedAuthority grantedAuthority1 = new SimpleGrantedAuthority("ROLE_01");
					GrantedAuthority grantedAuthority2 = new SimpleGrantedAuthority("ROLE_STUDENT");
					List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
					//增加默认权限（以后添加）
			        grantedAuthorities.add(grantedAuthority1);
			        grantedAuthorities.add(grantedAuthority2);
			        //admin/admin
			        usd=new MyUserDetails(userCode, student.getPassword(), grantedAuthorities,student,userType) ;
				}
			}
			return usd;
		}else {
			throw new java.lang.RuntimeException("不识别的用户类型！");
		}
	}

}
