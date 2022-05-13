package edu.hfu.auth.entity;


import lombok.Data;

@Data
public class Authentication {
	private String userCode;
	private String accessCode;//访问授权码
	private Long effectTime;//失效时间
}
