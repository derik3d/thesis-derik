package com.thesisderik.appthesis;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.thesisderik.appthesis.services.INamesCrawlerService;
import com.thesisderik.appthesis.services.INamesIdentifiersService;
import com.thesisderik.appthesis.services.NameCrawlerService;
import com.thesisderik.appthesis.services.NamesIdentifiersService;

@Configuration
public class ConfigurationSpring {

	
	   @Bean 
	   public RestTemplate restTemplateBean(){
	      return new RestTemplate();
	   }
	   
	   
   
}