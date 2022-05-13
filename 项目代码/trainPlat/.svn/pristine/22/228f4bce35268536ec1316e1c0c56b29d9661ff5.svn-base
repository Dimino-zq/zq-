package edu.hfu.train.action.sysset;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.train.bean.SysDepartTrainCycle;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysTrainCycle;
import edu.hfu.train.service.sysset.SysTrainCycleService;
import net.bytebuddy.asm.Advice.This;

@RestController
@RequestMapping(value = "/systraincycle")
public class SysTrainCycleAction {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Resource
	SysTrainCycleService sysTrainCycleService;

	@RequestMapping(value = "/gotoSysTrainCycle", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasRole('ROLE_0100')")
	public ModelAndView gotoSysTrainCycle() {
		ModelAndView mod = new ModelAndView("sysset/systraincycle.btl");
		return mod;
	}

	@RequestMapping(value = "/getAllSysTrainCycle", method = { RequestMethod.GET, RequestMethod.POST })
	public List<SysDepartTrainCycle> getAllSysTrainCycle(String cycleId) {
		try {
			List<SysDepartTrainCycle> a = sysTrainCycleService.getAllSysTrainCycle(cycleId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", a);
			}
			return sysTrainCycleService.getAllSysTrainCycle(cycleId);
		} catch (Exception e) {
			LOG.error("Exception:", e);
		}
		return null;
	}

	@RequestMapping(value = "/getSysTrainCycleByCon", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/getSysTrainCycleByCon','010000000')") // 判断是否有查询权限
	public Map<String, Object> getSysTrainCycleByCon(SysTrainCycle cycle, int page, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			List<SysDepartTrainCycle> ls = sysTrainCycleService.getSysTrainCycleByCon(cycle, page, rows);
			Integer count = sysTrainCycleService.getSysTrainCycleCountByCon(cycle);
			map.put("rows", ls);
			map.put("total", count);

		} catch (Exception e) {
			LOG.error("Exception:", e);
			map.put("total", 0);
			map.put("rows", null);

		}
		return map;
	}

	@RequestMapping(value = "/saveSysTrainCycle", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/saveSysTrainCycle','010000001')")
	public String saveSysTrainCycle(SysTrainCycle sysTrainCycle, HttpServletRequest request, HttpSession session) {
		String mess = "";
		try {

			String userCode = (String) session.getAttribute("userCode");
			SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
			sysTrainCycle.setCreateUser(userBackStaff.getStaffName());
			sysTrainCycle.setCreateDate(new Date());
			sysTrainCycle.setUpdDate(new Date());
			sysTrainCycle.setUpdUser(userBackStaff.getStaffName());

			mess = sysTrainCycleService.saveSysTrainCycle(sysTrainCycle);

			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "sysTrainCycle" + String.valueOf(sysTrainCycle));
			}
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/updSysTrainCycle", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/updSysTrainCycle','010000002')")
	public String updSysTrainCycle(SysTrainCycle sysTrainCycle, HttpSession session) {
		String mess = "";

		try {

			String userCode = (String) session.getAttribute("userCode");
			SysStaff userBackStaff = (SysStaff) session.getAttribute("user");

			sysTrainCycle.setUpdUser(userBackStaff.getStaffName());
			mess = sysTrainCycleService.updSysTrainCycle(sysTrainCycle);

		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/deleteSysTrainCycle", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/deleteSysTrainCycle','010000003')")
	public String deleteSysTrainCycle(SysTrainCycle sysTrainCycle) {
		String mess = "";
		try {
			sysTrainCycleService.delSysTrainCycle(sysTrainCycle);
			mess = "deletesuccess";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/saveSysDepartTrainCycle", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/saveSysDepartTrainCycle','010000004')")
	public String saveSysDepartTrainCycle(String cycleId, SysDepartTrainCycle sysDepartTrainCycle,
			HttpServletRequest request, HttpSession session) {
		String mess = "";
		try {

			mess = sysTrainCycleService.saveSysDepartTrainCycle(sysDepartTrainCycle, cycleId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "sysDepartTrainCycle:" + sysDepartTrainCycle);
				LOG.debug("{}", "cycleId:" + cycleId);
				LOG.debug("{}", "sysDepartTrainCycle" + String.valueOf(sysDepartTrainCycle));
			}
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/updSysDepartTrainCycle", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/updSysDepartTrainCycle','010000005')")
	public String updSysDepartTrainCycle(String cycleId, SysDepartTrainCycle sysDepartTrainCycle, HttpSession session) {
		String mess = "";

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "sysDepartTrainCycle:" + sysDepartTrainCycle);
				LOG.debug("{}", "cycleId:" + cycleId);
			}
			mess = sysTrainCycleService.updSysDepartTrainCycle(sysDepartTrainCycle, cycleId);

		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

}
