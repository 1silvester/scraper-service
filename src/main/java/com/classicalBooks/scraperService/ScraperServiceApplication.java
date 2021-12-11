package com.classicalBooks.scraperService;

import com.classicalBooks.scraperService.database.AuthorRepository;
import com.classicalBooks.scraperService.models.Authors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScraperServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScraperServiceApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(AuthorRepository authorRepository)
//	{
//		return args -> {
//			Authors jim = new Authors("jim author", "browse/jim.html");
//			authorRepository.save(jim);
//
//		};
//	}
}
