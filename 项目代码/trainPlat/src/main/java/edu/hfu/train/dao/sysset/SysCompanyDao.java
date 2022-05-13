package edu.hfu.train.dao.sysset;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.bean.SysLevel;
import edu.hfu.train.bean.SysPlanDetail;
import edu.hfu.train.bean.SysRecruitPlan;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class SysCompanyDao {
	@Resource
	private BaseDaoImpl dao;

	public List<SysCompany> getAllSysCompany() throws Exception {
		String hql = "from SysCompany";
		return dao.find(hql);
	}

	/**
	 * 学生专用获取实习单位列表
	 * @return List<SysCompany>
	 * @throws Exception 
	 */
	public List<SysCompany> getAllSysCompanyForStudent() throws Exception {
		String hql = "from SysCompany where checkstate = '已审批'";
		return dao.find(hql);
	}
	
	public List<SysPlanDetail> getALLSysPlanDetail(SysCompany sysCompa) throws Exception {
		String hql = "from SysPlanDetail detail where detail.recruitPlan.company.companyId=?0  and  detail.recruitPlan.cycle.status='进行中'";
		return dao.find(hql, sysCompa.getCompanyId());
	}

	public List<SysCompany> getSysCompanyByCon(SysCompany sysCompa) throws Exception {
		String hql = "from SysCompany ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != sysCompa) {
			if (null != sysCompa.getComName() && !"".equals(sysCompa.getComName())) {
				hql += (index == 0 ? "where" : "and") + "  comName=?" + index++;
				params.add(sysCompa.getComName());
			}
			if (null != sysCompa.getSign() && !"".equals(sysCompa.getSign())) {
				hql += (index == 0 ? " where" : "and") + " sign=?" + index++;
				params.add(sysCompa.getSign());
			}
		}
		hql += " order by companyId";
		return dao.find(hql, params.toArray());
	}

	public List<SysCompany> getSysCompanyByConForImport(SysCompany sysCompa) throws Exception {
		String hql = "from SysCompany ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != sysCompa) {
			if (null != sysCompa.getComName() && !"".equals(sysCompa.getComName())) {
				hql += (index == 0 ? "where" : "and") + "  comName=?" + index++;
				params.add(sysCompa.getComName());
			}

		}
		hql += " order by companyId";
		return dao.find(hql, params.toArray());
	}

	public List<SysCompany> getSysCompanyByCon(SysCompany sysCompa, int pageNo, int pageSize) throws Exception {
		String hql = "from SysCompany ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != sysCompa) {
			if (null != sysCompa.getComName() && !"".equals(sysCompa.getComName())) {
				hql += (index == 0 ? "where" : "and") + "  comName like ?" + index++;
				params.add('%' + sysCompa.getComName() + '%');
			}
			if (null != sysCompa.getIndustry() && !"".equals(sysCompa.getIndustry())) {
				hql += (index == 0 ? "where" : "and") + "  industry like ?" + index++;
				params.add('%' + sysCompa.getIndustry() + '%');
			}
			if (null != sysCompa.getSign() && !"".equals(sysCompa.getSign())) {
				hql += (index == 0 ? " where" : "and") + " sign=?" + index++;
				params.add(sysCompa.getSign());
			}
		}
		hql += " order by companyId DESC";
		return dao.findByPage(hql, params.toArray(), pageNo, pageSize);
	}

	public Integer getSysCompanyCountByCon(SysCompany sysCompa) throws Exception {
		String hql = "from SysCompany ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != sysCompa) {
			if (null != sysCompa.getComName() && !"".equals(sysCompa.getComName())) {
				hql += (index == 0 ? "where" : "and") + "  comName=?" + index++;
				params.add(sysCompa.getComName());
			}
			if (null != sysCompa.getSign() && !"".equals(sysCompa.getSign())) {
				hql += (index == 0 ? " where" : "and") + " sign=?" + index++;
				params.add(sysCompa.getSign());
			}
		}
		hql += " order by companyId";
		return dao.queryBeanCountByHql(hql, params.toArray());
	}

	// 增加企业
	public void saveSysCompany(SysCompany sysCompa) throws Exception {
		if (null == sysCompa.getCheckstate() && "".equals(sysCompa.getCheckstate())) {
			sysCompa.setCheckstate("未审批");
		}
		if (null == sysCompa.getSign() && "".equals(sysCompa.getSign())) {
			sysCompa.setSign("否");
		}

		dao.save(sysCompa);

	}

	// 增加招聘计划
	public void saveSysPlanDetail(SysPlanDetail detail) throws Exception {
		dao.save(detail);
	}

	public void saveSysRecruitPlan(SysRecruitPlan recruit) throws Exception {
		dao.save(recruit);
	}

	public void updSysRecruitPlan(SysRecruitPlan recruit) throws Exception {
		dao.update(recruit);
	}

	public void updSysCompany(SysCompany sysCompa) throws Exception {
		dao.update(sysCompa);

	}

	public void deleteSysCompany(SysCompany sysCompa) throws Exception {
		String hql = "delete SysCompany where companyId=?0";
		dao.bulkUpdate(hql, sysCompa.getCompanyId());
	}

	public void deleteSysPlanDetail(SysPlanDetail det) throws Exception {
		String hql = "delete SysPlanDetail where detailId=?0";
		dao.bulkUpdate(hql, det.getDetailId());
	}

	public SysCompany getSysCompanyById(SysCompany sysCompa) throws Exception {
		String hql = "from SysCompany where CompanyId = " + sysCompa.getCompanyId();
		return (SysCompany) dao.find(hql).get(0);
	}

	/**
	 * 根据企业id查询该企业发布的所有招聘计划
	 * 
	 * @author tomset
	 * @param companyId
	 *            Integer
	 * @return List
	 * @throws Exception
	 */
	public List<SysRecruitPlan> getAllPlanByCompany(Integer companyId) throws Exception {
		String hql = "from SysRecruitPlan where company.companyId = ?0 order by cycle.startschoolyear desc";
		return dao.find(hql, companyId);
	}

	public SysRecruitPlan getCurrentSysRecruitPlan(Integer companyId, Integer cycleId) throws Exception {
		String hql = "from SysRecruitPlan where company.companyId = ?0 and cycle.cycleId=?1 ";
		List<SysRecruitPlan> ls = dao.find(hql, companyId, cycleId);
		if (null != ls && ls.size() > 0) {
			return ls.get(0);
		} else {
			return null;
		}
	}

	public SysRecruitPlan getPlanById(Integer planId) throws Exception {
		return dao.get(SysRecruitPlan.class, planId);
	}

	/**
	 * 更新指定企业的等级信息
	 * 
	 * @author tomset
	 * @param companyId
	 * @throws Exception
	 */
	public void updCompanyLvl(Integer companyId) throws Exception {
		String hql = "update SysCompany s1 set " + "addupScore = ( "
				+ "select IFNULL(COUNT(distinct recruitPlan.planId),0)*4+IFNULL(count(detailId),0)*2+IFNULL(sum(planStu),0)*1+IFNULL(sum(actualStu),0)*3 "
				+ "from SysPlanDetail where recruitPlan.company.companyId = ?0 ), " + "currentLvl = ("
				+ "select lvlName from SysLevel where minScore = (select MAX(minScore) from SysLevel where minScore <=s1.addupScore)) "
				+ "where s1.companyId = ?1";
		dao.bulkUpdate(hql, companyId, companyId);
	}

	/**
	 * 更新所有企业的等级
	 * 
	 * @author tomset
	 * @throws Exception
	 */
	public void updAllCompanyLvl() throws Exception {
		String sql = "update SysCompany s1 set " + "addupScore = ( "
				+ "select IFNULL(COUNT(distinct p.planId),0)*4+IFNULL(count(d.detailId),0)*2+IFNULL(sum(d.planStu),0)*1+IFNULL(sum(d.actualStu),0)*3 "
				+ "from SysRecruitPlan p inner join SysPlanDetail d on p.planId=d.planId where p.companyId =s1.companyId ), "
				+ "currentLvl = ("
				+ "select lvlName from SysLevel where minScore <=30 order by minScore desc limit 1 ) "
				+ "where s1.companyId in (select temp.companyId from (select companyId from SysCompany) as temp)";
		dao.bulkSqlUpdate(sql);
		dao.clear(); // 防止查询出的数据还是原来的（缓存中的）
	}

	public List<SysLevel> findMinLevel() throws Exception {
		// TODO Auto-generated method stub
		String hql = "from SysLevel order by minScore";
		return dao.find(hql);
	}

	public List<SysLevel> getAllSysLevel() throws Exception {
		String hql = "from SysLevel";
		return dao.find(hql);
	}

}
