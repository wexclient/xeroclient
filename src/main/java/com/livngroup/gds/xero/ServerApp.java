package com.livngroup.gds.xero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ImportResource(locations = "classpath:xero-client-rest-app-context.xml")
@ComponentScan(basePackages = "com.livngroup.gds.xero")
//@EnableScheduling
@PropertySource("classpath:application-properties.xml")
public class ServerApp {

	public static ApplicationContext APPLICATION_CONTEXT;
	
	final private static Logger logger = LoggerFactory.getLogger(ServerApp.class);

    public static void main(String[] args) {
    	System.setProperty("banner.location", "classpath:xero-client-banner.txt");
    	APPLICATION_CONTEXT = SpringApplication.run(ServerApp.class, args);
        logger.info("application started [" + System.getProperty("spring.profiles.active") + "]...");
    }

}
