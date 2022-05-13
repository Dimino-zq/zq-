package edu.hfu.train.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import edu.hfu.train.util.daoru.ExcelTitle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Data
@ToString(exclude = { "plans" })
@EqualsAndHashCode(callSuper = false)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "comName" }) })
@JsonIgnoreProperties(value = "plans")
public class SysCompany extends SysBaseTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	private Integer companyId;
	@Column(length = 10)
	@ExcelTitle(title = "审批状态")
	private String checkstate;
	@Column(length = 50)
	@ExcelTitle(title = "数据来源")
	private String datasource;// 数据来源
	@Column(length = 50)
	@ExcelTitle(title = "所属行业")
	private String industry;
	@Column(length = 80)
	@ExcelTitle(title = "实习单位名称")
	private String comName;
	@Column(length = 50)
	@ExcelTitle(title = "实习单位地址")
	private String comAddress;
	@Column(length = 5)
	@ExcelTitle(title = "是否已签协议")
	private String sign;
	// @JSONField(format = "yyyy-MM-dd") */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	// @Column(length = 50)
	@ExcelTitle(title = "签协议时间")
	private Date signTime;
	@Column(length = 50)
	@ExcelTitle(title = "有效期")
	private String effeDate;// 有效期
	@Column(length = 8)
	@ExcelTitle(title = "企业联系人")
	private String comcontacts;
	@Column(length = 10)
	@ExcelTitle(title = "所任职务")
	private String position;
	@Column(length = 20)
	@ExcelTitle(title = "联系方式")
	private String phone;
	@Column(length = 8)
	@ExcelTitle(title = "校内联系人")
	private String schContact;
	@Column(length = 20)
	@ExcelTitle(title = "校内联系方式")
	private String schContactphone;
	@Column(length = 200)

	private String protocolPath;// 协议路劲

	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext")
	private String logoPath;//

	private Float addupScore;// 累计评分
	@Column(length = 20)
	private String currentLvl;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private List<SysRecruitPlan> plans;// 招聘计划

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "lvlId")
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private SysLevel sysLevel;

}
