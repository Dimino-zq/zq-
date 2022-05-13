package edu.hfu.train.dao.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.SysRecruitPlan;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class StudentTrainInformationDao {

	@Resource
	BaseDaoImpl baseDaoImpl;
	/**
	 * 全文搜索
	 * @param searchText
	 * @return
	 * @throws Exception
	 */
	public List<SysRecruitPlan> getStudentTrainInformationByCon(String searchText,int pageNo,int pageSize) throws Exception {
		String sql = "select DISTINCT plan.* from SysRecruitPlan as plan,SysPlanDetail as detail,SysTrainCycle as cycle "
						+ "where plan.planId = detail.planId "
						+ "and plan.cycle_cycleId=cycle.cycleId "
						+ "and cycle.status = '进行中' ";
		List<Object> params=new ArrayList<>();	
		if (null!=searchText&&searchText.length()>0) {
			sql+= "and (MATCH(plan.planDesc) AGAINST (?0) "
					+ "or MATCH(detail.stationName) AGAINST(?1) "
					+ "or MATCH(detail.majorName) AGAINST(?2)) ";
			params.add(searchText);
			params.add(searchText);
			params.add(searchText);
		}
		
		return baseDaoImpl.findBeanBySQL(sql,SysRecruitPlan.class,pageNo,pageSize,params.toArray());
	}
	public Integer getStudentTrainInformationCountByCon(String searchText) throws Exception {
		String sql = "select DISTINCT plan.* from SysRecruitPlan as plan,SysPlanDetail as detail,SysTrainCycle as cycle "
						+ "where plan.planId = detail.planId "
						+ "and plan.cycle_cycleId=cycle.cycleId "
						+ "and cycle.status = '进行中' ";
		List<Object> params=new ArrayList<>();	
		if (null!=searchText&&searchText.length()>0) {
			sql+= "and (MATCH(plan.planDesc) AGAINST (?0) "
					+ "or MATCH(detail.stationName) AGAINST(?1) "
					+ "or MATCH(detail.majorName) AGAINST(?2)) ";
			params.add(searchText);
			params.add(searchText);
			params.add(searchText);
		}
		sql="select count(1) as rtnCount from ("+sql+") a";
		List<Map<String, Object>> ls= baseDaoImpl.findBySQL(sql, params.toArray());
		Integer rnt=Integer.parseInt(String.valueOf(ls.get(0).get("rtnCount")));
		return rnt;
	}
	/**
	 * 获取所有
	 * @return
	 * @throws Exception
	 */
	public List<SysRecruitPlan> getAllInformationByCon(int pageNo,int pageSize) throws Exception {
		String hql = "from  SysRecruitPlan as plan "
				+ "where plan.cycle.status = '进行中' "
				+ "order by plan.company.currentLvl";
		return baseDaoImpl.findByPage(hql,pageNo,pageSize);
	}
	/**
	 * 获取总数
	 * @return
	 * @throws Exception
	 */
	public Integer getAllInformationCountByCon() throws Exception{
		String hql = "from  SysRecruitPlan as plan "
				+ "where plan.cycle.status = '进行中' ";
		return baseDaoImpl.queryBeanCountByHql(hql,null);
	}

}
