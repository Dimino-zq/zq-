package edu.hfu.train.action.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.bean.SysDepartTrainCycle;
import edu.hfu.train.bean.SysReviewLog;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.service.student.StudentApplicationService;
import edu.hfu.train.service.student.StudentSecApplicationService;
import edu.hfu.train.util.Constant;
import edu.hfu.train.util.FormatUtil;

@RestController
@RequestMapping(value = "/studentapp")
public class StudentSecApplicationAction {
	private final Logger LOG = LoggerFactory.getLogger(StudentSecApplicationAction.class);

	@Resource
	StudentSecApplicationService secApplicationService;

	@Resource
	StudentApplicationService applicationService;

	@RequestMapping(value = "/gotostudentchgapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoStudentSecApplication(HttpSession session) {
		ModelAndView mod = null;
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		if (null == user)
			throw new NullPointerException("查询失败：未登录状态！");
		else if (!session.getAttribute("userType").equals("student"))
			throw new ClassCastException("查询失败：用户类型不匹配！");
		try {
			SysStudent student = (SysStudent) user;
			if (secApplicationService.checkApplication((SysStudent) user))
			{
				mod = new ModelAndView("student/studentchgapplication.btl");
				mod.addObject("stuTrainStatus", student.getTrainStatus());
			}
		} catch (NullPointerException | ClassCastException e) {
			LOG.debug("{}",e.getMessage());
			mod.addObject("error", e.getMessage());
		} catch (RuntimeException e) {
			LOG.debug("{}",e.getMessage());
			mod = new ModelAndView("student/tip.btl");
			mod.addObject("tipURL", "/static/images/noApplication.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mod;
	}

	@RequestMapping(value = "/getchgapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public List<StudentSecApplication> getSecApplication(HttpSession session) {
		try {
			// 从session中获取当前用户
			Object user = session.getAttribute("user");
			if (null == user)
				throw new NullPointerException("查询失败：未登录状态！");
			else if (!session.getAttribute("userType").equals("student"))
				throw new ClassCastException("查询失败：用户类型不匹配！");
			// 查询当前学生的申请表
			List<StudentSecApplication> list = secApplicationService.getSecApplication((SysStudent) user);
			return list;
		} catch (ClassCastException | NullPointerException e) {
			LOG.debug("{}",e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 新建变更申请
	@RequestMapping(value = "/savechgapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> saveApplication(StudentSecApplication secApplication, SysCompany company,
			HttpSession session) {
		Map<String, String> message = new HashMap<String, String>();
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
			else if (null == secApplication)
				throw new NullPointerException("保存失败：申请信息不存在！");
			else if (null == company)
				throw new NullPointerException("保存失败：实习单位信息不存在！");
			else if (null == company.getCompanyId())
				throw new Exception("保存失败：实习单位Id为空！");
			// else if(null == secApplication.getTeacherNo() ||
			// application.getTeacherNo().equals(""))
			// throw new Exception("保存失败：指导老师为空！");
			// 在变更申请表中绑定最初的实习申请
			secApplication.setApplication(applicationService.getApplication((SysStudent) user).get(0));
			// 在申请表中绑定实习单位信息
			secApplication.setNewCompany(company);
			// 保存实习变更申请
			if(secApplicationService.checkForm(secApplication))
				message.put("tip", secApplicationService.saveSecApplication(secApplication));
		} catch (ClassCastException | NullPointerException e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}

	// 删除申请表
	@RequestMapping(value = "/deletechgapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> deleteSecApplication(Integer secApplicationId, HttpSession session) {
		Map<String, String> message = new HashMap<String, String>();
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
				throw new NullPointerException("保存失败：实训周期未开启！");
			else if (null == ((SysStudent) user).getStudentId())
				throw new Exception("删除失败：学生Id为空！");
			if (secApplicationId != null)
				message.put("tip", secApplicationService.deleteSecApplication(secApplicationId,
						((SysStudent) user).getStudentId()));
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

	// 修改变更申请表
	@RequestMapping(value = "/updatechgapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> updateSecApplication(StudentSecApplication secApplication, SysCompany company,HttpSession session) {
		Map<String, String> message = new HashMap<String, String>();
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
			else if (null == secApplication)
				throw new NullPointerException("修改失败：申请信息不存在！");
			else if (null == company)
				throw new NullPointerException("修改失败：实习单位信息不存在！");
			else if (null == company.getCompanyId())
				throw new Exception("修改失败：实习单位Id为空！");
			// else if(null == secApplication.getTeacherNo() ||
			// application.getTeacherNo().equals(""))
			// throw new Exception("修改失败：指导老师为空！");
			// 检查要修改的申请表的Id是否存在
			if (secApplication.getSecApplyId() != null) {
				// 在申请表中绑定实习单位信息
				secApplication.setNewCompany(company);
				if(secApplicationService.checkForm(secApplication))
					message.put("tip",secApplicationService.updateSecApplication(secApplication, ((SysStudent) user).getStudentId()));
			} else
				message.put("error", "修改失败，请选中申请表！");
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}

	// 上传附件
	@RequestMapping(value = "/uploadfile2", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> uploadFile(MultipartFile file, Integer secApplicationId, HttpSession session) {
		Map<String, String> message = new HashMap<String, String>();
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
				throw new NullPointerException("保存失败：实训周期未开启！");
			else if (null == secApplicationId)
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
				String savepath = "\\static\\apply\\" + startSchoolYear + "\\" + studentNo + "\\secApplication\\"
						+ secApplicationId + "\\";
				message.put("tip", secApplicationService.upLoadFile(secApplicationId, studentId, file, savepath));
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}

	// html查看变更申请表 --学生和教师通用方法，通过session中的useType判断用户类型
	@RequestMapping(value = "/gotohtmlapplication2", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView gotoHTMLApplication(Integer secApplicationId, HttpSession session) {
		ModelAndView mod = new ModelAndView("teacher/chgapplication.btl");
		// 从session中获取当前用户
		Object user = session.getAttribute("user");
		// 从session中获取当前用户类型
		Object userType = session.getAttribute("userType");
		try {
			if (null == user)
				throw new NullPointerException("无法查看：未登录状态！");
			else if (null == userType)
				throw new NullPointerException("无法查看：未知用户类型！");
			else if (null == secApplicationId)
				throw new NullPointerException("无法查看：请选中申请表！");
			// 查询申请表Id对应的申请表
			StudentSecApplication secApplication = secApplicationService.getSecApplicationById(secApplicationId);
			if (null == secApplication)
				throw new NullPointerException("无法查看：未查找到该申请记录！"); // 不存在该申请记录
			else {
				StudentApplication application = secApplication.getApplication();
				if (userType.equals("student")) {
					// 检查查当前的学生是否为申请表中记录的学生
					if (!((SysStudent) user).getStudentId().equals(application.getStudent().getStudentId()))
						throw new Exception("无法查看：非法操作");
				} else if (userType.equals("teacher")) {
					if(LOG.isDebugEnabled()) {
						LOG.debug(((SysStaff) user).getUserCode());
						LOG.debug(application.getTeacherNo());
					}
					// 检查查当前的指导老师是否为申请表中记录的学生的指导老师
					if (((SysStaff) user).getUserCode().equals(application.getTeacherNo())) {
					} else {
						throw new Exception("无法查看：非法操作");
					}
				} else
					throw new Exception("无法查看：非法操作"); // 未知用户类型

				// 获取从申请表中获取学生信息
				SysStudent student = application.getStudent();
				// btl替换数据
				mod.addObject("secApplyId", secApplicationId);
				mod.addObject("studentName", student.getStudentName()); // 姓名
				mod.addObject("studentNo", student.getStudentNo()); // 性别
				mod.addObject("major", student.getMajor()); // 专业
				mod.addObject("classNumber", student.getStudentGrade()+"-"+student.getClassNumber()); // 班级
				mod.addObject("oldCompany", secApplication.getOldComName()); // 原实习单位
				if (null == secApplication.getOldComStartDate() || null == secApplication.getOldComEndDate())
					throw new NullPointerException("原实习开始时间或结束时间为空");
				mod.addObject("oldDate", FormatUtil.formatDateToStr(secApplication.getOldComStartDate(), "yyyy-MM-dd")
						+ " 至 " + FormatUtil.formatDateToStr(secApplication.getOldComEndDate(), "yyyy-MM-dd")); // 原实习起止时间
				mod.addObject("newCompany", secApplication.getNewCompany().getComName()); // 原实习单位
				if (null == secApplication.getNewComStartDate() || null == secApplication.getNewComEndDate())
					throw new NullPointerException("新实习开始时间或结束时间为空");
				mod.addObject("newDate", FormatUtil.formatDateToStr(secApplication.getNewComStartDate(), "yyyy-MM-dd")
						+ " 至 " + FormatUtil.formatDateToStr(secApplication.getNewComEndDate(), "yyyy-MM-dd")); // 原实习起止时间
				mod.addObject("reason", secApplication.getReason()); // 变更原因
				mod.addObject("teacherName", secApplication.getTeacherName()); // 指导老师名字
				mod.addObject("teacherView", secApplication.getTeacherView()); // 指导老师意见
				if (null == secApplication.getTeacherViewDate() || null == secApplication.getTeacherViewDate()) {
					mod.addObject("teacherViewDate", " 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 月&nbsp;&nbsp;&nbsp;&nbsp; 日");

				} else {
					mod.addObject("teacherViewDate",
							FormatUtil.formatDateToStr(secApplication.getTeacherViewDate(), "yyyy年MM月dd日"));
				}
				// 指导老师时间
				mod.addObject("adviserName", secApplication.getAdviserName()); // 辅导员名字
				mod.addObject("adviserView", secApplication.getAdviserView()); // 辅导员意见
				LOG.debug("{}",secApplication.getAdviserViewDate());
				if (secApplication.getAdviserViewDate() == null) {
					mod.addObject("adviserViewDate", " 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 月&nbsp;&nbsp;&nbsp;&nbsp; 日");
				} else {
					mod.addObject("adviserViewDate",
							FormatUtil.formatDateToStr(secApplication.getAdviserViewDate(), "yyyy年MM月dd日")); // 辅导员时间
				}
				if(null!=secApplication.getFilePath()) {
					mod.addObject("filePath", "/getRemoteImgFile?filePath="+secApplication.getFilePath()); // 文件
				}else {
					mod.addObject("filePath", "");
				}
				
				mod.addObject("deptName", secApplication.getDeptName()); // 系（部）主任名字
				mod.addObject("departView", secApplication.getDeptView()); // 系（部）主任意见
				if (null == secApplication.getDeptViewDate() || null == secApplication.getDeptViewDate()) {
					mod.addObject("deptViewDate", " 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 月&nbsp;&nbsp;&nbsp;&nbsp; 日");

				} else {
					mod.addObject("deptViewDate",
							FormatUtil.formatDateToStr(secApplication.getDeptViewDate(), "yyyy年MM月dd日")); // 系（部）主任时间
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mod.addObject("tip", e.getMessage());
		}
		return mod;
	}

	// 提交变更申请表
	@RequestMapping(value = "/submitchgapplication", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, String> submitSecApplication(Integer secApplicationId, HttpSession session) {
		Map<String, String> message = new HashMap<String, String>();
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
				throw new NullPointerException("保存失败：实训周期未开启！");
			else if (null == ((SysStudent) user).getStudentId())
				throw new Exception("提交失败：学生Id为空！");
			if (secApplicationId != null)
				message.put("tip", secApplicationService.submitSecApplication(secApplicationId,((SysStudent) user).getStudentId()));
			else
				message.put("error", "提交失败，请选中申请表！");
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error", e.getMessage());
		}
		return message;
	}

	// 返回审批日志
	@RequestMapping(value = "/getreviewlog2", method = { RequestMethod.GET, RequestMethod.POST })
	public List<SysReviewLog> getReviewLog(Integer secApplicationId, HttpSession session) {
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
			else if (null == secApplicationId)
				throw new RuntimeException("读取审批记录失败：请选中申请表！");
			return secApplicationService.getReviewLog(secApplicationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
