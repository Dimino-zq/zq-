package edu.hfu.train.bean;



import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.hfu.train.util.CacheData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class StudentApplication  extends SysBaseTable{/**
	 * 
	 */
	private static final long serialVersionUID = 3863513146148129237L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	private Integer applyId;  //实习申请编号
	@Column(length = 100)
	private String surcomjob;  //实习岗位
	
	@Column(nullable = true)
	private Integer detailId;  //申请岗位编号
	
	@Column(length = 50)
	private String insurance;  //保险情况
	@Column(length = 150)
	private String surcomcontent;  //实习内容
	@Column(length = 100)
	private String adress;  //实习住宿地址
	@Column(length = 20)
	private String phoneOrQQ;  //学生联系方式
	@Column(length = 10, nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date applydate ;  //申请日期
	@Column(length = 10, nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate;  //结束日期
	@Column(length = 10)
	private String teacherName;  //指导老师姓名
	@Column(length = 10, nullable = false)
	private String teacherNo;  //指导老师工号
	@Column(length = 500)
	private String filePath;  //附件地址
	@Column(length = 500)
	private String appraisalFromFirmPath;  //鉴定表附件地址
	@Column(length = 200)
	private String deptView;  //系部意见
	@Column(length = 10, nullable = false)
	private String status;  //实习申请状态
	@Transient
	private String statusVal;
	
	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.DETACH)
	@JoinColumn(name = "studentId")
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
	private SysStudent student;

	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.DETACH)
	@JoinColumn(name = "companyId")
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
	private SysCompany company;

	/**
	 * 实训周期（实际与实训周期子表关联），SysDepartTrainCycle
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "trainCycleId")
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private SysDepartTrainCycle trainCycle;
	
	public String getStatusVal() {
		statusVal= CacheData.getDictValByKey(status, "审批状态");
		if ("".equals(statusVal)) {
			statusVal=status;
		}
		return statusVal;
	}
}
