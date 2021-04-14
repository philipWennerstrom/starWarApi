package br.com.b2sky.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "br.com.b2sky.infra" })
@EnableAutoConfiguration
@EnableMongoRepositories
@EnableReactiveMongoRepositories
public class StarWarApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarWarApplication.class, args);
	}
}