package br.com.inter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@ComponentScan({"br.com"})
@EntityScan(basePackages = { "br.com.model" })
@EnableJpaRepositories("br.com.repository")
public class InterApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterApplication.class, args);
	}

}
