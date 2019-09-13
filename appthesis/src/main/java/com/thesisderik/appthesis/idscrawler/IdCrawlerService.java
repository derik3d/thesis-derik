package com.thesisderik.appthesis.idscrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.thesisderik.appthesis.idscrawler.entities.ResponseBigg;
import com.thesisderik.appthesis.idscrawler.entities.ResponseData;
import com.thesisderik.appthesis.idscrawler.entities.ResponseKegg;
import com.thesisderik.appthesis.idscrawler.entities.ResponseSMILESPubchem;

@Service
public class IdCrawlerService {

	private static final String pubchem2SmilesUrl = "https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/%s/property/CanonicalSmiles/json";
	private static final String kegg2PubchemUrl = "http://rest.kegg.jp/conv/pubchem/%s";
	private static final String bigg2KeggUrl = "http://bigg.ucsd.edu/api/v2/universal/metabolites/%s";
	
	@Autowired
	private RestTemplate restTemplate;

	
	
	public ResponseData fromPubchemIdGetSmiles(String id) {
		

		try {
			
			ResponseSMILESPubchem resp = restTemplate.getForObject(String.format(pubchem2SmilesUrl, id),ResponseSMILESPubchem.class);		
			return ResponseData.responseDataBuilder(resp);
			
		} catch (RestClientException e) {
			System.out.println("400 from pubchem");
			return null;
		}
		
				
	}
	
	
	public ResponseData fromBiggIdGetKegg(String id) {
		

		try {
			
			ResponseBigg resp = restTemplate.getForObject(String.format(bigg2KeggUrl, id),ResponseBigg.class);		
			return ResponseData.responseDataBuilder(resp);
			
		} catch (RestClientException e) {
			System.out.println("400 from bigg");
			return null;
		}
		
				
	}

	public ResponseData fromKeggIdGetPubchem(String id) {
		
		try {
			ResponseEntity<String> resp = restTemplate.getForEntity( String.format(kegg2PubchemUrl, id), String.class);		

			if(resp.getStatusCode() == HttpStatus.OK) {
				
				try {
					ResponseKegg res = new ResponseKegg();
					
					String resString = resp.getBody();

					if(resString.length() < 1)
						throw new Exception("zero length data");
					
					String cpd;
					String pubchem;
					if(resString.contains("\t") && resString.contains("\n") && resString.contains("pubchem") && resString.contains("cpd")) {

						cpd = resString.substring(0,resString.indexOf("\t")).replace("cpd:","");
						pubchem = resString.substring(resString.indexOf("\t")+1, resString.indexOf("\n")).replace("pubchem:", "");
						
						if(cpd.length() < 1 || pubchem.length() < 1)
							throw new Exception("zero length data");
						
						res.setCpd(cpd);
						res.setPubchem(pubchem);
						return ResponseData.responseDataBuilder(res);
						
					}
				
				}catch(Exception ex) {
					System.out.println("not reading well kegg response");
					return null;
				}
			}
		} catch (RestClientException e) {
			System.out.println("400 from kegg");
			return null;
		}
		
		return null;
		
	}
	
}
