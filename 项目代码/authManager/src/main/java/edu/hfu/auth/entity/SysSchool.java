package edu.hfu.auth.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import org.hibernate.annotations.Proxy;
import java.io.Serializable;

/**
 * 学校定义
 */
@Proxy(lazy=false)
@Entity //实体类
public class SysSchool {
	private Integer schoolId;//学校ID
	private String schoolEmail;//学校邮箱
	private String schoolExplain;//学校简介
	private Date schoolDate ;//建校日期
	private String schoolLead;//学校校长
	private String schoolType;//学校类型
	private String schoolName;//学校名称
	private String schoolAddr;//学校地址 ַ
	private List<SysDepart> depart;
	private List<SysStaff> staff;
	@Id  //主键
	@GeneratedValue(strategy=GenerationType.IDENTITY) //主键生成策略
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	
	@Column(length=100)
	public String getSchoolEmail() {
		return schoolEmail;
	}
	public void setSchoolEmail(String schoolEmail) {
		this.schoolEmail = schoolEmail;
	}
	@Column(length=200)
	public String getSchoolExplain() {
		return schoolExplain;
	}
	public void setSchoolExplain(String schoolExplain) {
		this.schoolExplain = schoolExplain;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getSchoolDate() {
		return schoolDate;
	}
	public void setSchoolDate(Date schoolDate) {
		this.schoolDate = schoolDate;
	}
	@Column(length=100)
	public String getSchoolLead() {
		return schoolLead;
	}
	public void setSchoolLead(String schoolLead) {
		this.schoolLead = schoolLead;
	}
	@Column(length=100)
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	
	@Column(length=100)
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	@Column(length=200)
	public String getSchoolAddr() {
		return schoolAddr;
	}
	public void setSchoolAddr(String schoolAddr) {
		this.schoolAddr = schoolAddr;
	}
	
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="school",fetch=FetchType.LAZY) 
	//这里配置关系，并且确定关系维护端和被维护端。mappBy表示关系被维护端，只有关系端有权去更新外键。这里还有注意OneToMany默认的加载方式是赖加载。当看到设置关系中最后一个单词是Many，那么该加载默认为懒加载
	@JsonIgnore
	public List<SysDepart> getDepart() {
		return depart;
	}
	public void setDepart(List<SysDepart> depart) {
		this.depart = depart;
	}
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="school",fetch=FetchType.LAZY) 
	@JsonIgnore
	public List<SysStaff> getStaff() {
		return staff;
	}
	public void setStaff(List<SysStaff> staff) {
		this.staff = staff;
	}
}
