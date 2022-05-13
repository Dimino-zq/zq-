package edu.hfu.train.action.student;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import edu.hfu.train.bean.*;
import edu.hfu.train.util.FormatUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.train.bean.xmlBean.SysDictionary;
import edu.hfu.train.service.remote.AuthGrantService;
import edu.hfu.train.service.student.StudentApplicationService;
import edu.hfu.train.service.sysset.DictionaryService;
import edu.hfu.train.service.sysset.FastDFSService;
import edu.hfu.train.util.Constant;

@RestController
@RequestMapping(value = "/studentapp")
public class StudentApplicationAction {
	private final Logger LOG = LoggerFactory.getLogger(StudentApplicationAction.class);
	@Resource
	StudentApplicationService applicationService;

	@Resource
	DictionaryService dictionaryService;

	@Resource
	AuthGrantService authService;

	@RequestMapping(value = "/gotostudentapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentApplication(HttpSession session) {
		ModelAndView mod = null;
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		Object deptCycle = session.getAttribute("cycleDepart");
		if (null == user)
			throw new NullPointerException("查询失败：未登录状态！");
		else if (!session.getAttribute("userType").equals("student"))
			throw new ClassCastException("查询失败：用户类型不匹配！");
		try {
			SysStudent student = (SysStudent) user;
			//若实训周期已开启且用户未完成实习（新用户），提示实训周期未开启
			if (null==deptCycle && student.getTrainStatus().equals("0"))
			{
				mod = new ModelAndView("student/tip.btl");
				mod.addObject("tipURL", "/static/images/noCycle.jpg");
			} else {
				mod = new ModelAndView("student/studentapplication.btl");
				mod.addObject("stuTrainStatus", student.getTrainStatus());
			}
		} catch (Exception e) {
			LOG.debug("{}",e.getMessage());
		}
		return mod;
	}

	@RequestMapping(value = "/getapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getApplication(HttpSession session) {
		Object mess = null;
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		if (null == user)
			throw new RuntimeException("查询失败：未登录状态！");
		else if (!session.getAttribute("userType").equals("student"))
			throw new RuntimeException("查询失败：用户类型不匹配！");
		try {
			// 查询当前学生的申请表
			List<StudentApplication> list = applicationService.getApplication((SysStudent) user);
			mess = list;
		} catch (RuntimeException e) {
			LOG.debug("{}",e.getMessage());
			mess = new HashMap<String, Object>().put("error",e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mess;
	}

	// 保存申请表
	@RequestMapping(value = "/saveapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> saveApplication(StudentApplication application, SysCompany company,
			HttpSession session) {
		Map<String, Object> message = new HashMap<String, Object>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("保存失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("保存失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("保存失败：实训周期未开启！");
			else if (null == ((SysStudent) user).getStudentId())
				throw new Exception("保存失败：学生Id为空！");
			else if (null == application)
				throw new NullPointerException("保存失败：申请信息不存在！");
			// 在申请表中绑定学生信息
			application.setStudent((SysStudent) user);
			// 在申请表中绑定实习单位信息
			application.setCompany(company);
			// 在申请表中绑定当前实训周期
			application.setTrainCycle((SysDepartTrainCycle) trainCycle);
			// 保存实习申请
			if(applicationService.checkForm(application))
				message.put("tip", applicationService.saveApplication(application));
		} catch (ClassCastException | NullPointerException e) {
			LOG.debug("{}",e.getMessage());
			message.put("error", e.getMessage());
		} catch (RuntimeException e) {
			LOG.debug("{}",e.getMessage());
			message.put("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", "未知错误！");
		}
		return message;
	}

	// 删除申请表
	@RequestMapping(value = "/deleteapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> deleteApplication(Integer applicationId, HttpSession session) {
		Map<String, Object> message = new HashMap<String, Object>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("删除失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("删除失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("删除失败：实训周期未开启！");
			else if (null == ((SysStudent) user).getStudentId())
				throw new Exception("删除失败：学生Id为空！");
			if (applicationId != null)
				message.put("tip",
						applicationService.deleteApplication(applicationId, ((SysStudent) user).getStudentId()));
			else
				message.put("error", "删除失败，请选中申请表！");
		} catch (ClassCastException | NullPointerException e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}

	// 修改申请表
	@RequestMapping(value = "/updateapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> updateApplication(StudentApplication application, SysCompany company,
			HttpSession session) {
		Map<String, Object> message = new HashMap<String, Object>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("修改失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("修改失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("保存失败：实训周期未开启！");
			else if (null == ((SysStudent) user).getStudentId())
				throw new Exception("修改失败：学生Id为空！");
			else if (null == application)
				throw new NullPointerException("修改失败：申请信息不存在！");
			// 检查要修改的申请表的Id是否存在
			if (application.getApplyId() != null) {
				// 在申请表中绑定实习单位信息
				application.setCompany(company);
				if(applicationService.checkForm(application))
					message.put("tip",applicationService.updateApplication(application, ((SysStudent) user).getStudentId()));
			} else
				message.put("error", "修改失败，请选中申请表！");
		} catch (RuntimeException e) {
			LOG.debug("{}",e.getMessage());
			message.put("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", "未知错误！");
		}
		return message;
	}

	// 上传附件
	@RequestMapping(value = "/uploadfile", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> uploadFile(MultipartFile file, Integer applicationId, HttpSession session) {
		Map<String, Object> message = new HashMap<String, Object>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("上传失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("上传失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("上传失败：实训周期未开启！");
			else if (null == applicationId)
				throw new IllegalArgumentException("上传失败：请选中申请表！");
			if (null == file || file.isEmpty())
				throw new NullPointerException("上传失败:文件为空！");

			// 获取开始学年度
//			String startSchoolYear = ((SysDepartTrainCycle) trainCycle).getSysTrainCycle().getStartschoolyear() + "-"+ ((SysDepartTrainCycle) trainCycle).getSysTrainCycle().getEndschoolyear();
			// 获取学生学号和id
			SysStudent student = (SysStudent) user;
//			String studentNo = student.getStudentNo();
			Integer studentId = student.getStudentId();
			// 根据文件名称检查文件是否为.jpg格式图片
			if (!Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(Constant.PICTRUE_EXTENSION)) {
				message.put("error", "上传失败:文件类型错误，请上传.jpg格式的图片！");
				LOG.debug("{}","文件类型错误");
			}
			// 检查附件大小是否过大
			else if (file.getSize() > 1024 * 1024 * 1) {
				message.put("error", "上传失败:超出上传上限1M!");
				LOG.debug("{}","文件大小超出上传上限1M");
			} else {
				// 附件保存路径
				message.put("tip", applicationService.upLoadFile(applicationId, studentId, file));
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}

	// 上传实习鉴定表
	@RequestMapping(value = "/uploadAppraisalFromFirm", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> uploadAppraisalFromFirm(MultipartFile file, Integer applicationId, HttpSession session) {
		Map<String, Object> message = new HashMap<String, Object>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("上传失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("上传失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("上传失败：实训周期未开启！");
			else if (null == applicationId)
				throw new IllegalArgumentException("上传失败：请选中申请表！");
			if (null == file || file.isEmpty())
				throw new NullPointerException("上传失败:文件为空！");

			// 获取开始学年度
			String startSchoolYear = ((SysDepartTrainCycle) trainCycle).getSysTrainCycle().getStartschoolyear() + "-"+ ((SysDepartTrainCycle) trainCycle).getSysTrainCycle().getEndschoolyear();
			// 获取学生学号和id
			SysStudent student = (SysStudent) user;
			String studentNo = student.getStudentNo();
			Integer studentId = student.getStudentId();
			// 根据文件名称检查文件是否为.jpg格式图片
			if (!Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(Constant.PICTRUE_EXTENSION)) {
				message.put("error", "上传失败:文件类型错误，请上传.jpg格式的图片！");
				LOG.debug("{}","文件类型错误");
			}
			// 检查附件大小是否过大
			else if (file.getSize() > 1024 * 1024 * 1) {
				message.put("error", "上传失败:超出上传上限1M!");
				LOG.debug("{}","文件大小超出上传上限1M");
			} else {
				// 附件保存路径
//				String savepath = "\\static\\upfile\\apply\\" + startSchoolYear + "\\" + studentNo + "\\appraisalFromFirm\\";
				message.put("tip", applicationService.uploadAppraisalFromFirm(applicationId, studentId, file));
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}
	
	// 提交申请表
	@RequestMapping(value = "/submitapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> submitApplication(Integer applicationId, HttpSession session) {
		Map<String, Object> message = new HashMap<String, Object>();
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前进行中的实训周期
		Object trainCycle = session.getAttribute("cycleDepart");
		try {
			if (null == user)
				throw new NullPointerException("提交失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("提交失败：用户类型不匹配！");
			else if (null == trainCycle)
				throw new NullPointerException("提交失败：实训周期未开启！");
			else if (null == ((SysStudent) user).getStudentId())
				throw new Exception("提交失败：学生Id为空！");
			if (applicationId != null)
				message.put("tip",applicationService.submitApplication(applicationId, ((SysStudent) user).getStudentId()));
			else
				message.put("error", "提交失败，请选中申请表！");
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}

	// html查看申请表 --学生和教师通用方法，通过session中的useType判断用户类型
	@RequestMapping(value = "/gotohtmlapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoHTMLApplication(Integer applicationId, HttpSession session) {
		ModelAndView mod = new ModelAndView("student/htmlapplication.btl");
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前用户类型
		Object userType = session.getAttribute("userType");
		try {
			if (null == user)
				throw new NullPointerException("无法查看：未登录状态！");
			else if (null == userType)
				throw new NullPointerException("无法查看：未知用户类型！");
			else if (null == applicationId)
				throw new NullPointerException("无法查看：请选中申请表！");
			// 查询申请表Id对应的申请表
			StudentApplication application = applicationService.getApplicationById(applicationId);
			if (null == application)
				throw new NullPointerException("无法查看：未查找到该申请记录！"); // 不存在该申请记录
			else {
				if (userType.equals("student")) {
					// 检查查当前的学生是否为申请表中记录的学生
					if (!((SysStudent) user).getStudentId().equals(application.getStudent().getStudentId()))
						throw new Exception("无法查看：非法操作");
				}
//				else if (userType.equals("teacher"))
//				{
//					//检查查当前的指导老师是否为申请表中记录的学生的指导老师
//					if (((SysStaff) user).getUserCode().equals(application.getTeacherNo()))
//						throw new Exception("无法查看：非法操作");
//				}
//				else
//					throw new Exception("无法查看：非法操作");  //未知用户类型

				// 获取从申请表中获取学生信息
				SysStudent student = application.getStudent();
				// 获取从申请表中获取实训周期信息
				SysDepartTrainCycle trainCycle = application.getTrainCycle();
				// 获取从申请表中获取实习单位信息
				SysCompany company = application.getCompany();
				// btl替换数据
				mod.addObject("trainCycle", trainCycle.getSysTrainCycle().getStartschoolyear() + "-"+ trainCycle.getSysTrainCycle().getEndschoolyear()); // 学年度
				mod.addObject("semester", trainCycle.getSysTrainCycle().getSemester()); // 学期
				mod.addObject("depart", student.getDepart()); // 系别
				mod.addObject("studentName", student.getStudentName()); // 姓名
				mod.addObject("studentSex", student.getStudentSex()); // 性别
				// 专业班级
				mod.addObject("studentClass",student.getMajor() + student.getStudentGrade() + "-" + student.getClassNumber() + "班");
				mod.addObject("insurance", application.getInsurance()); // 保险情况
				mod.addObject("phoneOrQQ", application.getPhoneOrQQ()); // 联系方式
				if (null == application.getApplydate() || null == application.getEndDate())
					throw new NullPointerException("开始时间或结束时间为空");
				// 实习起止时间
				mod.addObject("applyDate", FormatUtil.formatDateToStr(application.getApplydate(), "yyyy-MM-dd") + "至"+ FormatUtil.formatDateToStr(application.getEndDate(), "yyyy-MM-dd"));
				mod.addObject("teacherName", application.getTeacherName()); // 校内指导老师
				mod.addObject("filePath", application.getFilePath()); // 附件
				mod.addObject("sign", student.getStudentName()); // 签名
				if (null == application.getUpdDate())
					throw new NullPointerException("更新时间为空");
				// 申请时间
				mod.addObject("updateDate", FormatUtil.formatDateToStr(application.getUpdDate(), "yyyy年   MM月   dd日"));
				mod.addObject("comName", company.getComName()); // 单位名称
				mod.addObject("comAddress", company.getComAddress()); // 单位地址
				mod.addObject("industry", company.getIndustry()); // 所属行业
				mod.addObject("surcomJob", application.getSurcomjob()); // 实习岗位
				mod.addObject("comContacts", company.getComcontacts()); // 企业联系人
				mod.addObject("phone", company.getPhone()); // 联系电话
				// 企业指导老师 //联系电话
				mod.addObject("surcomContent", application.getSurcomcontent()); // 实习项目内容
				mod.addObject("departView", application.getDeptView()); // 系部意见
			}
		} catch (Exception e) {
			e.printStackTrace();
			mod.addObject("tip", e.getMessage());
		}
		return mod;
	}

	// 下拉列表框数据
	// 保险情况
	@RequestMapping(value = "/getinsurance", method = { RequestMethod.GET, RequestMethod.POST })
	public List<SysDictionary> getInsurance() {
		return dictionaryService.getDictonaryByType("保险列表");
	}

	// 指导老师
	@RequestMapping(value = "/getteachername", method = { RequestMethod.GET, RequestMethod.POST })
	public List<SysStaff> getTeacherName(HttpSession session) {
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前用户类型
		Object userType = session.getAttribute("userType");
		try {
			if (null == user)
				throw new NullPointerException("无法查看：未登录状态！");
			else if (null == userType)
				throw new NullPointerException("无法查看：未知用户类型！");
			else if (!userType.equals("student"))
				throw new ClassCastException("查询指导老师失败：用户类型不匹配!");
			return authService.getTeacherByDepart(((SysStudent) user).getDepart());
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getreviewlog", method = { RequestMethod.GET, RequestMethod.POST })
	public List<SysReviewLog> getReviewLog(Integer applicationId, HttpSession session) {
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前用户类型
		Object userType = session.getAttribute("userType");
		try {
			if (null == user)
				throw new NullPointerException("读取审批记录失败：未登录状态！");
			else if (null == userType)
				throw new NullPointerException("读取审批记录失败：未知用户类型！");
			else if (!userType.equals("student"))
				throw new ClassCastException("读取审批记录失败：用户类型不匹配!");
			else if (null == applicationId)
				throw new RuntimeException("读取审批记录失败：请选中申请表！");
			return applicationService.getReviewLog(applicationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回当前实训周期,通用
	 */
	@RequestMapping(value = "/getcycle", method = { RequestMethod.GET, RequestMethod.POST })
	public SysDepartTrainCycle getTrainCycle(HttpSession session) {
		// 从session中获取当前进行中的实训周期
		Object deptCycle = session.getAttribute("cycleDepart");
		try {
			if (null == deptCycle)
				throw new NullPointerException("实训周期未开启！");
			else {
				SysDepartTrainCycle departCycle = (SysDepartTrainCycle) deptCycle;
				//departCycle.setSysTrainCycle(null);
				LOG.debug("{}",departCycle);
				return departCycle;
			}
		} catch (Exception e) {
			LOG.debug("{}",e.getMessage());
		}
		return null;
	}
	
	//预览打印实习鉴定表
	@RequestMapping(value = "/gotoAppraisalFromFirm",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView gotoTrainReport(HttpSession session)
	{
		ModelAndView mod = new ModelAndView("student/htmlAppraisalFromFirm.btl");
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		//从session中获取当前用户类型
		Object userType = session.getAttribute("userType");
		try {
			if (null == user)
				throw new NullPointerException("无法查看：未登录状态！");
			else if(!"student".equals(userType))
				throw new RuntimeException("无法查看：用户类型不匹配！");
			SysStudent student = (SysStudent) user;
			//学生基本信息
			mod.addObject("studentName",student.getStudentName());  //姓名
			mod.addObject("studentNo",student.getStudentNo());  //学号
			mod.addObject("studentSex",student.getStudentSex());  //性别
			mod.addObject("depart",student.getDepart());//系部
			mod.addObject("major",student.getMajor());//专业
			mod.addObject("classNumber",student.getStudentGrade()+"-"+student.getClassNumber());//班级

			/*
			 * //根据学生查询自主实习申请 List<StudentApplication> applicationList =
			 * applicationService.getApplication(student); if(null==applicationList ||
			 * applicationList.size()==0 ||
			 * !applicationList.get(0).getStatus().equals("DApprove") &&
			 * !applicationList.get(0).getStatus().equals("EApprove")) throw new
			 * RuntimeException("无法查看：您从未开始过实习！");
			 * mod.addObject("comName",applicationList.get(0).getCompany().getComName());//
			 * 单位名称 mod.addObject("surcomjob",applicationList.get(0).getSurcomjob());//实习岗位
			 * mod.addObject("applydate",FormatUtil.formatDateToStr(applicationList.get(0).
			 * getApplydate(),"yyyy-MM-dd") + "至" +
			 * FormatUtil.formatDateToStr(applicationList.get(0).getEndDate(),"yyyy-MM-dd"))
			 * ;//实习起止时间
			 * mod.addObject("surcomcontent",applicationList.get(0).getSurcomcontent());//
			 * 实习岗位
			 */
		} catch (Exception e) {
			e.printStackTrace();
			mod.addObject("tip", e.getMessage());
		}
		return mod;
	}
	
}
