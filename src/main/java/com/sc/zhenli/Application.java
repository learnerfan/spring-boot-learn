package com.sc.zhenli;

import com.sc.zhenli.conf.MybatisInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan({"com.sc.zhenli.common.dao","com.sc.zhenli.dao"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	public MybatisInterceptor mybatisInterceptor() {
		return new MybatisInterceptor();
	}
}
