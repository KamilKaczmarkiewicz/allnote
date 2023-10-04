package com.allnote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AllnoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllnoteApplication.class, args);
	}

}
