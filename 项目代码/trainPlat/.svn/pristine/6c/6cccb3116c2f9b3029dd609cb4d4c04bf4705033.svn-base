package edu.hfu.train.bean;

/*实习记录表*/
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class StudentRecord extends SysBaseTable {
	
	private static final long serialVersionUID = 820945043610536534L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	private Integer recordId; // 记录编号
	@Column(length = 80)
	private String comPost; // 实习岗位
	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startDate; // 开始日期
	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate; // 结束日期
	@Column(length = 100)
	private String content; // 内容要求
	@Column(length = 20)
	private String compleStatus; // 完成情况
	@Column(length = 200)
	private String other;  //备注
	@Column(length = 200)
	private String guidStatus; // 指导情况
	@Column(length = 10, nullable = false)
	private String teacherNo;       //教师工号

	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.DETACH)
	@JoinColumn(name = "studentid")
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
	private SysStudent student;
	
	/**
	 * CascadeType.PERSIST,级联增加
	 */
	@OneToOne(fetch=FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE})
	@JoinColumn(name = "workLogId")
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
	private StudentWorkRec workRecord;
}
