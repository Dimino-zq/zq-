package edu.hfu.auth.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 职务
 */
@Entity // 实体类
public class SysPost {
	private Integer postId;// 职务ID
	private String postName;// 职务名称
	private String postExplain;// 职务简介
	private SysPost parentPost;
//	private List<SysPost> childPost;
	private Integer postLvl;// 职务级别
	private List<AuthGrant> grants;
	private String childGrantCodes;
	

	@Id // 主键
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	@Column(length = 200,nullable=false)
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	@Column(length = 300,nullable=false)
	public String getPostExplain() {
		return postExplain;
	}

	public void setPostExplain(String postExplain) {
		this.postExplain = postExplain;
	}

	@OneToOne
	@JoinColumn(name = "parentPostId")  
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="postId")
	public SysPost getParentPost() {
		return parentPost;
	}

	public void setParentPost(SysPost parentPost) {
		this.parentPost = parentPost;
	}
	
	
//	@OneToMany(mappedBy = "parentPost", targetEntity = SysPost.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})  
//	//@JsonIgnore
//	public List<SysPost> getChildPost() {
//		return childPost;
//	}
//	public void setChildPost(List<SysPost> childPost) {
//		this.childPost = childPost;
//	}
	
	@Column(length = 10,nullable=false)
	public Integer getPostLvl() {
		return postLvl;
	}

	public void setPostLvl(Integer postLvl) {
		this.postLvl = postLvl;
	}
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},targetEntity=AuthGrant.class)
	@JoinTable( name="SysPostGrant", joinColumns={@JoinColumn(name="postId",referencedColumnName="postId")},
			inverseJoinColumns={@JoinColumn(name="grantId",referencedColumnName="grantId")})
	public List<AuthGrant> getGrants() {
		return grants;
	}
	public void setGrants(List<AuthGrant> grants) {
		this.grants = grants;
	}
	
	
	@Transient
	public String getChildGrantCodes() {
		childGrantCodes="";
		if (null!=grants&&grants.size()>0) {
			for(AuthGrant au:grants) {
				childGrantCodes+=au.getGrantCode()+",";
			}
			childGrantCodes=childGrantCodes.substring(0, childGrantCodes.length()-1);
		}
		return childGrantCodes;
	}
	public void setChildGrantCodes(String childGrantCodes) {
		this.childGrantCodes = childGrantCodes;
	}
	
	
}


