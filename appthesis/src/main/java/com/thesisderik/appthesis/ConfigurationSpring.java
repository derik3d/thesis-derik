package com.thesisderik.appthesis;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import com.thesisderik.appthesis.interfaces.IGraphBuilder;
import com.thesisderik.appthesis.interfaces.IGraphManagerService;
import com.thesisderik.appthesis.interfaces.INamesCrawlerService;
import com.thesisderik.appthesis.interfaces.INamesIdentifiersService;
import com.thesisderik.appthesis.services.GraphBuilder;
import com.thesisderik.appthesis.services.GraphManagerService;
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
	   

	    @Bean
	    public TaskExecutor threadPoolTaskExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(4);
	        executor.setMaxPoolSize(4);
	        executor.setThreadNamePrefix("default_task_executor_thread");
	        executor.initialize();
	        return executor;
	    }
  
}