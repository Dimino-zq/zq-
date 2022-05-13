package edu.hfu.train.service.sysset;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.bean.SysLevel;
import edu.hfu.train.bean.SysPlanDetail;
import edu.hfu.train.bean.SysRecruitPlan;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysTrainCycle;
import edu.hfu.train.dao.sysset.SysCompanyDao;
import edu.hfu.train.dao.sysset.SysTrainCycleDao;
import edu.hfu.train.util.daoru.ExcelPoi;

@Service
@Transactional
public class SysCompanyService {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource
	SysCompanyDao syscompanydao;
	@Resource
	SysTrainCycleDao sysTrainCycledao;
	@Resource
	DictionaryService dictionaryService;

	public List<SysCompany> getAllSysCompany() throws Exception {
		return syscompanydao.getAllSysCompany();
	}

	/**
	 * 学生专用获取实习单位列表
	 * 
	 * @author tomset
	 * @return List<SysCompany>
	 * @throws Exception
	 */
	public List<SysCompany> getAllSysCompanyForStudent() throws Exception {
		return syscompanydao.getAllSysCompanyForStudent();
	}

	public List<SysPlanDetail> getAllSysPlanDetail(SysCompany sysCompa) throws Exception {
		return syscompanydao.getALLSysPlanDetail(sysCompa);
	}

	public List<SysCompany> getSysCompanyByCon(SysCompany sysCompa) throws Exception {
		return syscompanydao.getSysCompanyByCon(sysCompa);
	}

	public List<SysCompany> getSysCompanyByConForImport(SysCompany sysCompa) throws Exception {

		return syscompanydao.getSysCompanyByConForImport(sysCompa);
	}

	public List<SysCompany> getSysCompanyByCon(SysCompany sysCompa, int pageNo, int pageSize) throws Exception {
		return syscompanydao.getSysCompanyByCon(sysCompa, pageNo, pageSize);
	}

	public Integer getSysCompanyCountByCon(SysCompany sysCompa) throws Exception {
		return syscompanydao.getSysCompanyCountByCon(sysCompa);
	}

	/**
	 * 获取当前的招聘计划
	 * 
	 * @param companyId
	 * @param cycyleId
	 * @return
	 */
	public SysRecruitPlan getCurrentSysRecruitPlan(Integer companyId, Integer cycyleId) throws Exception {
		return syscompanydao.getCurrentSysRecruitPlan(companyId, cycyleId);
	}

	public String checkSysCompany(SysCompany sysCompa) throws Exception {
		SysCompany sysCom = new SysCompany();
		Pattern p = null;
		Matcher m = null;
		boolean b = false;

		String errorData = "";
		sysCom.setComName(sysCompa.getComName());
		sysCom.setCheckstate(sysCompa.getCheckstate());
		sysCom.setSign(sysCompa.getSign());
		sysCom.setSignTime(sysCompa.getSignTime());
		sysCom.setSchContactphone(sysCompa.getSchContactphone());
		sysCom.setPhone(sysCompa.getPhone());
		List<SysCompany> ls = getSysCompanyByConForImport(sysCompa);
		if (ls.size() == 0 && errorData == "") {

		} else {
			// errorData.concat("企业名" + sysCom.getComName() + "重复.");
			if (sysCom.getComName() == null || sysCom.getComName() == "" || sysCom.getComName() == "null") {
				errorData = errorData + "企业名为空";
			} else {
				errorData = errorData + "企业名" + sysCom.getComName() + "重复.";
			}
		}

		if (Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(sysCom.getPhone()).matches()
				|| Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$").matcher(sysCom.getPhone()).matches()
				|| Pattern.compile("^[1-9]{1}[0-9]{5,8}$").matcher(sysCom.getPhone()).matches()) {
			// errorData.concat("联系方式有误.");

		} else {
			errorData = errorData + "联系方式有误.       ";
		} // schContactphone
		if (Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(sysCom.getSchContactphone()).matches()
				|| Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$").matcher(sysCom.getSchContactphone()).matches()
				|| Pattern.compile("^[1-9]{1}[0-9]{5,8}$").matcher(sysCom.getSchContactphone()).matches()) {
			// errorData.concat("校内联系方式有误.");

		} else {
			errorData = errorData + "校内联系方式有误.        ";
		}
		System.out.println(errorData);
		if (ls.size() == 0 && errorData == "") {
			return "某行数据正确";

		} else {
			// errorData.concat("企业名" + sysCom.getComName() + "重复.");

		}

		return errorData;
	}

	public String saveSysCompanyByHand(SysCompany sysCompa) throws Exception {

		List<SysLevel> sysLevelMin = syscompanydao.findMinLevel();

		try {
			if (sysLevelMin.size() != 0) {
				SysLevel lv = sysLevelMin.get(0);
				sysCompa.setSysLevel(lv);
				sysCompa.setCurrentLvl(lv.getLvlName());
				sysCompa.setAddupScore(lv.getMinScore());
				sysCompa.setCreateDate(new Date());
				sysCompa.setUpdDate(new Date());
				sysCompa.setSign("否");
				sysCompa.setCheckstate("未审批");
				syscompanydao.saveSysCompany(sysCompa);
			} else {
				return "lvNo";
			}

		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			return "重复";
		}

		return "succ";
	}

	/**
	 * 保存计划
	 * 
	 * @param detail
	 * @param recruitPlan
	 * @param company
	 * @param cycle
	 * @param operName
	 * @return
	 * @throws Exception
	 */
	public String saveSysPlanDetail(SysPlanDetail detail, SysRecruitPlan recruitPlan, SysCompany company,
			SysTrainCycle cycle, String operName) throws Exception {
		// 计划明细列表
		List<SysPlanDetail> details = new ArrayList<SysPlanDetail>();
		if (null == recruitPlan.getPlanId()) {// 主键为空，则表明是新增
			recruitPlan.setCompany(company);
			recruitPlan.setCreateDate(new Date());
			recruitPlan.setCreateUser(operName);// 操作人
			recruitPlan.setCycle(cycle);
			if (null != detail.getStationName() && !"".equals(detail.getStationName())) {
				String[] stationNames = detail.getStationName().split("\\$");
				String[] majorNames = detail.getMajorName().split("\\$");
				String[] planStuStrings = detail.getPlanStuString().split("\\$");
				for (int i = 0; i < stationNames.length; i++) {
					SysPlanDetail plde = new SysPlanDetail();
					plde.setMajorName(majorNames[i]);
					plde.setStationName(stationNames[i]);
					plde.setPlanStu(Integer.valueOf(planStuStrings[i]));
					plde.setRecruitPlan(recruitPlan);
					plde.setActualStu(0);
					plde.setCreateUser(detail.getCreateUser());
					plde.setCreateDate(detail.getCreateDate());
					plde.setUpdUser(detail.getUpdUser());
					plde.setUpdDate(detail.getUpdDate());
					details.add(plde);
				}
			}
			recruitPlan.setDetails(details);
			syscompanydao.saveSysRecruitPlan(recruitPlan);
		} else {
			SysRecruitPlan oldPlan = syscompanydao.getPlanById(recruitPlan.getPlanId());
			oldPlan.setPlanDesc(recruitPlan.getPlanDesc());
			oldPlan.setUpdDate(new Date());
			oldPlan.setUpdUser(operName);
			List<SysPlanDetail> oldDetails = oldPlan.getDetails();
			for (SysPlanDetail dl : oldDetails) {// 暴力删除所有
				syscompanydao.deleteSysPlanDetail(dl);
			}
			if (null != detail.getStationName() && !"".equals(detail.getStationName())) {
				String[] stationNames = detail.getStationName().split("\\$");
				String[] majorNames = detail.getMajorName().split("\\$");
				String[] planStuStrings = detail.getPlanStuString().split("\\$");
				for (int i = 0; i < stationNames.length; i++) {
					SysPlanDetail plde = new SysPlanDetail();
					plde.setMajorName(majorNames[i]);
					plde.setStationName(stationNames[i]);
					plde.setPlanStu(Integer.valueOf(planStuStrings[i]));
					plde.setRecruitPlan(oldPlan);
					// plde.setCreateUser(detail.getCreateUser());
					// plde.setCreateDate(detail.getCreateDate());
					plde.setUpdUser(detail.getUpdUser());
					plde.setUpdDate(detail.getUpdDate());
					if (null != detail.getActualStu()) {
						plde.setActualStu(detail.getActualStu());
					} else {
						plde.setActualStu(0);
					}

					details.add(plde);
				}
			}
			oldPlan.setDetails(details);
			syscompanydao.updSysRecruitPlan(oldPlan);
		}
		return "succ";
	}

	public String updSysCompany(SysCompany sysCompa) throws Exception {
		SysCompany com = syscompanydao.getSysCompanyById(sysCompa);
		com.setDatasource(sysCompa.getDatasource());
		com.setIndustry(sysCompa.getIndustry());
		com.setComName(sysCompa.getComName());
		com.setComAddress(sysCompa.getComAddress());

		com.setComcontacts(sysCompa.getComcontacts());
		com.setPosition(sysCompa.getPosition());
		com.setPhone(sysCompa.getPhone());
		com.setSchContact(sysCompa.getSchContact());
		com.setSchContactphone(sysCompa.getSchContactphone());
		com.setLogoPath(sysCompa.getLogoPath());
		com.setUpdUser(sysCompa.getUpdUser());
		com.setUpdDate(new Date());
		syscompanydao.updSysCompany(com);
		updCompanyLvl(sysCompa.getCompanyId());
		return "succ";
	}

	public void deleteLession(SysCompany sysCompa) throws Exception {
		syscompanydao.deleteSysCompany(sysCompa);
	}

	public void deleteSysPlanDetail(SysPlanDetail det) throws Exception {
		syscompanydao.deleteSysPlanDetail(det);
	}

	public List<SysCompany> getCustomList() {
		return null;
	}

	public Map<String, String> importSysCompany(MultipartFile file, SysStaff thisStaff, SysLevel sysLevel)
			throws Exception {
		SysCompany sysCompa = null;
		SysCompany sysCompany = null;
		String backString = null;
		String canSave = "可存";
		SysLevel lv = null;
		int index = 0;
		int totalNum = 0;

		String logopath = "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AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA7ugAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO7oAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO4A6AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA/9k=";
		Map<String, String> importBackMsg = new HashMap<>();

		try {
			if (file == null || file.getSize() == 0 || !file.getOriginalFilename().contains(".xlsx")) {
				return null;
			}

			if (null != sysLevel) {

				// 根据自己定义的实体类更换list的泛型
				List<SysCompany> list = new ExcelPoi<SysCompany>().importObjectList(file.getInputStream(),
						file.getOriginalFilename(), SysCompany.class);
				// SysCompany可以替换成你的实体类
				// list就是获取的数据
				Iterator<SysCompany> iterator = list.iterator();

				while (iterator.hasNext()) {
					sysCompa = (SysCompany) iterator.next();
					index++;
					backString = checkSysCompany(sysCompa);// 检查数据
					if (backString != "某行数据正确") {
						System.out.println(backString);
						// comNames.add(backString);
						importBackMsg.put(String.valueOf(index), backString);
						canSave = "不可存";
					} else {

					}
				}
				iterator = list.iterator();

				if (canSave.equals("可存")) {
					lv = sysLevel;
					while (iterator.hasNext()) {

						totalNum++;
						sysCompany = (SysCompany) iterator.next();
						sysCompany.setCreateUser(thisStaff.getStaffName());
						sysCompany.setCreateDate(new Date());
						sysCompany.setUpdUser(thisStaff.getStaffName());
						sysCompany.setUpdDate(new Date());
						sysCompany.setLogoPath(logopath);
						sysCompa.setSysLevel(sysLevel);
						sysCompa.setCurrentLvl(sysLevel.getLvlName());
						sysCompa.setAddupScore(sysLevel.getMinScore());
						sysCompany.setDatasource(thisStaff.getStaffParentDepart());

						syscompanydao.saveSysCompany(sysCompany); // 保存数据到数据库
						updAllCompanyLvl();
					}

				}

				importBackMsg.put("total", String.valueOf(index));
			} else {
				importBackMsg.put("lvNo", "lvNo");
				return importBackMsg;
			}

		} catch (Exception e) {
			// log.error(e);
			LOG.error("Exception:", e);
			return importBackMsg;
		}
		return importBackMsg;

	}

	public String updSysCompanyPdf(SysCompany sysCompa) throws Exception {
		SysCompany com = syscompanydao.getSysCompanyById(sysCompa);
		com.setCheckstate(sysCompa.getCheckstate());
		com.setSign(sysCompa.getSign());
		com.setProtocolPath(sysCompa.getProtocolPath());
		com.setEffeDate(sysCompa.getEffeDate());
		com.setSignTime(sysCompa.getSignTime());

		com.setUpdUser(sysCompa.getUpdUser());
		com.setUpdDate(new Date());
		syscompanydao.updSysCompany(com);
		return "succ";

	}

	/**
	 * 根据企业id查询当前企业发布的所有招聘计划
	 * 
	 * @author tomset
	 * @param company
	 * @return List
	 * @throws Exception
	 */
	public List<SysRecruitPlan> getAllPlanByCompany(Integer companyId) throws Exception {
		if (null == companyId)
			throw new RuntimeException("查询失败：数据缺失！");
		return syscompanydao.getAllPlanByCompany(companyId);
	}

	/**
	 * 更新指定企业的等级
	 * 
	 * @author tomset
	 * @param companyId
	 * @return String
	 * @throws Exception
	 */
	@Transactional
	public void updCompanyLvl(Integer companyId) throws Exception {
		if (null == companyId)
			throw new RuntimeException("更新失败：数据缺失！");
		syscompanydao.updCompanyLvl(companyId);
	}

	/**
	 * 更新所有企业的等级
	 * 
	 * @author tomset
	 * @throws Exception
	 */
	@Transactional
	public void updAllCompanyLvl() throws Exception {
		syscompanydao.updAllCompanyLvl();
	}

	public List<SysLevel> getAllSysLevel() throws Exception {
		return syscompanydao.getAllSysLevel();
	}

	public String checkSysCom(SysCompany sysCompa) throws Exception {
		// TODO Auto-generated method stub
		SysCompany com = syscompanydao.getSysCompanyById(sysCompa);
		com.setCheckstate(sysCompa.getCheckstate());
		syscompanydao.updSysCompany(com);
		updCompanyLvl(sysCompa.getCompanyId());
		return "succ";

	}

}
