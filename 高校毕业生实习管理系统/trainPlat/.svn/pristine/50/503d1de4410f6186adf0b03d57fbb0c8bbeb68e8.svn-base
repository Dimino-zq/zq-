package edu.hfu.train.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class SysTrainCycle extends SysBaseTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3863513146148129237L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	private Integer cycleId; // 周期编号
	@Column(length = 10)
	private String startschoolyear; // 开始学年度
	@Column(length = 10)
	private String endschoolyear; // 结束学年度
	@Column(length = 10)
	private String semester; // 学期
	@Column(length = 10)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date planstartTime; // 实习计划开始时间
	@Column(length = 10)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date planendTime; // 实习计划结束时间
	@Column(length = 10)
	private Integer conWeeks; // 持续周期
	@Column(length = 10)
	private String status; // 状态
}
