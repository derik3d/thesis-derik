package com.thesisderik.appthesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IdCrawlerService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String fromKeggIdGetPubchem() {
		
		
		
		return "ERROR";
		
	}

}
