package com.thesisderik.appthesis.services;

import com.thesisderik.appthesis.idscrawler.entities.ResponseData;

public interface INamesCrawlerService {


	ResponseData fromBiggIdGetKegg(String id);

	ResponseData fromKeggIdGetPubchem(String id);
	

	ResponseData fromBiggIdGetBiocyc(String id);

	ResponseData fromBiocycIdGetPubchem(String id);
	
	
	ResponseData fromPubchemIdGetSmiles(String id);


}