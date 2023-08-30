package com.ankita.codeonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ankita.codeonline.*"})
public class CodeOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeOnlineApplication.class, args);
	}

}
