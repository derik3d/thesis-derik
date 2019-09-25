package com.thesisderik.appthesis;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.thesisderik.appthesis.services.INamesCrawlerService;
import com.thesisderik.appthesis.services.NameCrawlerService;

@Configuration
public class ConfigurationSpring {
	
	   @Bean 
	   public NameCrawlerService beanCrawler(){
	      return new NameCrawlerService();
	   }
	   

		
	   @Bean 
	   public RestTemplate restTemplateBean(){
	      return new RestTemplate();
	   }
	   
	   
   
}