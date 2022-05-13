package edu.hfu.train.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

import edu.hfu.train.util.CacheData;
import edu.hfu.train.util.daoru.ExcelTitle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "studentNo" }) })
public class SysStudent extends SysBaseTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;// 学生编号
	@Column(length = 20, nullable = false)
	@ExcelTitle(title = "学号")
	private String studentNo;// 学号
	@Column(length = 32)
	private String password;// 密码
	@Column(length = 10, nullable = false)
	@ExcelTitle(title = "姓名")
	private String studentName;// 姓名
	@Column(length = 3)
	@ExcelTitle(title = "性别")
	private String studentSex;// 性别
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ExcelTitle(title = "生日")
	private Date birthDay;// 生日
	@Column(length = 20, nullable = false)
	@ExcelTitle(title = "系部")
	private String depart;// 系部
	@Column(length = 15, nullable = false)
	@ExcelTitle(title = "专业")
	private String major;// 专业
	@Column(length = 16)
	@ExcelTitle(title = "班级")
	private String classNumber;// 班级
	@Column(length = 10, nullable = false)
	@ExcelTitle(title = "年级")
	private String studentGrade;// 年级
	@Column(length = 10)
	@ExcelTitle(title = "政治面貌")
	private String politics;// 政治面貌
	@Column(length = 10)
	@ExcelTitle(title = "民族")
	private String nation;// 民族
	@Column(length = 50)
	@ExcelTitle(title = "籍贯")
	private String nativeplace;// 籍贯
	@Column(length = 20)
	@ExcelTitle(title = "身份证")
	private String identity;// 身份证
	@Column(length = 10, nullable = false)
	private String trainStatus;// 实习状态，0未完成实习，1已完成实习

	@Transient
	private String statusVal; // 临时状态，用于转换

	public String getStatusVal() {
		statusVal = CacheData.getDictValByKey(trainStatus, "实习状态");
		if ("".equals(statusVal)) {
			statusVal = trainStatus;
		}
		return statusVal;
	}
}
