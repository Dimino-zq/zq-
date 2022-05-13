package edu.hfu.train.bean;
/**
 * 部门定义
 * @author iceheartboy
 *
 */
@SuppressWarnings("serial")
public class SysDepart implements java.io.Serializable{
	private Integer departId;//部门ID
	private String departName;//部门名称
	public Integer getDepartId() {
		return departId;
	}
	public void setDepartId(Integer departId) {
		this.departId = departId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
}