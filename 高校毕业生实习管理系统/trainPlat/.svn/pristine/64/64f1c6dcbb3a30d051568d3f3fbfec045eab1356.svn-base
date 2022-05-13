package edu.hfu.train.action.sysset;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.bean.SysLevel;
import edu.hfu.train.bean.SysPlanDetail;
import edu.hfu.train.bean.SysRecruitPlan;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.bean.SysTrainCycle;
import edu.hfu.train.dao.sysset.SysCompanyDao;
import edu.hfu.train.service.sysset.FastDFSService;
import edu.hfu.train.service.sysset.SysCompanyService;
import edu.hfu.train.service.sysset.SysTrainCycleService;

@RestController
@RequestMapping(value = "/syscompany")
public class SysCompanyAction {
	private final Logger LOG = LoggerFactory.getLogger(SysCompanyAction.class);
	@Resource
	SysCompanyService sysCompanyService;
	@Resource
	SysCompanyDao syscompanydao;
	@Resource
	SysTrainCycleService sysTrainCycleService;
	@Autowired
	private FastDFSService fastDFSService;
	@RequestMapping(value = "/gotoSysCompany", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasRole('ROLE_0100')")
	public ModelAndView gotoCompany() {
		ModelAndView mod = new ModelAndView("sysset/syscompany.btl");
		return mod;
	}

	@RequestMapping(value = "/getAllSysCompany", method = { RequestMethod.GET, RequestMethod.POST })
	public List<SysCompany> getAllSysCompany() {

		try {
			return sysCompanyService.getAllSysCompany();
		} catch (Exception e) {
			LOG.error("Exception:", e);
		}
		return null;
	}

	/**
	 * 学生专用获取实习单位列表
	 * 
	 * @author tomset
	 * @return List<SysCompany>
	 */
	@RequestMapping(value = "/getAllSysCompanyForStudent", method = { RequestMethod.GET, RequestMethod.POST })
	public List<SysCompany> getAllSysCompanyForStudent() {
		try {
			return sysCompanyService.getAllSysCompanyForStudent();
		} catch (Exception e) {
			LOG.error("Exception:", e);
		}
		return null;
	}

	/**
	 * 获取当前招聘计划
	 * 
	 * @param companyId
	 */
	@RequestMapping(value = "/getCurrentSysRecruitPlan", method = { RequestMethod.POST })
	@PreAuthorize("hasPermission('/getCurrentSysRecruitPlan','010002007')")
	public Map<String, Object> getCurrentSysRecruitPlan(Integer companyId, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysTrainCycle cycleCologe = (SysTrainCycle) session.getAttribute("cycleCologe");
			if (null != cycleCologe) {
				SysRecruitPlan plan = sysCompanyService.getCurrentSysRecruitPlan(companyId, cycleCologe.getCycleId());
				if (null != plan) {
					map.put("planId", plan.getPlanId());
					map.put("planDesc", plan.getPlanDesc());
					map.put("details", plan.getDetails());
					map.put("mess", "succ");
				} else {
					map.put("mess", "noPlan");
				}
			} else {
				map.put("mess", "未找到当前实训周期");
			}

		} catch (Exception e) {
			LOG.error("Exception:", e);
			map.put("mess", e.toString());
		}
		return map;
	}

	@RequestMapping(value = "/getSysCompanyByCon", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/getSysCompanyByCon','010002000')") // 判断是否有查询权限
	public Map<String, Object> getSysCompanyByCon(HttpSession session, SysCompany sysCompa, int page, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			List<SysCompany> ls = sysCompanyService.getSysCompanyByCon(sysCompa, page, rows);
			Integer count = sysCompanyService.getSysCompanyCountByCon(sysCompa);
			SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
			userBackStaff.getStaffName();
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "StaffName:" + userBackStaff.getStaffName());
			}

			map.put("rows", ls);
			map.put("total", count);

		} catch (Exception e) {
			LOG.error("Exception:", e);
			map.put("total", 0);
			map.put("rows", null);

		}
		return map;
	}

	@RequestMapping(value = "/saveSysCompany", method = { RequestMethod.GET, RequestMethod.POST })

	public String saveSysCompany(SysCompany sysCompa, HttpServletRequest request) {
		String mess = "";

		try {
			HttpSession session = request.getSession();
			Object user = session.getAttribute("user");

			if (session.getAttribute("userType").equals("teacher")) {
				SysStaff thisStaff = (SysStaff) session.getAttribute("user");
				sysCompa.setCreateUser(thisStaff.getStaffName());
				// sysCompa.setDatasource(thisStaff.getStaffName());
				sysCompa.setDatasource(thisStaff.getStaffParentDepart());
				sysCompa.setUpdUser(thisStaff.getStaffName());
				thisStaff.getStaffName();

				mess = sysCompanyService.saveSysCompanyByHand(sysCompa);
			} else if (session.getAttribute("userType").equals("student")) {
				SysStudent student = (SysStudent) user;
				String datasource = student.getStudentNo().concat("@".concat(student.getDepart()));
				sysCompa.setCreateUser(student.getStudentNo());
				sysCompa.setDatasource(datasource);
				sysCompa.setUpdUser(student.getStudentNo());
				if (LOG.isDebugEnabled()) {
					LOG.debug("{}", "datasource:" + datasource);
				}
				mess = sysCompanyService.saveSysCompanyByHand(sysCompa);
			} else {
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "LogoPath:" + sysCompa.getLogoPath());
				LOG.debug("{}", "session.user:" + session.getAttribute("user").toString());
			}

		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/updSysCompany", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/updSysCompany','010002003')")
	public String updSysCompany(HttpSession session, SysCompany sysCompa, String companyTimeVal) {
		String mess = "";

		try {
			SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
			sysCompa.setUpdDate(new Date());
			sysCompa.setUpdUser(userBackStaff.getStaffName());
			sysCompa.setDatasource(userBackStaff.getStaffParentDepart());
			mess = sysCompanyService.updSysCompany(sysCompa);

		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/checkSysCom", method = { RequestMethod.GET, RequestMethod.POST })

	public String checkSysCom(HttpSession session, SysCompany sysCompa) {
		String mess = "";

		try {
			SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
			sysCompa.setUpdDate(new Date());
			sysCompa.setUpdUser(userBackStaff.getStaffName());
			sysCompa.setDatasource(userBackStaff.getStaffParentDepart());
			mess = sysCompanyService.checkSysCom(sysCompa);

		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	@RequestMapping(value = "/deleteSysCompany", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/deleteSysCompany','010002002')")
	public String deleteSysCompany(SysCompany sysCompa) {
		String mess = "";
		try {
			if (null!=sysCompa.getProtocolPath()) {
				fastDFSService.deleteFile(sysCompa.getProtocolPath());
			}
			sysCompanyService.deleteLession(sysCompa);
			mess = "deletesuccess";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	/**
	 * 保存当前招聘计划
	 * 
	 * @param companyId
	 */
	@RequestMapping(value = "/saveSysPlanDetail", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/saveSysPlanDetail','010002011')")
	public String saveDepart(HttpSession session, SysPlanDetail detail, SysRecruitPlan recruitPlan,
			SysCompany company) {
		String mess = "";
		try {
			SysStaff thisStaff = (SysStaff) session.getAttribute("user");
			detail.setCreateUser(thisStaff.getStaffName());
			detail.setCreateDate(new Date());
			detail.setUpdDate(new Date());
			detail.setUpdUser(thisStaff.getStaffName());

			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "CreateUser:" + detail.getCreateUser());
				LOG.debug("{}", "detail:" + detail);
			}
			// SysTrainCycle cycleCologe = (SysTrainCycle)
			// session.getAttribute("cycleCologe");
			SysTrainCycle cycle = new SysTrainCycle();
			cycle.setStatus("进行中");
			List<SysTrainCycle> lsCologe = sysTrainCycleService.getSysTrainCycleByCon(cycle);
			if (null != lsCologe && lsCologe.size() != 0) {
				cycle = lsCologe.get(0);
				// 保存当前实训周期
				mess = sysCompanyService.saveSysPlanDetail(detail, recruitPlan, company, cycle,
						thisStaff.getStaffName());
			} else {
				mess = "notrain";

			}

		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	/**
	 * 删除当前招聘计划
	 * 
	 * @param companyId
	 */
	@RequestMapping(value = "/deleteSysPlanDetail", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/deleteSysPlanDetail','010002012')")
	public String deleteSysPlanDetail(SysPlanDetail det) {
		String mess = "";
		try {
			sysCompanyService.deleteSysPlanDetail(det);
			mess = "deletesuccess";
		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	// 增加协议
	@RequestMapping(value = "/updSysCompanyPdf", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/updSysCompanyPdf','010002006')")
	public String updSysCompanyPdf(@RequestParam("upfile") MultipartFile file, HttpSession session,
			SysCompany sysCompa) {
		String mess = "";
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("{}", "file:" + file);
				LOG.debug("{}", "OriginalFilename:" + file.getOriginalFilename());
				LOG.debug("{}", "Name:" + file.getName());
				LOG.debug("{}", "sysCompa:" + sysCompa);
			}
			SysStaff userBackStaff = (SysStaff) session.getAttribute("user");
			sysCompa.setUpdDate(new Date());
			sysCompa.setUpdUser(userBackStaff.getStaffName());
			sysCompa.setDatasource(userBackStaff.getStaffParentDepart());
			if (null!=sysCompa.getProtocolPath()) {//删除就文件
				fastDFSService.deleteFile(sysCompa.getProtocolPath());
			}
			String realpath=fastDFSService.uploadFile(file.getBytes());
			sysCompa.setProtocolPath(realpath);
			sysCompanyService.updSysCompanyPdf(sysCompa);

		} catch (Exception e) {
			LOG.error("Exception:", e);
			mess = e.toString();
		}
		return mess;
	}

	// 导入数据
	@RequestMapping(value = "/importCustomerList", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/importCustomerList','010002005')")
	public Map<String, String> importCustomerList(@RequestParam("file") MultipartFile file, HttpSession session,
			HttpServletRequest request) throws Exception {
		SysStaff thisStaff = (SysStaff) session.getAttribute("user");
		List<SysLevel> sysLevelMin = syscompanydao.findMinLevel();
		Map<String, String> mess = null;
		if (sysLevelMin.size() != 0) {

			mess = sysCompanyService.importSysCompany(file, thisStaff, sysLevelMin.get(0));
		} else {

		}

		return mess;
	}

	/**
	 * 获取选中企业的所有招聘计划 查看招聘计划
	 * 
	 * @author tomset
	 * @param company 需要companyId
	 * @param session
	 * @return Map
	 */
	@RequestMapping(value = "/getallplan", method = { RequestMethod.POST, RequestMethod.GET })
	@PreAuthorize("hasPermission('/getallplan','010002008')")
	public Map<String, Object> getAllPlanByCompany(SysCompany company, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		try {
			if (null == user)
				throw new RuntimeException("查询失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("teacher"))
				throw new RuntimeException("查询失败：用户类型不匹配！");
			else if (null == company || null == company.getCompanyId())
				throw new RuntimeException("查询失败：数据缺失！");
			List<SysRecruitPlan> plans = sysCompanyService.getAllPlanByCompany(company.getCompanyId());
			map.put("rows", plans);
			map.put("total", plans.size());
		} catch (RuntimeException e) {
			LOG.error("Exception:", e);
			map.put("error", e.getMessage());
		} catch (Exception e) {
			LOG.error("Exception:", e);
			map.put("error", e.getMessage());
		}
		return map;
	};

	/**
	 * 根据企业Id更新企业的分数和等级
	 * 
	 * @param companyId Integer
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/updCompanyLvl", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasPermission('/updCompanyLvl','010002009')")
	public Map<String, String> updCompanyLvl(Integer companyId, HttpSession session) {
		Map<String, String> message = new HashMap<String, String>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		try {
			if (null == user)
				throw new RuntimeException("更新失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("teacher"))
				throw new RuntimeException("更新失败：用户类型不匹配！");
			else if (null == companyId)
				throw new Exception("更新失败：数据缺失！");
			sysCompanyService.updCompanyLvl(companyId);
			message.put("tip", "succ");
		} catch (RuntimeException e) {
			LOG.error("Exception:", e);
			message.put("tip", e.getMessage());
		} catch (Exception e) {
			LOG.error("Exception:", e);
			message.put("error", e.getMessage());
		}
		return message;
	}

	@RequestMapping(value = "/getAllSysLevel", method = { RequestMethod.GET, RequestMethod.POST })
	public List<SysLevel> getAllSysLevel() {

		try {
			List<SysLevel> ls = sysCompanyService.getAllSysLevel();
			return ls;
		} catch (Exception e) {
			LOG.error("Exception:", e);
		}
		return null;
	}

	@RequestMapping(value = "/initPdfView", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView initPdfView(String pdfPath) {
		ModelAndView mod = new ModelAndView("/pdfview/viewer.btl");
		mod.addObject("pdfPath", "/getRemotePdfFile?filePath="+pdfPath);
		return mod;
	}

}
