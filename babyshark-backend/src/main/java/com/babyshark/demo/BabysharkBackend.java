package com.babyshark.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BabysharkBackend {


    private static Logger logger = LoggerFactory.getLogger(BabysharkBackend.class);

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BabysharkBackend.class, args);
        logger.info("**********************************");
        logger.info("*                                *");
        logger.info("*   Babyshark at your service!   *");
        logger.info("*                                *");
        logger.info("**********************************");
	}
}
