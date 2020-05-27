package com.project.ecommerce;

import com.project.ecommerce.auditing.AuditorAwareImpl;
import com.project.ecommerce.entity.Product;
import com.project.ecommerce.rabbitmq.RabbitMQConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@SpringBootApplication


// For asynchronous email
@EnableAsync


// To enable JPA Auditing
@EnableJpaAuditing(auditorAwareRef = "auditorAware")


// To enable the scheduler for your application.
@EnableScheduling

//To enable caching
@EnableCaching


public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}



	// Used for Auditing

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}



	// Used for Internationalization

	@Bean
	public LocaleResolver localeResolver(){
		AcceptHeaderLocaleResolver localeResolver=new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

	public ResourceBundleMessageSource bundleMessageSource(){
		ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

}