package edu.hfu.train.dao.sysset;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.SysDepartTrainCycle;
import edu.hfu.train.bean.SysTrainCycle;
import edu.hfu.train.dao.base.BaseDaoImpl;

@Repository
public class SysTrainCycleDao {
	@Resource
	private BaseDaoImpl dao;

	public List<SysDepartTrainCycle> getAllSysTrainCycle(String cycleId) throws Exception {
		String hql = "from SysDepartTrainCycle sysDeptTraCyc";
		if ((cycleId == "") || (cycleId == null)) {

		} else {
			hql += " where sysDeptTraCyc.sysTrainCycle.cycleId='" + cycleId + "'";
		}
		return dao.find(hql);
	}

	// 查询
	public List<SysTrainCycle> getSysTrainCycleByCon(SysTrainCycle cycle) throws Exception {
		String hql = "from SysTrainCycle ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != cycle) {
			if (null != cycle.getStatus() && !"".equals(cycle.getStatus())) {
				hql += (index == 0 ? "where" : "and") + "  status=?" + index++;
				params.add(cycle.getStatus());
			}
		}
		hql += " order by cycleId";
		return dao.find(hql, params.toArray());
	}

	public List<SysTrainCycle> getSysTrainCycleByConForSave(SysTrainCycle cycle) throws Exception {
		String hql = "from SysTrainCycle ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != cycle) {
			if (null != cycle.getStatus() && !"".equals(cycle.getStatus())) {
				hql += (index == 0 ? "where" : "and") + "  status=?" + index++;
				params.add(cycle.getStatus());
			}
		}
		hql += " order by cycleId";
		return dao.find(hql, params.toArray());
	}

	public List<SysDepartTrainCycle> getSysTrainCycleByCon(SysTrainCycle cycle, int pageNo, int pageSize)
			throws Exception {
		String hql = "from SysTrainCycle ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != cycle) {
			if (null != cycle.getStatus() && !"".equals(cycle.getStatus())) {
				hql += (index == 0 ? "where" : "and") + "  status=?" + index++;
				params.add(cycle.getStatus());
			}
		}
		// hql += " order by cycleId";
		return dao.findByPage(hql, params.toArray(), pageNo, pageSize);
	}

	public Integer getSysTrainCycleCountByCon(SysTrainCycle cycle) throws Exception {
		String hql = "from SysTrainCycle ";
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		if (null != cycle) {
			if (null != cycle.getStatus() && !"".equals(cycle.getStatus())) {
				hql += (index == 0 ? "where" : "and") + "  status=?" + index++;
				params.add(cycle.getStatus());
			}
		}
		hql += " order by cycleId";
		return dao.queryBeanCountByHql(hql, params.toArray());
	}

	// 增加
	public void saveSysTrainCycle(SysTrainCycle cycle) throws Exception {
		dao.save(cycle);
	}

	// 修改
	public void updSysTrainCycle(SysTrainCycle cycle) throws Exception {
		dao.update(cycle);
	}

	// 删除
	public void delSysTrainCycle(SysTrainCycle cycle) throws Exception {
		String hql = "delete SysTrainCycle where cycleId=?0";
		dao.bulkUpdate(hql, cycle.getCycleId());
	}

	public SysTrainCycle GetSysTrainCycleById(SysTrainCycle cycle) throws Exception {
		String hql = "from SysTrainCycle where cycleId = " + cycle.getCycleId();
		return (SysTrainCycle) dao.find(hql).get(0);
	}

	public int updSysTrainCycleAll() throws Exception {

		String hql = "update SysTrainCycle u set u.status='已结束' where u.status='进行中'";
		return dao.bulkSqlUpdate(hql);
	}

	public List<SysDepartTrainCycle> getMajorSysTrainCycleByCon(SysTrainCycle cycle,
			SysDepartTrainCycle sysDepartTrainCycle) throws Exception {
		int index = 0;
		List<Object> params = new ArrayList<Object>();
		String hql = "from SysDepartTrainCycle sysDeptTraCyc";
		if (cycle != null) {
			if (null != cycle.getCycleId() && !"".equals(cycle.getCycleId())) {
				hql += (index == 0 ? " where" : " and") + "  sysDeptTraCyc.sysTrainCycle.cycleId=?" + index++;
				params.add(cycle.getCycleId());
			}
		}
		if (sysDepartTrainCycle != null) {
			if (null != sysDepartTrainCycle.getMajorName() && !"".equals(sysDepartTrainCycle.getMajorName())) {
				hql += (index == 0 ? " where" : " and") + "  majorName=?" + index++;
				params.add(sysDepartTrainCycle.getMajorName());
			}
		}
		return dao.find(hql, params.toArray());
	}

	public void saveSysDepartTrainCycle(SysDepartTrainCycle sysDepartTrainCycle, String cycleId) throws Exception {
		dao.save(sysDepartTrainCycle);

	}

	public void updSysDepartTrainCycle(SysDepartTrainCycle sysDepartTrainCycle, String cycleId) throws Exception {
		// TODO Auto-generated method stub
		dao.update(sysDepartTrainCycle);
	}

}
