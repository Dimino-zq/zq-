package edu.hfu.train.dao.teacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.StudentSecApplication;
import edu.hfu.train.bean.SysStaff;
import edu.hfu.train.bean.SysStudent;
import edu.hfu.train.config.CustomPermissionEvaluator;
import edu.hfu.train.dao.base.BaseDaoImpl;
import edu.hfu.train.dao.sysset.ReviewLogDao;

@Repository
public class TeacherSecApplicationDao {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Resource
	BaseDaoImpl baseDaoImpl;
	@Resource
	ReviewLogDao reviewLogDao;

	/**
	 * 根据老师编号查找申请表
	 * 
	 * @param teacher
	 * @return
	 * @throws Exception
	 */
	public List<StudentSecApplication> getStudentSecByCon(Authentication auth, String a, SysStaff userBackStaff,
			StudentSecApplication stuSecApp, SysStudent stu, int page, int rows) throws Exception {

		List<Object> params = new ArrayList<Object>();
		int index = 0;
		String staff = userBackStaff.getPoststr();

		if (LOG.isDebugEnabled()) {
			LOG.debug("{}", "staff:" + staff);
			LOG.debug("{}", userBackStaff.getStaffDepart() + "|" + userBackStaff.getStaffParentDepart() + "|"
					+ userBackStaff.getDepartId() + "|" + userBackStaff.getStaffAddr());
		}

		String hql = "from StudentSecApplication stuSecApp ";
		if (CustomPermissionEvaluator.hasPermission(auth, "010101002")) {
			hql += (index == 0 ? " where (" : " and") + "  stuSecApp.application.teacherNo=? " + index++;
			params.add(a);
		}

		if (null != stu) {
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += (index == 0 ? " where" : " and") + "  stuSecApp.application.student.studentName=?" + index++;
				params.add(stu.getStudentName());
			}
		}
		if (CustomPermissionEvaluator.hasPermission(auth, "010101004")) {
			hql += (index == 0 ? " where" : " or") + "  stuSecApp.application.student.depart=? " + index++;
			params.add(userBackStaff.getStaffParentDepart());
		}

		if (null != stuSecApp) {
			if ("查询所有".equals(stuSecApp.getStatus())) {
				hql += ") and (status='DApprove' or status='CApprove' or status='BApprove' )";
			} else if (null != stuSecApp.getStatus() && !"".equals(stuSecApp.getStatus())) {
				hql += ") and status=?" + index++;
				params.add(stuSecApp.getStatus());
			} else {
				hql += ") and ( status='CApprove' )";
			}
		}
		return baseDaoImpl.findByPage(hql, params.toArray(), page, rows);

	}

	public Integer getSecApplicationCount(Authentication auth, String a, SysStaff userBackStaff,
			StudentSecApplication stuSecApp, SysStudent stu) throws Exception {
		List<Object> params = new ArrayList<Object>();
		int index = 0;
		String staff = userBackStaff.getPoststr();
		String hql = "from StudentSecApplication stuSecApp ";
		if (CustomPermissionEvaluator.hasPermission(auth, "010101002")) {
			hql += (index == 0 ? " where (" : " and") + "  stuSecApp.application.teacherNo=? " + index++;
			params.add(a);
		}

		if (null != stu) {
			if (null != stu.getStudentName() && !"".equals(stu.getStudentName())) {
				hql += (index == 0 ? " where" : " and") + "  stuSecApp.application.student.studentName=?" + index++;
				params.add(stu.getStudentName());
			}
		}
		if (CustomPermissionEvaluator.hasPermission(auth, "010101004")) {
			hql += (index == 0 ? " where" : " or") + "  stuSecApp.application.student.depart=? " + index++;
			params.add(userBackStaff.getStaffParentDepart());
		}
		if (null != stuSecApp) {
			if ("查询所有".equals(stuSecApp.getStatus())) {
				hql += ") and (status='DApprove' or status='CApprove' or status='BApprove' )";
			} else if (null != stuSecApp.getStatus() && !"".equals(stuSecApp.getStatus())) {

				hql += ") and status=?" + index++;
				params.add(stuSecApp.getStatus());
			} else {
				hql += ") and ( status='CApprove' )";
			}
		}
		return baseDaoImpl.queryBeanCountByHql(hql, params.toArray());

	}

	public List<StudentSecApplication> getAudit(String studentNo, String status) throws Exception {
		String hql = "update StudentSecApplication u set u.status='" + status + "' where studentNo=?0";
		return baseDaoImpl.find(hql, studentNo);

	}

	// 老师将待审批改为未通过
	public void refuseTeacherSec(Authentication auth, SysStaff userBackStaff, StudentSecApplication stuSecApp,
			String applyId) throws Exception {

		String hql = "update StudentSecApplication stuSecApp ";
		StudentSecApplication stuSecAppli = new StudentSecApplication();
		stuSecAppli.setTeacherViewDate(new Date());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Object> params = new ArrayList<Object>();
		int index = 0;
		String staff = userBackStaff.getPoststr();
		if (CustomPermissionEvaluator.hasPermission(auth, "010101002")) {
			System.out.println(stuSecApp.toString());
			System.out.println(index);
			hql += (index == 0 ? "set" : ",") + " teacherview='" + stuSecApp.getTeacherView() + "',teacherName='"
					+ userBackStaff.getStaffName() + "' " + ",teacherviewDate='" + format.format(new Date()) + "'";
			index++;
			reviewLogDao.addReviewLog("StudentSecApplication", stuSecApp.getSecApplyId(), "审批", "CApprove", "FApprove",
					"实习变更:老师拒绝理由为:" + stuSecApp.getTeacherView() + "!");
		}
		hql += ",status='FApprove' where status='CApprove' and  secapplyId='" + stuSecApp.getSecApplyId() + "'";
		baseDaoImpl.bulkSqlUpdate(hql);
	}

	// 系主任将待审批改为未通过
	public void refuseDeptSec(Authentication auth, SysStaff userBackStaff, StudentSecApplication stuSecApp,
			String applyId) throws Exception {

		String hql = "update StudentSecApplication stuSecApp ";
		StudentSecApplication stuSecAppli = new StudentSecApplication();
		stuSecAppli.setTeacherViewDate(new Date());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Object> params = new ArrayList<Object>();
		int index = 0;
		String staff = userBackStaff.getPoststr();

		if (CustomPermissionEvaluator.hasPermission(auth, "010101004")) {
			hql += (index == 0 ? "set" : ",") + " deptView='" + stuSecApp.getDeptView() + "', deptName='"
					+ userBackStaff.getStaffName() + "'" + ",deptViewDate='" + format.format(new Date()) + "'";
			index++;
			reviewLogDao.addReviewLog("StudentSecApplication", stuSecApp.getSecApplyId(), "审批", "CApprove", "FApprove",
					"实习变更:系主任拒绝理由为:" + stuSecApp.getDeptView() + "!");
		}

		hql += ",status='FApprove' where status='CApprove' and  secapplyId='" + stuSecApp.getSecApplyId() + "'";
		baseDaoImpl.bulkSqlUpdate(hql);

	}

	// 老师通过
	// 将已提交改为已通过
	public void passTeacherSec(Authentication auth, String a, SysStaff userBackStaff, StudentSecApplication stuSecApp,
			String applyId) throws Exception {
		if (CustomPermissionEvaluator.hasPermission(auth, "010101002")) {

			String hql = "update StudentSecApplication stuSecApp ";
			StudentSecApplication stuSecAppli = new StudentSecApplication();
			stuSecAppli.setTeacherViewDate(new Date());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Object> params = new ArrayList<Object>();
			int index = 0;
			String staff = userBackStaff.getPoststr();

			if (CustomPermissionEvaluator.hasPermission(auth, "010101002")) {
				System.out.println(stuSecApp.toString());
				System.out.println(index);
				hql += (index == 0 ? "set" : ",") + " teacherview='" + stuSecApp.getTeacherView() + "',teacherName='"
						+ userBackStaff.getStaffName() + "' " + ",teacherviewDate='" + format.format(new Date()) + "'";
				index++;
				reviewLogDao.addReviewLog("StudentSecApplication", stuSecApp.getSecApplyId(), "审批", "CApprove",
						"DApprove", "实习变更:老师审批通过!");

			}

			hql += " where status='CApprove' and  secapplyId='" + stuSecApp.getSecApplyId() + "'";
			baseDaoImpl.bulkSqlUpdate(hql);

			reviewLogDao.addReviewLog("StudentSecApplication", stuSecApp.getSecApplyId(), "审批", "CApprove", "DApprove",
					"实习变更:老师审批通过!等待系主任审批！");

		}

	}

	// 系主任通过,并且作废原来申请表
	public void passDeptSec(Authentication auth, String a, SysStaff userBackStaff, StudentSecApplication stuSecApp,
			String applyId) throws Exception {
		if (CustomPermissionEvaluator.hasPermission(auth, "010101004")) {
			String hql = "update StudentSecApplication stuSecApp ";
			StudentSecApplication stuSecAppli = new StudentSecApplication();
			stuSecAppli.setTeacherViewDate(new Date());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<Object> params = new ArrayList<Object>();
			int index = 0;
			String staff = userBackStaff.getPoststr();

			hql += (index == 0 ? "set" : ",") + " deptView='同意', deptName='" + userBackStaff.getStaffName()
					+ "',status='DApprove'" + ",deptViewDate='" + format.format(new Date()) + "'";
			updateDownStudentApplication(stuSecApp, applyId);
			updateDownStudentSecApplication(stuSecApp, applyId);
			reviewLogDao.addReviewLog("StudentSecApplication", stuSecApp.getSecApplyId(), "审批", "CApprove", "DApprove",
					"实习变更:系主任审批通过!变更审批通过!");
			index++;
			hql += " where status='CApprove' and  secapplyId='" + stuSecApp.getSecApplyId() + "'";
			baseDaoImpl.bulkSqlUpdate(hql);
		}
	}

	public StudentSecApplication getSecApplicationById(Integer secApplicationId) throws Exception {
		return baseDaoImpl.get(StudentSecApplication.class, secApplicationId);
	}

	// 作废申请表
	public void updateDownStudentApplication(StudentSecApplication stuSecApp, String applyId) throws Exception {
		String hql = "update StudentApplication set status='EApprove' where applyId='" + applyId + "'";
		baseDaoImpl.bulkSqlUpdate(hql);

	}

	// 作废变更表
	public void updateDownStudentSecApplication(StudentSecApplication stuSecApp, String applyId) throws Exception {
		String hql = "update StudentSecApplication set status='EApprove' where status='DApprove' and applyId='"
				+ applyId + "'";
		baseDaoImpl.bulkSqlUpdate(hql);
	}

}
