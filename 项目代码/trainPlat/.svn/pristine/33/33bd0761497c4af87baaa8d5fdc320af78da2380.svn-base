package edu.hfu.train.bean;

/*实习工作记录表*/
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
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class StudentWorkRec extends SysBaseTable {
	private static final long serialVersionUID = 1266340927600951871L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	private Integer workLogId; // 工作记录编号
	@Column(length = 20, nullable = false)
	private String weeks; // 周数
	@Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate; // 记录开始日期
	@Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate; // 记录结束日期
    @Column(length = 100)
    private String workContents; // 工作主要内容
    @Column(length = 500)
    private String maingains; // 主要收获
    @Column(length = 50)
    private String tutorComate; // 导师沟通
    @Column(length = 50)
    private String tutorComatemode; // 导师沟通方式
    @Column(length = 200)
    private String tutorGuidcontent; // 导师沟通内容
    @Column(length = 200)
    private String selorComate; // 辅导员沟通
    @Column(length = 50)
    private String selorComatemode; // 辅导员沟通方式
    @Column(length = 200)
    private String selorGuidcontent; // 辅导员指导内容
    @Column(length = 200)
    private String notes; // 其他（备注）
    @Column(length = 10, nullable = false)
    private String teacherNo; //教师工号
    private Integer fullHours; //出勤
    private Integer lateTime; //迟到
    private Integer leaveEarly; //早退
    private Integer neglect; //旷工
    private Integer sickLeave; //病假
    private Integer thingsLeave; //事假

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "studentId")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private SysStudent student;

}