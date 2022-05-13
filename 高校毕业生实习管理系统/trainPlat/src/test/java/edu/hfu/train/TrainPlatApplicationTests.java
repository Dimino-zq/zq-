package edu.hfu.train;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import edu.hfu.train.action.student.StudentApplicationAction;
import edu.hfu.train.bean.StudentApplication;
import edu.hfu.train.bean.StudentRecord;
import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.StudentWorkRec;
import edu.hfu.train.bean.SysCompany;
import edu.hfu.train.bean.SysDepartTrainCycle;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.dao.student.StudentApplicationDao;
import edu.hfu.train.dao.student.StudentRecordDao;
import edu.hfu.train.dao.student.StudentSecApplicationDao;
import edu.hfu.train.dao.sysset.SysStudentDao;
import edu.hfu.train.service.student.StudentApplicationService;
import edu.hfu.train.service.student.StudentRecordService;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@SpringBootTest
class TrainPlatApplicationTests {
	
	private final Logger LOG = LoggerFactory.getLogger(TrainPlatApplicationTests.class);

	@Resource
	StudentRecordService recordService;
	
	@Resource
	StudentRecordDao recordDao;
	
	@Resource
	StudentApplicationDao applicationDao;

	@Resource
	StudentSecApplicationDao secApplicationDao;

	@Resource
	StudentApplicationService applicationService;
	
	@Resource
	SysStudentDao studentDao;
	
	@Resource
	StudentApplicationAction applicationAction;
	
	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	@Before(value = "")
	public void setUp(){
		request =new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response =new MockHttpServletResponse();
	}
	
	@Test
	void contextLoads() {
		this.setUp();
		HttpSession session = request.getSession(true);
		SysStudent student = new SysStudent();
		student.setStudentId(176);
		session.setAttribute("user", student);
		session.setAttribute("userType", "student");
		session.setAttribute("cycleDepart", new SysDepartTrainCycle());
		
		//SysStudent student = new SysStudent();
		SysCompany company = new SysCompany();
		company.setCompanyId(123);
		
		StudentApplication studentApplication = new StudentApplication();
		studentApplication.setAdress("test");
		studentApplication.setApplydate(new Date());
		studentApplication.setEndDate(new Date());
		studentApplication.setInsurance("test");
		studentApplication.setPhoneOrQQ("test");
		studentApplication.setSurcomcontent("");
		studentApplication.setSurcomjob("test");
		studentApplication.setTeacherNo("test");
		System.out.println("========================================");
		
		
		if((String) applicationAction.saveApplication(studentApplication, company, session).get("error") == "请输入实习内容！")
			System.out.println("测试成功!");
		else
			System.out.println("测试失败!");
		System.out.println("========================================");

//		//根据学生查询自主实习申请
//		try {
//
//		} catch (Exception e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}

	}


}
