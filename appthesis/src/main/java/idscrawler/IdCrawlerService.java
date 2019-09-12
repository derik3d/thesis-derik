package idscrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import idscrawler.entities.ResponseKegg;

@Service
public class IdCrawlerService {
	
	private static final String kegg2PubchemUrl = "http://rest.kegg.jp/conv/pubchem/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public ResponseKegg fromKeggIdGetPubchem(String id) {
		
		ResponseEntity resp = restTemplate.getForEntity( kegg2PubchemUrl + id, ResponseKegg.class);
		
		return resp.getStatusCode() == HttpStatus.OK ? (ResponseKegg)resp.getBody() : null;
		
	}
	
	public String fromBiggIdGetKegg() {
		
		
		
		return "ERROR";
		
	}

}
