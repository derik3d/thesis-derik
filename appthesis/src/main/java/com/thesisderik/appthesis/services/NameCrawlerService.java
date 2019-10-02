package com.thesisderik.appthesis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.thesisderik.appthesis.idscrawler.entities.ResponseBiggBiocyc;
import com.thesisderik.appthesis.idscrawler.entities.ResponseBiggKegg;
import com.thesisderik.appthesis.idscrawler.entities.ResponseBiocyc;
import com.thesisderik.appthesis.idscrawler.entities.ResponseData;
import com.thesisderik.appthesis.idscrawler.entities.ResponseKegg;
import com.thesisderik.appthesis.idscrawler.entities.ResponseSMILESPubchem;

@Service
public class NameCrawlerService implements INamesCrawlerService{

	private static final String pubchem2SmilesUrl = "https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/%s/property/CanonicalSmiles/json";
	private static final String kegg2PubchemUrl = "http://rest.kegg.jp/conv/pubchem/%s";
	private static final String biocyc2PubchemUrl = "https://websvc.biocyc.org/getxml?%s";
	private static final String bigg2KeggUrl = "http://bigg.ucsd.edu/api/v2/universal/metabolites/%s";
	private static final String bigg2BiocycUrl = "http://bigg.ucsd.edu/api/v2/universal/metabolites/%s";
	
	
	
	
	
	@Autowired
	private RestTemplate restTemplate;

	
	
	@Override
	public ResponseData fromPubchemIdGetSmiles(String id) {
		

		try {
			
			ResponseSMILESPubchem resp = restTemplate.getForObject(String.format(pubchem2SmilesUrl, id),ResponseSMILESPubchem.class);		
			return ResponseData.responseDataBuilder(resp);
			
		} catch (RestClientException e) {
			System.out.println("400 from pubchem");
			return null;
		}
		
				
	}
	
	
	@Override
	public ResponseData fromBiggIdGetKegg(String id) {
		

		try {
			
			ResponseBiggKegg resp = restTemplate.getForObject(String.format(bigg2KeggUrl, id),ResponseBiggKegg.class);		
			return ResponseData.responseDataBuilder(resp);
			
		} catch (RestClientException e) {
			System.out.println("400 from bigg");
			return null;
		}
		
				
	}

	@Override
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


	@Override
	public ResponseData fromBiggIdGetBiocyc(String id) {
		

		try {
			
			ResponseBiggBiocyc resp = restTemplate.getForObject(String.format(bigg2BiocycUrl, id),ResponseBiggBiocyc.class);		
			return ResponseData.responseDataBuilder(resp);
			
		} catch (RestClientException e) {
			System.out.println("400 from bigg");
			return null;
		}
		
				
	}

	@Override
	public ResponseData fromBiocycIdGetPubchem(String id) {
		
		try {
			ResponseEntity<String> resp = restTemplate.getForEntity( String.format(biocyc2PubchemUrl, id), String.class);		

			if(resp.getStatusCode() == HttpStatus.OK) {
				
				try {
					
					String resString = resp.getBody();

					if(resString.length() < 1)
						throw new Exception("zero length data");
					
					String cpd;
					String pubchem;
					
					if(resString.contains("<dblink-db>PUBCHEM</dblink-db>")){

						ResponseBiocyc rs = new ResponseBiocyc();
						
						cpd=id;
						
						resString = resString.substring(resString.indexOf("<dblink-db>PUBCHEM</dblink-db>"));
						
						resString = resString.substring(0,resString.indexOf("</dblink-oid>"));
						
						pubchem = resString.substring(resString.indexOf("<dblink-oid>")+"<dblink-oid>".length() );
						
						rs.setCpd(cpd);
						rs.setPubchem(pubchem);

						return ResponseData.responseDataBuilder(rs);
						
					}
				
				}catch(Exception ex) {
					System.out.println("not reading well biocyc response");
					return null;
				}
			}
		} catch (RestClientException e) {
			System.out.println("400 from biocyc");
			return null;
		}
		
		return null;
		
	}
	
}
