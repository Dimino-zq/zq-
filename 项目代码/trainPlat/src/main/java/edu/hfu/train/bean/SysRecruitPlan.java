package edu.hfu.train.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 招聘计划
 * 
 * @author iceheartboy
 *
 */
@SuppressWarnings("serial")
@Entity
@Data
@ToString(exclude = {"company"})
@EqualsAndHashCode(callSuper = false)
public class SysRecruitPlan extends SysBaseTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	private Integer planId;
	// 招聘摘要
	@Column(length = 1000)
	private String planDesc;
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "companyId") // 这里设置JoinColum设置了外键的名字，并且orderItem是关系维护端
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private SysCompany company;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitPlan",cascade = { CascadeType.ALL })
	private List<SysPlanDetail> details;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	@JsonIgnore
	private SysTrainCycle cycle;

}
