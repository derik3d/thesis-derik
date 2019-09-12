package com.thesisderik.appthesis;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import idscrawler.IdCrawlerService;

@Configuration
public class ConfigurationSpring {
	
	   @Bean 
	   public IdCrawlerService beanCrawler(){
	      return new IdCrawlerService();
	   }
	   

		
	   @Bean 
	   public RestTemplate restTemplateBean(){
	      return new RestTemplate();
	   }
	   
	   
   
}