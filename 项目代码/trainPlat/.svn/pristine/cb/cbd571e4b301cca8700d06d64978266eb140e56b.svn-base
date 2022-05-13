package edu.hfu.train.action.teacher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.train.action.sysset.SysCompanyAction;
import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.service.teacher.TeacherCheckCompanyService;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherCheckCompanyAction {
	private final Logger LOG = LoggerFactory.getLogger(SysCompanyAction.class);

	@Resource
	TeacherCheckCompanyService teacherCheckCompanyService;

	@RequestMapping(value = "/gototeachercheckcompany", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoTeacherCheckCompanyApplication() {
		ModelAndView mod = new ModelAndView("/teacher/teachercheckcompany.btl");
		return mod;
	}

	@RequestMapping(value = "/getTeacherCheckSysCompanyByCon", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getSysCompanyByCon(HttpSession session, SysCompany sysCompa, int page, int rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SysStaff userBackStaff = (SysStaff) session.getAttribute("user");

			LOG.debug("userCode:" + userBackStaff.getUserCode());
			LOG.debug("StaffParentDepart:" + userBackStaff.getStaffParentDepart());
			String datasource = "@" + userBackStaff.getStaffParentDepart();
			List<SysCompany> ls = teacherCheckCompanyService.getTeacherCheckSysCompanyByCon(datasource,
					userBackStaff.getUserCode(), sysCompa, page, rows);
			Integer count = teacherCheckCompanyService.getTeacherCheckSysCompanyCountByCon(datasource,
					userBackStaff.getUserCode(), sysCompa);

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

}
