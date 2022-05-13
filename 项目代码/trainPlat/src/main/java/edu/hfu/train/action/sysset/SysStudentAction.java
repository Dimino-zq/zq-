package edu.hfu.train.action.sysset;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import edu.hfu.train.bean.SysDepart;
import edu.hfu.train.bean.SysMajor;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.config.CustomPermissionEvaluator;
import edu.hfu.train.service.remote.AuthGrantService;
import edu.hfu.train.service.sysset.SysStudentService;
import edu.hfu.train.util.Constant;
import feign.FeignException;

@RestController
@RequestMapping(value = "/student")
public class SysStudentAction {
	@Resource
	SysStudentService studentservice;
	
	@Resource
	AuthGrantService authGrantService;

	private final Logger LOG = LoggerFactory.getLogger(SysStudentAction.class);
	
	@RequestMapping(value = "/gotostudent",method = {RequestMethod.GET,RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_0100')")
	public ModelAndView gotoStudent()
	{
		ModelAndView mod= new ModelAndView("sysset/sysstudent.btl");
		return mod;
	}
	
	
	
	@RequestMapping(value = "/getStudentByCon",method = {RequestMethod.GET,RequestMethod.POST})
	@PreAuthorize("hasPermission('/getStudentByCon','010001000')")	//判断是否有查询权限
	public Map<String,Object> getStudentByCon(SysStudent stu,int page, int rows, HttpSession session)
	{
		Map<String,Object> map=new HashMap<String, Object>();
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		if (null == user)
			throw new NullPointerException("查询失败：未登录状态！");
		else if(!session.getAttribute("userType").equals("teacher"))
			throw new ClassCastException("查询失败：用户类型不匹配！");
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			//判断是否有查询所有学生的权限
			if (CustomPermissionEvaluator.hasPermission(auth, "010001002")) {
//				stu.setDepart(((SysStaff) user).getStaffDepart());
				//仅查询当前登陆者所在院系的数据
				stu.setDepart(((SysStaff) user).getStaffParentDepart());
			} else if (CustomPermissionEvaluator.hasPermission(auth, "010001001")) {
				stu.setDepart(null);
			} 
			//判断查询条件中是否有年级存在,不存在则默认查询最大年级的学员
			if(null == stu.getStudentGrade() || "".equals(stu.getStudentGrade()))
			{
				stu.setStudentGrade(null);
			}
			else if("all".equals(stu.getStudentGrade()))	//为“all”时查询所有年级的学员
			{
				stu.setStudentGrade(null);
			}
			System.out.println(stu);
			List<SysStudent> ls=studentservice.getStudentByCon(stu,page,rows);
			int count=studentservice.getStudentCountByCon(stu);
			map.put("rows", ls);
			map.put("total",count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	@RequestMapping(value = "/saveStudent",method = {RequestMethod.GET,RequestMethod.POST})
	@PreAuthorize("hasPermission('/saveStudent','010001004')")
	public String saveStudent(SysStudent student, HttpSession session)
	{
		String mess="";
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		if (null == user)
			throw new NullPointerException("保存失败：未登录状态！");
		else if(!session.getAttribute("userType").equals("teacher"))
			throw new ClassCastException("保存失败：用户类型不匹配！");
		try {
			student.setCreateUser(((SysStaff) user).getUserCode());
			student.setUpdUser(((SysStaff) user).getUserCode());
			mess=studentservice.saveSysStudent(student);
		} catch (Exception e) {
			e.printStackTrace();
			mess=e.toString();
		}
		return mess;
	}
	
	@RequestMapping(value = "/updateStudent",method = {RequestMethod.GET,RequestMethod.POST})
	@PreAuthorize("hasPermission('/updateStudent','010001005')")
	public Map<String, Object> updateStudent(SysStudent student, HttpSession session)
	{
		Map<String, Object> mess= new HashMap<String, Object>();
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		if (null == user)
			throw new RuntimeException("查询失败：未登录状态！");
		else if(!session.getAttribute("userType").equals("teacher"))
			throw new RuntimeException("查询失败：用户类型不匹配！");
		try {
			//Date birthDay=DateUtil.strToDate(birthDayVal, "yyyy-MM-dd");
			//student.setBirthDay(birthDay);
			//student.setDepart(depart);
			student.setUpdUser(((SysStaff) user).getUserCode());
			mess.put("tip", studentservice.updataSysStudent(student));
		} catch (RuntimeException e) {
			LOG.debug("{}",e.getMessage());
			mess.put("tip", e.getMessage());
		} catch (Exception e) {
			LOG.debug("{}",e.getMessage());
			e.printStackTrace();
		}
		return mess;
	}
	
	//跟据学生id删除一条学生数据
    @RequestMapping(value = "/delstudent",method = {RequestMethod.GET,RequestMethod.POST})
    @PreAuthorize("hasPermission('/delstudent','010001006')")
    public Map<String, String> delStudent(SysStudent student, HttpSession session)
    {
    	Map<String, String> mess = new HashMap<String, String>();
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		if (null == user)
			throw new RuntimeException("删除失败：未登录状态！");
		else if(!session.getAttribute("userType").equals("teacher"))
			throw new RuntimeException("删除失败：用户类型不匹配！");
        try {
        	studentservice.delStudent(student.getStudentId());
            mess.put("tip", "succ");
        }catch (PersistenceException | SQLIntegrityConstraintViolationException e) {
        	LOG.debug("{}",e.getMessage());
        	mess.put("tip", "该学员已进入实习状态，无法删除！");
		}catch (RuntimeException e) {
			LOG.debug("{}",e.getMessage());
			mess.put("tip", e.getMessage());
		}catch (Exception e) {
        	e.printStackTrace();
        	mess.put("tip", "未知错误！");
        }
        return mess;
    }
  
    //重置学生密码
    @RequestMapping(value = "/resetpassword",method = RequestMethod.POST)
    @PreAuthorize("hasPermission('/resetpassword','010001007')")
    public String resetPassword(Integer studentId, HttpSession session)
    {
    	String mess = "";
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		if (null == user)
			throw new NullPointerException("查询失败：未登录状态！");
		else if(!session.getAttribute("userType").equals("teacher"))
			throw new ClassCastException("查询失败：用户类型不匹配！");
        try {
        	if(null !=studentId)
        		mess = studentservice.resetPassword(studentId, (SysStaff) user);
        	else
        	{
        		System.out.println("学生id为空");
        		mess = "error";
        	}
        } catch (Exception e) {
        	mess = e.toString();
        }
        return mess;
    }
    
    //获取院系
    @RequestMapping(value = "/getdepart",method = RequestMethod.POST)
    public Object getDepart()
    {
    	Object mess = null;
    	Map<String, Object> message = new HashMap<String, Object>();
    	try {
    		mess = authGrantService.getAllEduDepart();
		} catch (FeignException e) {
            LOG.debug("{}",e.getMessage());
            if(e.getMessage().indexOf("授权信息错误")!= -1)
            	message.put("error","授权信息已过期，请重新登录！");
            else
            	message.put("error","未知错误！");
            mess = message;
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error","未知错误！");
			mess = message;
		}
    	return mess;
    }
    
    //获取专业
    @RequestMapping(value = "/getmajor",method = RequestMethod.POST)
    public Object getMajor(Integer departId)
    {
    	Object mess = null;
    	Map<String, Object> message = new HashMap<String, Object>();
    	try {
    		mess = authGrantService.getMajorByDept(departId);
		} catch (FeignException e) {
            LOG.debug("{}",e.getMessage());
            if(e.getMessage().indexOf("授权信息错误")!= -1)
            	message.put("error","授权信息已过期，请重新登录！");
            else
            	message.put("error","未知错误！");
            mess = message;
		} catch (Exception e) {
			e.printStackTrace();
			message.put("error","未知错误！");
			mess = message;
		}
    	return mess;
    }

	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @PreAuthorize("hasPermission('/importExcel','010001008')")
	public Map<String,Object> importExcel(MultipartFile file, HttpSession session,HttpServletRequest request)
	{
		Map<String,Object> message = new HashMap<String, Object>();
		//从session中获取当前用户
		Object user = session.getAttribute("user");
		if (null == user)
			throw new RuntimeException("查询失败：未登录状态！");
		else if(!session.getAttribute("userType").equals("teacher"))
			throw new RuntimeException("查询失败：用户类型不匹配！");
		try {
			if (file != null)
			{
					if(file.getOriginalFilename().endsWith(Constant.EXCEL_EXTENSION))
					{
						message.put("tip",studentservice.importExcel(file, (SysStaff) user));
					}
					else
					{
						message.put("error","文件类型错误，请选择*.xlsx文件导入！");
					}
			}
			else
			{
				message.put("error","文件上传失败！");
			}
			
		} catch (FeignException e) {
            LOG.debug("{}",e.getMessage());
            if(e.getMessage().indexOf("授权信息错误")!= -1)
            	message.put("error","授权信息已过期，请重新登录！");
            else
            	message.put("error","未知错误！");
		} catch(ConstraintViolationException | SQLIntegrityConstraintViolationException e) {
            LOG.debug("{}",e.getMessage());
            message.put("error","Excel表格中有学号重复的学员");
		} catch (RuntimeException e) {
            LOG.debug("{}",e.getMessage());
            message.put("error",e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			//message.put("error",e.toString());
		}
		return message;
	}
}
