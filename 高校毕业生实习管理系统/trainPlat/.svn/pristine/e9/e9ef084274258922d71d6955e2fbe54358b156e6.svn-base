package edu.hfu.train.bean;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLevel extends SysBaseTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
	private Integer lvlId;
	@Column(length = 30, nullable = false)
	private String lvlName;
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longtext")
	private String lvlPicPath;//等级图片
	@Column(nullable = false)
	private Float minScore; //最小分数
}
