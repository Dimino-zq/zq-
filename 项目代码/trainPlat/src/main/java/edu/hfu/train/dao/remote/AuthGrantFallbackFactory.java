package edu.hfu.train.dao.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import edu.hfu.train.bean.AuthGrant;
import edu.hfu.train.bean.SysDepart;
import edu.hfu.train.bean.SysMajor;
import edu.hfu.train.bean.SysStaff;
@Component
public class AuthGrantFallbackFactory implements FallbackFactory<AuthGrantDao> {
	private final Logger LOG = LoggerFactory.getLogger(AuthGrantFallbackFactory.class);
	/**
	 * 熔断处理，当方法报错时用该方法对错误进行处理，保证后续业务逻辑
	 */
	@Override
	public AuthGrantDao create(Throwable cause) {
		
		return new AuthGrantDao() {

			@Override
			public List<AuthGrant> getAllGrant(String authorization,String grantCode) {
				LOG.error("getAllGrant", cause);
				return null;
			}

			@Override
			public Map<String, Object> userReqAuth(String userCode,String sysGrantCode) {
				LOG.error("userReqAuth", cause);
				Map<String, Object> map=new HashMap<>();
				if (cause.toString().indexOf("TimeoutException")>-1) {
					map.put("mess", "网络超时错误");
				}else {
					map.put("mess", cause.toString());
				}
				return map;
			}

			

			@Override
			public Map<String, Object> getDepartInfo(String authorization,String departName, String cycleDate) {
				LOG.error("getDepartInfo", cause);
				return null;
			}

			@Override
			public List<SysStaff> getStaffInfoByCode(String authorization,String userCodes) {
				LOG.error("getStaffInfoByCode", cause);
				return null;
			}



			@Override
			public List<SysDepart> getDepartByLvl2(String authorization,Integer id) {
				LOG.error("getDepartByLvl2", cause);
				return null;
			}

			@Override
			public Integer updUserPassword(String authorization,String userCode, String newPass) {
				LOG.error("updUserPassword", cause);
				return null;
			}

			@Override
			public String updUserByCode(String authorization,Integer staffId, String userCode, String staffName, String staffBirthDay,
					String staffNational, String staffEducation, String entryDate, String staffPhone, String staffAddr,
					String staffSex, String staffTitle, Integer departId, Integer parentDepartId, String postIds) {
				System.out.println("staffId:"+staffId);
				LOG.error("updUserByCode", cause);
				return null;
			}

			@Override
			public List<SysDepart> getAllDepart(String authorization) {
				LOG.error("getAllDepart", cause);
				return null;
			}

			@Override
			public List<SysDepart> getAllEduDepart(String authorization) {
				LOG.error("getAllEduDepart", cause);
				return null;
			}

			@Override
			public List<SysStaff> getTeacherByDepart(String authorization, String departName) {
				LOG.error("getTeacherByDepart", cause);
				return null;
			}

			@Override
			public List<SysDepart> getAllEduDept(String authorization) {
				LOG.error("getAllEduDept", cause);
				return null;
			}

			@Override
			public List<SysMajor> getMajorByDept(String authorization, Integer departId) {
				// TODO 自动生成的方法存根
				return null;
			}
			
		};
	}

}
