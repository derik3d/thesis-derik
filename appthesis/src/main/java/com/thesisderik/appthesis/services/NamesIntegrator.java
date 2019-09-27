package com.thesisderik.appthesis.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;
import com.thesisderik.appthesis.repositories.KgmlRepositoryDao;
import com.thesisderik.appthesis.repositories.PubchemRepositoryDao;
import com.thesisderik.appthesis.repositories.SbmlRepositoryDao;


@Service
public class NamesIntegrator implements INamesIntegrator{


	@Autowired
	INamesIdentifiersService iNamesIdentifiersService;
	
	@Autowired
	INamesCrawlerService iNamesCrawlerService;

	@Override
	public Optional<PubchemIdentifier> processKgmlIdentifier(String name) {

		
		
		
		
		return null;
	}

	@Override
	public Optional<PubchemIdentifier> processSbmlIdentifier(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
