package com.thesisderik.appthesis;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.thesisderik.appthesis.services.GraphBuilder;
import com.thesisderik.appthesis.services.GraphManagerService;
import com.thesisderik.appthesis.services.IGraphBuilder;
import com.thesisderik.appthesis.services.IGraphManagerService;
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
	
	   @Bean 
	   public IGraphBuilder graphBuilderBean(){
	      return new GraphBuilder();
	   }
	
	   @Bean 
	   public IGraphManagerService graphManagerServiceBean(){
	      return new GraphManagerService();
	   }
	   
	   
   
}