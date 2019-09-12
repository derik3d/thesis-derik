package idscrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import idscrawler.entities.ResponseBigg;
import idscrawler.entities.ResponseData;
import idscrawler.entities.ResponseKegg;

@Service
public class IdCrawlerService {

	private static final String kegg2PubchemUrl = "http://rest.kegg.jp/conv/pubchem/";
	private static final String bigg2KeggUrl = "http://bigg.ucsd.edu/api/v2/universal/metabolites/";
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	public ResponseData fromBiggIdGetKegg(String id) {
		

		try {
			
			ResponseBigg resp = restTemplate.getForObject(bigg2KeggUrl+id,ResponseBigg.class);		
			return ResponseData.responseDataBuilder(resp);
			
		} catch (RestClientException e) {
			System.out.println("400 from bigg");
			return null;
		}
		
				
	}

	public ResponseData fromKeggIdGetPubchem(String id) {
		
		try {
			ResponseEntity<String> resp = restTemplate.getForEntity( kegg2PubchemUrl + id, String.class);		

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
