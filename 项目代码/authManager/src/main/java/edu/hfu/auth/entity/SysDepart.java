package edu.hfu.auth.entity;

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

/**
 * 閮ㄩ棬瀹氫箟
 * @author iceheartboy
 *
 */
@Entity 
public class SysDepart {
	private Integer departId;//部门ID
	private String departAddr;//部门地址
	private String departPhone;//部门电话
	private Integer departLevel;//部门级别
	private String departName;//部门名称
	private String departExplain;//部门说明
	private String departType;//部门类型
	private SysSchool school;
	private SysDepart parentDepart;
	private List<SysStaff> staff;
	private String departCharge;//负责人
	private List<SysMajor> major;
	
	@Id  //涓婚敭
	@GeneratedValue(strategy=GenerationType.IDENTITY) //涓婚敭鐢熸垚绛栫暐
	public Integer getDepartId() {
		return departId;
	}
	public void setDepartId(Integer departId) {
		this.departId = departId;
	}
	@Column(length=200,nullable=false)
	public String getDepartAddr() {
		return departAddr;
	}
	public void setDepartAddr(String departAddr) {
		this.departAddr = departAddr;
	}
	@Column(length=100,nullable=false)
	public String getDepartPhone() {
		return departPhone;
	}
	public void setDepartPhone(String departPhone) {
		this.departPhone = departPhone;
	}
	@Column(nullable=false)
	public Integer getDepartLevel() {
		return departLevel;
	}
	public void setDepartLevel(Integer departLevel) {
		this.departLevel = departLevel;
	}
	@Column(length=200,nullable=false)
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	@Column(length=250,nullable=false)
	public String getDepartExplain() {
		return departExplain;
	}
	public void setDepartExplain(String departExplain) {
		this.departExplain = departExplain;
	}
	@ManyToOne(cascade = {CascadeType.REFRESH }, optional = true,fetch=FetchType.LAZY)    
	@JoinColumn(name="schoolId")//杩欓噷璁剧疆JoinColum璁剧疆浜嗗閿殑鍚嶅瓧锛屽苟涓攐rderItem鏄叧绯荤淮鎶ょ
	public SysSchool getSchool() {
		return school;
	}
	public void setSchool(SysSchool school) {
		this.school = school;
	}
	
	@OneToOne
	public SysDepart getParentDepart() {
		return parentDepart;
	}
	public void setParentDepart(SysDepart parentDepart) {
		this.parentDepart = parentDepart;
	}
	
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="depart",fetch=FetchType.LAZY) 
	public List<SysStaff> getStaff() {
		return staff;
	}
	public void setStaff(List<SysStaff> staff) {
		this.staff = staff;
	}
	@Column(length=200)
	public String getDepartType() {
		return departType;
	}
	public void setDepartType(String departType) {
		this.departType = departType;
	}
	@Column(length=50)
	public String getDepartCharge() {
		return departCharge;
	}
	public void setDepartCharge(String departCharge) {
		this.departCharge = departCharge;
	}
	
	//一对多关联专业
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="depart",fetch=FetchType.LAZY) 
	public List<SysMajor> getMajor() {
		return major;
	}
	public void setMajor(List<SysMajor> major) {
		this.major = major;
	}
	
	
}