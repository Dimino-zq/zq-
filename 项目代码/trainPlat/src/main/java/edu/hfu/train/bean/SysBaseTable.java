package edu.hfu.train.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
@SuppressWarnings("serial")
@Data
@MappedSuperclass
public abstract class SysBaseTable implements java.io.Serializable{
	@Column(length = 50)
	protected String createUser;
	@Column(length = 50)
	protected String updUser;//
	@Column
	protected Date createDate;//创建时间
	@Column(length = 100)
	protected Date updDate;//修改时间
}
