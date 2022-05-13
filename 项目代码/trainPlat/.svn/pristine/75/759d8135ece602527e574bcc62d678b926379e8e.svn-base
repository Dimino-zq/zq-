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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class SysDepartTrainCycle implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2688448270982756489L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	private Integer sysDepartTrainCycleId; // 系专业周期编号
	@Column(length = 10)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date planStartTime; // 实习计划开始时间
	@Column(length = 10)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date planEndTime; // 实习计划结束时间
	@Column(length = 15)
	private String departName;// 部门名称
	@Column(length = 15)
	private String majorName;// 部门名称
	@Column(length = 10)
	private Integer weeks; // 持续周期

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "sysTrainCycleId")
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private SysTrainCycle sysTrainCycle;

}
