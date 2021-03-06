package edu.hfu.train.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import edu.hfu.train.util.CacheData;


@SuppressWarnings("serial")
@Entity
public class SysReviewLog extends SysBaseTable {
	private Integer log_id;
	private String userCode;
	private String userName;
	private String userType; //用户类型 student  teacher
	private String recordType;
	private Integer recordId;
	private String action;//动作
	private String oldStatus;
	private String newStatus;
	private String chgStatus;
	private String toUserCode;
	private String toUserType;
	private String readStatus;
	private String memo;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//主键生成策略
	public Integer getLog_id() {
		return log_id;
	}
	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}
	@Column(length = 50,nullable = false)
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	@Column(length = 50,nullable = false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getToUserType() {
		return toUserType;
	}
	public void setToUserType(String toUserType) {
		this.toUserType = toUserType;
	}
	@Column(length = 50,nullable = false)
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public Integer getRecordId() {
		return recordId;
	}
	@Column(nullable = false)
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
	@Column(length = 50,nullable = false)
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	@Column(length = 50,nullable = false)
	public String getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	@Column(length = 500)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Transient
	public String getChgStatus() {
		String oldstatusValue=CacheData.getDictValByKey(this.oldStatus, "审批状态");
		if (oldstatusValue=="") {
			oldstatusValue=this.oldStatus;
		}
		String newstatusValue=CacheData.getDictValByKey(this.newStatus, "审批状态");
		if (newstatusValue=="") {
			newstatusValue=this.newStatus;
		}
		chgStatus=oldstatusValue+"->"+newstatusValue;
		return chgStatus;
	}
	public void setChgStatus(String chgStatus) {
		this.chgStatus = chgStatus;
	}
	@Column(length = 50)
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Column(length = 10)
	public String getReadStatus() {
		if(null==readStatus) {
			readStatus="未读";
		}
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	@Column(length = 50)
	public String getToUserCode() {
		return toUserCode;
	}
	public void setToUserCode(String toUserCode) {
		this.toUserCode = toUserCode;
	}
	
	
}
