package edu.hfu.auth.entity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 员工定义
 */
@Entity //实体类
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"userCode"})})
public class SysStaff  {
	private Integer staffId;//员工ID
	private	String userCode;
	private	String admin;
	private String userPass;
	private String staffName;//员工姓名	
	private Date staffBirthDay;//员工出生日期
	private String staffNational;//员工民族
	private String staffEducation;//员工受教育程度
	private Date entryDate;//员工入职日期
	private String staffPhone;//员工联系方式
	private String staffAddr;//员工家庭住
	private String staffSex;//员工性别	 
	private String staffTitle;//员工职称
	private SysDepart depart;
	private List<SysPost> posts;	
	private String postType; //教学岗 、管理岗
	private String poststr;
	private String postId;
	 
	private SysSchool school;

	private List<AuthGrant> grants;//权限列表
	
	@Id  //主键
	@GeneratedValue(strategy=GenerationType.IDENTITY) //主键生成策略
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	@Column(length = 15,nullable = false)
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	@Column(length = 50,nullable = false)	
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	@Column(length = 50,nullable = false)
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
//	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@JSONField(format="yyyy-MM-dd")
	public Date getStaffBirthDay() {
		return staffBirthDay;
	}
	public void setStaffBirthDay(Date staffBirthDay) {
		this.staffBirthDay = staffBirthDay;
	}
	@Column(length=50)
	public String getStaffNational() {
		return staffNational;
	}
	public void setStaffNational(String staffNational) {
		this.staffNational = staffNational;
	}
	@Column(length=50)
	public String getStaffEducation() {
		return staffEducation;
	}
	public void setStaffEducation(String staffEducation) {
		this.staffEducation = staffEducation;
	}
	
//	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@JSONField(format="yyyy-MM-dd")
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	@Column(length=50,nullable = false)
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	@Column(length=50,nullable = false)
	public String getStaffPhone() {
		return staffPhone;
	}
	public void setStaffPhone(String staffPhone) {
		this.staffPhone = staffPhone;
	}
	@Column(length=200)
	public String getStaffAddr() {
		return staffAddr;
	}
	public void setStaffAddr(String staffAddr) {
		this.staffAddr = staffAddr;
	}
	@Column(length=50)
	public String getStaffSex() {
		return staffSex;
	}
	public void setStaffSex(String staffSex) {
		this.staffSex = staffSex;
	}
	
	@Column(length=50)
	public String getStaffTitle() {
		return staffTitle;
	}
	public void setStaffTitle(String staffTitle) {
		this.staffTitle = staffTitle;
	}
	@Transient
	public String getPoststr() {
		poststr="";
		if (null!=posts&&posts.size()>0) {
			for(SysPost de:posts) {
				poststr+=de.getPostName()+",";
			}
			poststr=poststr.substring(0, poststr.length()-1);
		}
		return poststr;
	}
	public void setPoststr(String poststr) {
		this.poststr = poststr;
	}
	

	@Transient
	public String getPostId() {
		postId="";
		  if (null!=posts&&posts.size()>0) {
		   for(SysPost post:posts) {
		    postId+=post.getPostId()+",";
		   }
		   postId=postId.substring(0, postId.length()-1);
		  }
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	
	@ManyToMany(cascade = {CascadeType.MERGE},targetEntity=SysPost.class)
	@JoinTable( name="SysStaffPost", joinColumns={@JoinColumn(name="staffId",referencedColumnName="staffId")},
			inverseJoinColumns={@JoinColumn(name="postId",referencedColumnName="postId",nullable = false)})
	public List<SysPost> getPosts() {
		return posts;
	}
	public void setPosts(List<SysPost> posts) {
		this.posts = posts;
	}
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.ALL},targetEntity=AuthGrant.class)
	@JoinTable( name="SysStaffGrant", joinColumns={@JoinColumn(name="staffId",referencedColumnName="staffId")},
			inverseJoinColumns={@JoinColumn(name="grantId",referencedColumnName="grantId")})
	public List<AuthGrant> getGrants() {
		return grants;
	}
	
	public List<AuthGrant> getGrants(String sysGrantCode) {
		List<AuthGrant> ls=this.getGrants();
		if (null==ls) {
			return null;
		}else {
			return ls.stream()
					.filter(auth->{
						if (auth.getGrantCode().length()>=2&&auth.getGrantCode().substring(0, 2).equals(sysGrantCode))
							return true;
						else 
							return false;
						})
					.collect(Collectors.toList());
			
		}
	}
	
	public void setGrants(List<AuthGrant> grants) {
		this.grants = grants;
	}
	@ManyToOne(cascade = {CascadeType.REFRESH }, optional = true,fetch=FetchType.LAZY)    
	@JoinColumn(name="departId",nullable = false)//这里设置JoinColum设置了外键的名字，并且orderItem是关系维护端
	public SysDepart getDepart() {
		return depart;
	}
	public void setDepart(SysDepart depart) {
		this.depart = depart;
	}
	
	
	
	@ManyToOne(cascade = {CascadeType.REFRESH }, optional = true,fetch=FetchType.LAZY)    
	@JoinColumn(name="schoolId")//这里设置JoinColum设置了外键的名字，并且orderItem是关系维护端
	public SysSchool getSchool() {
		return school;
	}

	public void setSchool(SysSchool school) {
		this.school = school;
	}
	@Column(length=10,nullable = true)
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
	
}
