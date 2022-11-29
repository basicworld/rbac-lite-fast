package com.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动程序
 * 
 * @author wlfei
 */
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
	    // SpringApplication.run()的底层其实就是new了一个SpringApplication的对象，并执行run()方法。
		SpringApplication.run(Application.class, args);
		System.out.println("--------------------");
		System.out.println("-- start success! --");
		System.out.println("--------------------");
	}
}
