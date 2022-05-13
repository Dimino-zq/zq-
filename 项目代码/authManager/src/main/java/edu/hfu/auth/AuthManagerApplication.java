package edu.hfu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

@SpringBootApplication(scanBasePackages = {"edu.hfu.auth","com.hanb.filterJson"})
public class AuthManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthManagerApplication.class, args);
	}
	@Bean
	public OpenEntityManagerInViewFilter openEntityManagerInViewFilter(){
	   return new OpenEntityManagerInViewFilter();
	}
}
