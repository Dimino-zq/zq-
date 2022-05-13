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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class TeacherReportAppraisal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer appraisalId;// 总结报告评阅id
	@Column(length = 80)
	private String reviewercomments; // 评阅人评语
	@Column(length = 4)
	private Integer teachergrate; // 老师成绩
	@Column(length = 4)
	private Integer companygrate; // 公司成绩
	@Column(length = 4)
	private Integer finalgrate; // 总成绩
	@Column(length = 10)
	private String signature; // 评阅人签名
	@Column(length = 10)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date signaturedate; // 申请日期

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "studentInterReportId")
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
	private StudentInterReport studentInterReport;
}
