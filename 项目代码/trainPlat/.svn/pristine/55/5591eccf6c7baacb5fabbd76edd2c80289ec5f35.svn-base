package edu.hfu.train.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@Data
@ToString(exclude = {"recruitPlan"})
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = "recruitPlan")
public class SysPlanDetail extends SysBaseTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	private Integer detailId;
	private String stationName;// 岗位名称
	private String majorName;// 所需专业
	private Integer planStu; // 计划所需学生数
	@Transient
	private String planStuString; // 计划所需学生数
	private Integer actualStu;// 已招聘人数
	@Transient
	private String actualStuString;// 已招聘人数
	@ManyToOne(cascade = { CascadeType.REFRESH }, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "planId") // 这里设置JoinColum设置了外键的名字，并且orderItem是关系维护端
	private SysRecruitPlan recruitPlan;
	
	
}
