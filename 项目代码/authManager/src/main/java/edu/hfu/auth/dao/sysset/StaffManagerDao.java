package edu.hfu.auth.dao.sysset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import edu.hfu.auth.dao.base.BaseDaoImpl;
import edu.hfu.auth.entity.SysStaff;
import org.springframework.util.StringUtils;

@Repository
public class StaffManagerDao {
	@Resource
	private BaseDaoImpl dao;

	public List<SysStaff> getStaffByCode(String userCode, String userPass, String admin) throws Exception {
		if (null == userPass) {
			String hql = "from SysStaff where userCode=?0  and admin=?1";
			List<SysStaff> ls = dao.find(hql, userCode, admin);
			return ls;
		} else {
			String hql = "from SysStaff where userCode=?0 and userPass=?1 and admin=?2";
			List<SysStaff> ls = dao.find(hql, userCode, userPass, admin);
			return ls;
		}

	};

	public List<SysStaff> getStaff(SysStaff staff, Integer departId,Integer parentDepartId,int pageNo, int maxResults) throws Exception {
		String hql = "from SysStaff s ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != staff) {
			if (null != staff.getStaffName() && !"".equals(staff.getStaffName())) {
                hql+=(index==0?"where":"and")+" s.staffName  like ?"+(index++);
				params.add('%'+staff.getStaffName()+'%');
			}
			if (null != staff.getUserCode() && !"".equals(staff.getUserCode())) {
                hql+=(index==0?"where":"and")+" s.userCode=?"+(index++);
				params.add(staff.getUserCode());
			}
		}
		if (null != departId) {
            hql+=(index==0?"where":"and")+" s.depart.departId=?"+(index++);
			params.add(departId);
		}else {
			if (null != parentDepartId) {
                hql+=(index==0?"where":"and")+" (s.depart.departId=?"+(index++)+ " "
                		+ "or s.depart.parentDepart.departId=?"+(index++)+")";
				params.add(parentDepartId);
				params.add(parentDepartId);
			}
		}
		hql += "order by staffId";
		return dao.findByPage(hql, params.toArray(), pageNo, maxResults);
	}

	public Integer getStaffCount(SysStaff staff, Integer departId,Integer parentDepartId) throws Exception {
		String hql = "from SysStaff s ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != staff) {
			if (null != staff.getStaffName() && !"".equals(staff.getStaffName())) {
                hql+=(index==0?"where":"and")+" s.staffName=?"+(index++);
				params.add(staff.getStaffName());
			}
			if (null != staff.getUserCode() && !"".equals(staff.getUserCode())) {
                hql+=(index==0?"where":"and")+" s.userCode=?"+(index++);
				params.add(staff.getUserCode());
			}
		}
		if (null != departId) {
            hql+=(index==0?"where":"and")+" s.depart.departId=?"+(index++);
			params.add(departId);
		}else {
			if (null != parentDepartId) {
                hql+=(index==0?"where":"and")+" (s.depart.departId=?"+(index++)+ " "
                		+ "or s.depart.parentDepart.departId=?"+(index++)+")";
				params.add(parentDepartId);
				params.add(parentDepartId);
			}
		}
		return dao.queryBeanCountByHql(hql, params.toArray());
	}

	public Integer saveStaff(SysStaff staff) throws Exception {
		return (Integer) dao.save(staff);
	}

	public void updStaff(SysStaff staff) throws Exception {
		dao.update(staff);
	}

	public boolean delStaff(Integer staffId) throws Exception {
		String hql = "delete SysStaff where staffId=?0";
		int st = dao.bulkUpdate(hql, staffId);
		if (st == 1) {
			return true;
		} else {
			return false;
		}
	}

	public SysStaff getStaffByCode(String userCode) throws Exception {
		String hql = "from SysStaff where userCode = ?0 ";
		List<SysStaff> ls = dao.find(hql, userCode);
		if (ls != null && ls.size() > 0) {
			return ls.get(0);
		} else {
			return null;
		}
	}
	
	public SysStaff getStaffById(Integer staffId) throws Exception {
		return dao.get(SysStaff.class, staffId);
	}

	public List<SysStaff> getStaffByCode(String[] userCodes) throws Exception {
		String hql = "from SysStaff where userCode in (?0) ";
		List<SysStaff> ls = dao.find(hql, new Object[] {userCodes});
		return ls;
	}
	
	/**
	 * 查询本部门的员工
	 * @param departName
	 * @return
	 * @throws Exception
	 */
	public List<SysStaff> getStaffByDepart(String departName)throws Exception{
		String hql="";
		if(null!=departName) {
			hql ="from SysStaff o "
					+ "where o.admin='用户'  and  ((o.depart.departName=?0 and o.depart.parentDepart is null) "
					+ "or o.depart.parentDepart.departName=?1)  ";
			return dao.find(hql, departName,departName);
		}else {//所有
			hql ="from SysStaff o where o.admin='用户'";
			return dao.find(hql);
		}
		
	}
	/**
	 * 根据部门名称获取员工 分页
	 * @param departName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<SysStaff> getStaffByDepart(String staffName,String departName,int pageNo,int pageSize)throws Exception{
		String hql="from SysStaff o where o.admin='用户' ";
		List<Object> params = new ArrayList<Object>();

		if(!StringUtils.isEmpty(departName)) {
			hql+="and  ((o.depart.departName=?0 and o.depart.parentDepart is null) "
					+ "or o.depart.parentDepart.departName=?1)";
			params.add(departName);
			params.add(departName);
		}
        if(!StringUtils.isEmpty(staffName)) {
		    int index =0;
            if(!StringUtils.isEmpty(departName)){
                index =2 ;
            }
            hql+=" and o.staffName=?" + index;
            params.add(staffName);
        }
        System.out.println("=============== " +hql);

		return dao.findByPage(hql, params.toArray(), pageNo, pageSize);
	}
	
	public int getStaffByDepartCount(String staffName,String departName)throws Exception{
		String hql="from SysStaff o where o.admin='用户' ";
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isEmpty(departName)) {
			hql+="and  ((o.depart.departName=?0 and o.depart.parentDepart is null) "
					+ "or o.depart.parentDepart.departName=?1)";
			params.add(departName);
			params.add(departName);
		}
		if(!StringUtils.isEmpty(staffName)) {
			int index =0;
			if(!StringUtils.isEmpty(departName)){
				index =2 ;
			}
			hql+=" and o.staffName=?" + index;
			params.add(staffName);
		}
		return dao.queryBeanCountByHql(hql, params.toArray());
	}
	
	/**
	 * 查询本部门的员工
	 * @param departName
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Integer getStaffByDepart(String departName,Date beginDate)throws Exception{
		String hql ="select count(o) from SysStaff o "
				+ "where o.admin='用户'  and  ((o.depart.departName=?0 and o.depart.parentDepart is null) "
				+ "or o.depart.parentDepart.departName=?1) ";
		int count=0;
		if (null!=beginDate) {
			hql+=" and o.entryDate<= ?2 and postType='教学岗'";
			List<Object> ls=dao.find(hql, departName,departName,beginDate);
			count=Integer.valueOf(ls.get(0).toString());
		}else {
			List<Object> ls=dao.find(hql, departName,departName);
			count=Integer.valueOf(ls.get(0).toString());
		}
		return count;
	}
	/**
	 * 获取本校所有 应参与达标的人
	 * @param departName
	 * @param beginDate
	 * @return
	 * @throws Exception
	 */
	public Integer getAllStaff(Date beginDate)throws Exception{
		String hql ="select count(o) from SysStaff o "
				+ "where o.admin='用户'  and o.entryDate<= ?0 and postType='教学岗'";
		List<Object> ls=dao.find(hql,beginDate);
		int count=Integer.valueOf(ls.get(0).toString());
		return count;
	}
	
	public Integer updUserPassword(String userCode,String newPass)throws Exception{
		String hql="update SysStaff set userPass=?0 where userCode=?1";
		return dao.bulkUpdate(hql, newPass,userCode);
	}
	
}
