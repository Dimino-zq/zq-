package edu.hfu.train.bean;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class StudentInterReport extends SysBaseTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5408826011520572107L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reportId; // 实习报告Id
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext")
	private String comProfile; // 实习单位简介
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext")
	private String comworkfile; // 实习单位工作简介
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext")
	private String conclusion; // 实习总结
	@Column(length = 10, nullable = false)
	private String teacherNo; // 教师工号
	@Column(length = 80, nullable = false)
	private String status; // 评阅状态
	@Column(length = 4)
	private Integer grate; // 总成绩

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "applyId")
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private StudentApplication application;

}
