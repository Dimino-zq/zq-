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
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.hfu.train.util.CacheData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实习变更申请表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class StudentSecApplication extends SysBaseTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8873010716682173748L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer secApplyId; // 变更申请Id
	@Column(length = 80)
	private String oldComName; // 原实习单位
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date oldComStartDate; // 原实习开始日期
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date oldComEndDate; // 原实习结束日期
	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date newComStartDate; // 新实习开始日期
	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date newComEndDate; // 新实习结束日期
	@Column(length = 80)
	private String newStation; // 新实习岗位
	@Column(length = 100)
	private String newContent; // 新实习内容
	@Column(length = 100)
	private String newAdress; // 新实习住宿地址
	@Column(length = 200)
	private String reason; // 变更理由
	// @Column(length = 200)
	// private String oldComView; //原单位意见
	@Column(length = 8)
	private String teacherName; // 指导教师姓名
	@Column(length = 200)
	private String teacherView; // 指导教师意见
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date teacherViewDate; // 导师意见时间
	@Column(length = 200)
	private String adviserName; // 辅导员姓名
	@Column(length = 200)
	private String adviserView; // 辅导员意见
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date adviserViewDate; // 辅导员意见时间
	@Column(length = 8)
	private String deptName; // 系部负责人姓名
	@Column(length = 200)
	private String deptView; // 系部意见
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date deptViewDate; // 系部审批时间
	@Column(length = 200, nullable = false)
	private String status; // 状态
	@Transient
	private String statusVal; // 临时状态，用于转换
	@Column(length = 200)
	private String filePath; // 附件地址

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "applyId")
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private StudentApplication application;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "companyId")
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private SysCompany newCompany;

	public String getStatusVal() {
		statusVal = CacheData.getDictValByKey(status, "审批状态");
		if ("".equals(statusVal)) {
			statusVal = status;
		}
		return statusVal;
	}

}
