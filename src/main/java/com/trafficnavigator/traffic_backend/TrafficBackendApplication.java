package com.trafficnavigator.traffic_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class TrafficBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrafficBackendApplication.class, args);
	}

}
