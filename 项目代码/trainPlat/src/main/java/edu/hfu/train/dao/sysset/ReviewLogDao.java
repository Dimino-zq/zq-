package edu.hfu.train.dao.sysset;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import edu.hfu.train.bean.SysReviewLog;
import edu.hfu.train.dao.base.BaseDaoImpl;


@Repository
public class ReviewLogDao {
	@Resource
	private BaseDaoImpl dao;
	@Autowired
	private HttpSession session;

	/**
	 * 
	 * @param recordType 写表名 
	 * @param recordId 
	 * @param action
	 * @param memo
	 * @throws Exception
	 */
	public void addReviewLog(String recordType,Integer recordId,String action,String oldStatus,String newStatus,String memo) throws Exception {
		SysReviewLog lg=new SysReviewLog();
		lg.setCreateDate(new Date());
		lg.setRecordType(recordType);
		lg.setRecordId(recordId);
		lg.setAction(action);
		lg.setOldStatus(oldStatus);
		lg.setNewStatus(newStatus);
		lg.setMemo(memo);
		String userType=(String) session.getAttribute("userType");
		String userCode=(String) session.getAttribute("userCode");
		String userName=(String) session.getAttribute("userName");
		lg.setUserCode(userCode);
		lg.setUserType(userType);
		lg.setUserName(userName);
		dao.save(lg);
	}
	/**
	 * 查询 某个科研项目的 审批记录
	 * @param recordType
	 * @param recordId
	 * @return
	 * @throws Exception
	 */
	public List<SysReviewLog> findLog(String recordType,Integer recordId)throws Exception{
		String hql="from SysReviewLog where recordType=?0 and recordId=?1";
		return dao.find(hql, recordType,recordId);
	}

	/**
	 * 新增记录
	 * @param recordType 记录类型，例：实习申请，实习变更申请
	 * @param recordId 记录编号，找到唯一记录
	 * @param action 动作，例：提交，通过，未通过
	 * @param oldStatus 操作前状态
	 * @param newStatus 操作后状态
	 * @param memo 备注
	 * @param toUserCode 目标阅读人编号
	 * @param toUserType 目标阅读人类型
	 * @throws Exception
	 */
	public void addReviewLog(String recordType,Integer recordId,String action,String oldStatus,String newStatus,String memo,String toUserCode,String toUserType) throws Exception {
		SysReviewLog lg=new SysReviewLog();
		lg.setCreateDate(new Date());
		lg.setRecordType(recordType);
		lg.setRecordId(recordId);
		lg.setAction(action);
		lg.setOldStatus(oldStatus);
		lg.setNewStatus(newStatus);
		lg.setMemo(memo);
		String userType=(String) session.getAttribute("userType");
		String userCode=(String) session.getAttribute("userCode");
		String userName=(String) session.getAttribute("userName");
		lg.setUserCode(userCode);
		lg.setUserType(userType);
		lg.setUserName(userName);
		lg.setToUserCode(toUserCode);
		lg.setToUserType(toUserType);
		dao.save(lg);
	}

	/**
	 * 根据记录类型和用户代码查询审批记录
	 * @param recordType
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public List<SysReviewLog> findLog(String recordType,String userCode)throws Exception{
		String hql="from SysReviewLog where userCode=?0 or toUserCode=?1 and recordType=?2 order by createDate";
		return dao.find(hql, userCode,userCode,recordType);		//返回创建者或目标阅读者都为@userCode的记录
	}
}
