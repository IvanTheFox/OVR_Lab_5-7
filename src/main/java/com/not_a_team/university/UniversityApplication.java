package com.not_a_team.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Основной класс приложения (инициализатор)
 */
@SpringBootApplication
public class UniversityApplication extends SpringBootServletInitializer{
	/**
	 * Основной метод
	 * @param args - параметры запуска
	 */
	public static void main(String[] args) {
		SpringApplication.run(UniversityApplication.class, args);
	}

	/**
	 * Метод для упаковки веб-приложения
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UniversityApplication.class);
	}
}