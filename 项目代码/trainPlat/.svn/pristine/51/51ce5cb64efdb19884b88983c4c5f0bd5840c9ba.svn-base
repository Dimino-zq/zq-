package edu.hfu.train.service.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import edu.hfu.train.util.MD5Util;

public class UserPassEncoder implements PasswordEncoder {

	//注册用户时用
	@Override
	public String encode(CharSequence rawPassword) {
		return MD5Util.string2MD5(rawPassword.toString());
	}

	/**
	 * rawPassword 用户输入的密码
	 * encodedPassword  数据库中的密码
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(MD5Util.string2MD5(rawPassword.toString()));
	}

}
