package edu.hfu.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;


/**
 * 权限 定义表
 * @author iceheartboy
 *
 */
@Entity //实体类
public class AuthGrant{
	private Integer grantId;//权限id
	private String grantCode;//权限编号
	private String grantName;//权限名称
	private String parentGrantCode;//父级权限编号
	private String belongSys;//所属系统
	private Integer grantLvl;//权限级别
	private String grantCode1;//权限编号1
	private String grantCode2;//权限编号2
	private String grantCode3;//权限编号3
	private String grantCode4;//权限编号4
	private Boolean isDefault;  //是否默认
	
	@Id  //主键
	@GeneratedValue(strategy=GenerationType.IDENTITY) //主键生成策略
	public Integer getGrantId() {
		return grantId;
	}
	public void setGrantId(Integer grantId) {
		this.grantId = grantId;
	}
	@Column(length = 50,nullable = false,unique = true)
	public String getGrantCode() {
		return grantCode;
	}
	public void setGrantCode(String grantCode) {
		this.grantCode = grantCode;
	}
	
	@Column(length = 50,nullable=false)
	public String getGrantName() {
		return grantName;
	}
	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}
	public String getParentGrantCode() {
		return parentGrantCode;
	}
	public void setParentGrantCode(String parentGrantCode) {
		this.parentGrantCode = parentGrantCode;
	}
	@Column(length = 50,nullable=false)
	public String getBelongSys() {
		return belongSys;
	}
	public void setBelongSys(String belongSys) {
		this.belongSys = belongSys;
	}
	public Integer getGrantLvl() {
		return grantLvl;
	}
	public void setGrantLvl(Integer grantLvl) {
		this.grantLvl = grantLvl;
	}
	
	@Transient
	public String getGrantCode1() {
		return grantCode1;
	}
	public void setGrantCode1(String grantCode1) {
		this.grantCode1 = grantCode1;
	}
	@Transient
	public String getGrantCode2() {
		return grantCode2;
	}
	public void setGrantCode2(String grantCode2) {
		this.grantCode2 = grantCode2;
	}
	@Transient
	public String getGrantCode3() {
		return grantCode3;
	}
	public void setGrantCode3(String grantCode3) {
		this.grantCode3 = grantCode3;
	}
	@Transient
	public String getGrantCode4() {
		return grantCode4;
	}
	public void setGrantCode4(String grantCode4) {
		this.grantCode4 = grantCode4;
	}
	
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	
	
}
