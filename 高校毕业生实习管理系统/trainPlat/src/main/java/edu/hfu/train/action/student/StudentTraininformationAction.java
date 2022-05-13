package edu.hfu.train.action.student;

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

import edu.hfu.train.bean.SysRecruitPlan;
import edu.hfu.train.service.student.StudentTrainInformationService;

@RestController
@RequestMapping(value = "/traininformation")
public class StudentTraininformationAction {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Resource
	StudentTrainInformationService studentTrainInformationService;

	@RequestMapping(value = "/gotostudenttraininformation", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentTrainInformation() {
		ModelAndView mod = new ModelAndView("student/studenttraininformation.btl");
		return mod;
	}

	// 查询
	@RequestMapping(value = "/getStudentTrainInformationByCon", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getStudentTrainInformationByCon(String searchText,int pageNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<SysRecruitPlan> ls = studentTrainInformationService.getStudentTrainInformationByCon(searchText,pageNo,10);
			map.put("rows", ls);
		} catch (Exception e) {
			LOG.error("Exception:", e);
			map.put("rows", null);
		}
		return map;
	}
	@RequestMapping(value = "/getStudentTrainInformationCountByCon", method = { RequestMethod.GET, RequestMethod.POST })
	public Integer getStudentTrainInformationCountByCon(String searchText) {
		try {
			Integer res= studentTrainInformationService.getStudentTrainInformationCountByCon(searchText);
			return res;
		} catch (Exception e) {
			LOG.error("Exception:", e);
		}
		return 0;
	}
	/**
	 * 默认查询
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getStudentDefaultTrainInformation", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getStudentDefaultTrainInformation(HttpSession session,int pageNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String depart=String.valueOf(session.getAttribute("depart"));
			String major=String.valueOf(session.getAttribute("major"));
			String searchText=depart+"+"+major;
			List<SysRecruitPlan> ls = studentTrainInformationService.getStudentTrainInformationByCon(searchText,pageNo,10);
			if (null==ls||ls.size()==0) {//没找到对口的，则返回全部
				ls=studentTrainInformationService.getAllInformationByCon(pageNo,10);
			}
			map.put("rows", ls);
		} catch (Exception e) {
			LOG.error("Exception:", e);
			map.put("rows", null);
		}
		return map;
	}
	/**
	 * 默认查询的数量
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getStudentDefaultTrainInformationCount", method = { RequestMethod.GET, RequestMethod.POST })
	public Integer getStudentDefaultTrainInformationCount(HttpSession session) {
		try {
			String depart=String.valueOf(session.getAttribute("depart"));
			String major=String.valueOf(session.getAttribute("major"));
			String searchText=depart+"+"+major;
			int  res=studentTrainInformationService.getStudentTrainInformationCountByCon(searchText);
			if (res==0) {//没找到对口的，则返回全部
				res=studentTrainInformationService.getAllInformationCountByCon();
			}
			return res;
		} catch (Exception e) {
			LOG.error("Exception:", e);
		}
		return 0;
	}

}
