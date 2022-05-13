package edu.hfu.train.service.sysset;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.hfu.train.bean.SysDepartTrainCycle;
import edu.hfu.train.bean.SysTrainCycle;
import edu.hfu.train.dao.sysset.SysTrainCycleDao;

@Service
@Transactional
public class SysTrainCycleService {
	@Resource
	SysTrainCycleDao sysTrainCycledao;
	@Resource
	SysCompanyService sysCompanyService;

	public List<SysDepartTrainCycle> getAllSysTrainCycle(String cycleId) throws Exception {
		return sysTrainCycledao.getAllSysTrainCycle(cycleId);
	}

	public List<SysTrainCycle> getSysTrainCycleByCon(SysTrainCycle cycle) throws Exception {
		return sysTrainCycledao.getSysTrainCycleByCon(cycle);
	}

	public List<SysDepartTrainCycle> getMajorSysTrainCycleByCon(SysTrainCycle cycle,
			SysDepartTrainCycle sysDepartTrainCycle) throws Exception {
		return sysTrainCycledao.getMajorSysTrainCycleByCon(cycle, sysDepartTrainCycle);
	}

	public List<SysTrainCycle> getSysTrainCycleByConForSave(SysTrainCycle cycle) throws Exception {
		return sysTrainCycledao.getSysTrainCycleByConForSave(cycle);
	}

	public List<SysDepartTrainCycle> getSysTrainCycleByCon(SysTrainCycle cycle, int pageNo, int pageSize)
			throws Exception {
		return sysTrainCycledao.getSysTrainCycleByCon(cycle, pageNo, pageSize);
	}

	public Integer getSysTrainCycleCountByCon(SysTrainCycle cycle) throws Exception {
		return sysTrainCycledao.getSysTrainCycleCountByCon(cycle);
	}

	// 增加
	public String saveSysTrainCycle(SysTrainCycle traincycle) throws Exception {

		sysTrainCycledao.updSysTrainCycleAll();
		sysTrainCycledao.saveSysTrainCycle(traincycle);
		sysCompanyService.updAllCompanyLvl();
		return "succ";
	}

	// 修改
	public String updSysTrainCycle(SysTrainCycle traincycle) throws Exception {
		SysTrainCycle cyc = sysTrainCycledao.GetSysTrainCycleById(traincycle);
		cyc.setStartschoolyear(traincycle.getStartschoolyear());
		cyc.setEndschoolyear(traincycle.getEndschoolyear());
		cyc.setSemester(traincycle.getSemester());
		cyc.setPlanstartTime(traincycle.getPlanstartTime());
		cyc.setPlanendTime(traincycle.getPlanendTime());
		cyc.setConWeeks(traincycle.getConWeeks());
		cyc.setStatus(traincycle.getStatus());
		cyc.setUpdDate(new Date());
		cyc.setUpdUser(traincycle.getUpdUser());
		sysTrainCycledao.updSysTrainCycle(cyc);

		return "succ";
	}

	public void delSysTrainCycle(SysTrainCycle traincycle) throws Exception {
		sysTrainCycledao.delSysTrainCycle(traincycle);
	}

	public String saveSysDepartTrainCycle(SysDepartTrainCycle sysDepartTrainCycle, String cycleId) throws Exception {
		SysTrainCycle sysTrainCycle = new SysTrainCycle();
		// TODO Auto-generated method stub
		sysTrainCycle.setCycleId(Integer.valueOf(cycleId));
		sysDepartTrainCycle.setSysTrainCycle(sysTrainCycle);
		List<SysDepartTrainCycle> test = sysTrainCycledao.getMajorSysTrainCycleByCon(sysTrainCycle,
				sysDepartTrainCycle);
		if (test.size() == 0) {
			sysTrainCycledao.saveSysDepartTrainCycle(sysDepartTrainCycle, cycleId);
			return "succ";

		} else {
			return "hasMajor";
		}

	}

	public String updSysDepartTrainCycle(SysDepartTrainCycle sysDepartTrainCycle, String cycleId) throws Exception {
		// TODO Auto-generated method stub

		SysTrainCycle sysTrainCycle = new SysTrainCycle();
		sysTrainCycle.setCycleId(Integer.valueOf(cycleId));
		sysDepartTrainCycle.setSysTrainCycle(sysTrainCycle);
		sysTrainCycledao.updSysDepartTrainCycle(sysDepartTrainCycle, cycleId);
		return "succ";
	}
}
