package edu.hfu.train.service.sysset;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.hfu.train.bean.SysLevel;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.sysset.SysStudentDao;
import edu.hfu.train.service.remote.AuthGrantService;
import edu.hfu.train.util.IdcardValidatorUtil;
import edu.hfu.train.util.daoru.ExcelPoi;

@Service
@Transactional
public class SysStudentService {
	private final Logger LOG = LoggerFactory.getLogger(SysStudentService.class);
	@Resource
	SysStudentDao studentdao;

	@Resource
	AuthGrantService authGrantService;

	public List<SysStudent> getAllStudent() throws Exception {
		return studentdao.getAllStudent();
	}

	public List<SysStudent> getStudentByCon(SysStudent stu) throws Exception {
		return studentdao.getStudentByCon(stu);
	}

	public List<SysStudent> getStudentByCon(SysStudent stu, int pageNo, int pageSize) throws Exception {
		return studentdao.getStudentByCon(stu, pageNo, pageSize);
	}

	public int getStudentCountByCon(SysStudent stu) throws Exception {
		return studentdao.getStudentCountByCon(stu);
	}

	public String saveSysStudent(SysStudent student) throws Exception {
		SysStudent sysstudent = new SysStudent();
		sysstudent.setStudentNo(student.getStudentNo());
		List<SysStudent> ls = getStudentByCon(sysstudent);
		if (null == ls || ls.size() == 0) {
			// 123456
			student.setPassword("e10adc3949ba59abbe56e057f20f883e");
			student.setTrainStatus("0");	//新加入的学生实习状态默认为“未完成实习”
			student.setUpdDate(new Date());
			student.setCreateDate(new Date());
			studentdao.saveSysStudent(student);
			return "succ";
		} else {
			return student.getStudentNo() + "重复！";
		}

	}

	public String updataSysStudent(SysStudent student) throws Exception {
		if( null ==student || null==student.getStudentId())
			throw new NullPointerException("参数缺失，学生id不能为空");
		SysStudent oldStu = studentdao.getStudentById(student.getStudentId());
		//若修改了学生的学号则需要判断修改后的学号是否被占用
		if(!oldStu.getStudentNo().equals(student.getStudentNo()))
		{
			SysStudent lsStu = new SysStudent();
			lsStu.setStudentNo(student.getStudentNo());
			List<SysStudent> ls = getStudentByCon(lsStu);
			if (null == ls || ls.size() == 0)
				oldStu.setStudentNo(student.getStudentNo());
			else
				throw new RuntimeException("修改失败：该学号已存在！");
		}
		oldStu.setStudentNo(student.getStudentNo());
		oldStu.setStudentName(student.getStudentName());
		oldStu.setStudentSex(student.getStudentSex());
		oldStu.setBirthDay(student.getBirthDay());
		oldStu.setDepart(student.getDepart());
		oldStu.setMajor(student.getMajor());
		oldStu.setStudentGrade(student.getStudentGrade());
		oldStu.setClassNumber(student.getClassNumber());
		oldStu.setPolitics(student.getPolitics());
		oldStu.setNation(student.getNation());
		oldStu.setNativeplace(student.getNativeplace());
		oldStu.setIdentity(student.getIdentity());
		oldStu.setUpdDate(new Date());
		oldStu.setUpdUser(student.getUpdUser());
		studentdao.updateStudent(oldStu);
		return "succ";
	}

	/**
	 * 删除一条学生信息
	 * 
	 * @param studentId
	 *            Integer类型
	 * @throws Exception
	 */
	public void delStudent(Integer studentId) throws Exception {
		studentdao.delStudent(studentId);
	}

	/**
	 * 重置一名学生的密码
	 * 
	 * @param studentId
	 * @param user
	 *            当前登录的用户，其身份类型应该为teacher
	 * @return
	 * @throws Exception
	 */
	public String resetPassword(Integer studentId, SysStaff user) throws Exception {
		SysStudent stu = new SysStudent();
		stu = studentdao.getStudentById(studentId);
		if (null == stu) {
			LOG.debug("{}","无此记录，错误的学生id");
			return "error";
			
			
		} else {
			//默认密码123456
			stu.setPassword("e10adc3949ba59abbe56e057f20f883e");
			stu.setUpdDate(new Date());
			stu.setUpdUser(user.getUserCode());
			studentdao.updateStudent(stu);
			return "succ";
		}
	}

	/**
	 * 校验及导入文件
	 * 
	 * @param file
	 * @param user
	 *            当前登录的用户，其身份类型应该为teacher
	 * @return
	 * @throws Exception
	 */
	public List<String> importExcel(MultipartFile file, SysStaff user) throws Exception {
		List<String> message = new ArrayList<String>();
		int i = 2;// Excel中数据开始的行数

		List<SysStudent> list = new ExcelPoi<SysStudent>().importObjectList(file.getInputStream(),
				file.getOriginalFilename(), SysStudent.class);
		Iterator<SysStudent> iterator = list.iterator();
		// 遍历学生信息
		while (iterator.hasNext()) {
			String mess = "";
			SysStudent student = iterator.next();
			// 校验学号字段
			String studentNo = student.getStudentNo();
			if (studentNo != null && !"".equals(studentNo)) {
				SysStudent stu = new SysStudent();
				stu.setStudentNo(studentNo);
				if (getStudentByCon(stu).size() > 0)
					mess = "学号为 " + studentNo + " 的学生已存在";
			} else
				mess = "学号为空";

			// 校验姓名字段
			String studentName = student.getStudentName();
			if (studentName == null || "".equals(studentName))
				mess += "".equals(mess) ? "姓名为空" : "，姓名为空";

			// 校验性别字段
			String studentGender = student.getStudentSex();
			if (studentGender != null && studentGender != "") {
				if (!"男".equals(studentGender) && !"女".equals(studentGender))
					mess += "".equals(mess) ? ("性别 " + studentGender + " 错误") : ("，性别 " + studentGender + " 错误");
			}

			// 校验系部和专业字段
			String eduDepart = student.getDepart();
			String major = student.getMajor();
			if (eduDepart != null && eduDepart != "") {
				if (major != null && major != "") {
					int check = authGrantService.checkEduDepartName(eduDepart, major);
					if (check == 3)
						mess += "".equals(mess) ? ("院系 " + eduDepart + " 不存在") : ("，院系 " + eduDepart + " 不存在");
					else if (check == 2)
						mess += "".equals(mess) ? ("专业 " + major + " 不存在") : ("，专业 " + major + " 不存在");
				} else
					mess += "".equals(mess) ? ("专业为空") : ("，专业为空");
			} else
				mess += "".equals(mess) ? ("院系为空") : ("，院系为空");

			// 校验生日
			Date birthday = student.getBirthDay();
			if (null == birthday)
				mess += "".equals(mess) ? ("生日为空或格式错误") : ("，生日为空或格式错误");
			// 校验政治面貌
			String politic = student.getPolitics();
			if (null == politic || "".equals(politic))
				mess += "".equals(mess) ? ("政治面貌为空") : ("，政治面貌为空");
			// 校验民族
			String nation = student.getNation();
			if (null == nation || "".equals(nation))
				mess += "".equals(mess) ? ("民族为空") : ("，民族为空");
			// 校验籍贯
			String place = student.getNation();
			if (null == place || "".equals(place))
				mess += "".equals(mess) ? ("籍贯为空") : ("，籍贯为空");
			// 校验身份证
			String idcard = student.getIdentity();
			if (null == idcard || "".equals(idcard))
				mess += "".equals(mess) ? ("身份证为空") : ("，身份证为空");
			else if (!IdcardValidatorUtil.isValidatedAllIdcard(idcard))
				mess += "".equals(mess) ? ("身份证格式错误") : ("，身份证格式错误");

			if (!"".equals(mess)) {
				mess = "第" + i + "行数据有误，" + mess + "，请检查数据！";
				message.add(mess);
			}
			i++;
		}

		if (message.size() == 0) {
			iterator = list.iterator();
			while (iterator.hasNext()) {
				SysStudent student = iterator.next();
				// 123456
				student.setPassword("e10adc3949ba59abbe56e057f20f883e");
				student.setTrainStatus("0");	//新加入的学生实习状态默认为“未完成实习”
				student.setCreateDate(new Date());
				student.setUpdDate(new Date());
				student.setCreateUser(user.getUserCode());
				student.setUpdUser(user.getUserCode());
				saveSysStudent(student);
			}
		}

		return message;
	}

	public List<SysStudent> getStudent(SysStudent student) throws Exception {
		// TODO Auto-generated method stub

		return studentdao.getStudent(student);
	}
	
	/**
	 * 获取最新一批进入实习的年级
	 * @return String
	 * @throws Exception
	 */
	public List<String> getLatestGrade() throws Exception
	{
		return studentdao.getLatestGrade();
	}
}
