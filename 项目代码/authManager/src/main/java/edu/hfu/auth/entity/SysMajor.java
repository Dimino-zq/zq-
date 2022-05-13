package edu.hfu.auth.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
	 * 专业定义
	 * @author Shaocc
	 *
	 */
@Entity //实体类
public class SysMajor {

		private Integer majorId;//部门ID
		private String majorName;//部门地址
		private String majorExplain;//部门说明
		private SysDepart depart;
		
		@Id  //主键
		@GeneratedValue(strategy=GenerationType.IDENTITY) //主键生成策略
		public Integer getMajorId() {
			return majorId;
		}
		public void setMajorId(Integer majorId) {
			this.majorId = majorId;
		}
		//专业名
		@Column(length=200,nullable=false)
		public String getMajorName() {
			return majorName;
		}
		public void setMajorName(String majorName) {
			this.majorName = majorName;
		}
		//专业介绍
		@Column(length=250,nullable=true)
		public String getMajorExplain() {
			return majorExplain;
		}
		public void setMajorExplain(String majorExplain) {
			this.majorExplain = majorExplain;
		}
		
		@ManyToOne(cascade = {CascadeType.REFRESH }, optional = true,fetch=FetchType.LAZY)    
		@JoinColumn(name="departId",nullable = false)//这里设置JoinColum设置了外键的名字，并且orderItem是关系维护端
		public SysDepart getDepart() {
			return depart;
		}
		public void setDepart(SysDepart depart) {
			this.depart = depart;
		}
}
