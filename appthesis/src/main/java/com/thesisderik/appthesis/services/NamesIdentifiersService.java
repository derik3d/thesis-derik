package com.thesisderik.appthesis.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;
import com.thesisderik.appthesis.repositories.ErrorFoundRepositoryDao;
import com.thesisderik.appthesis.repositories.KgmlRepositoryDao;
import com.thesisderik.appthesis.repositories.PubchemRepositoryDao;
import com.thesisderik.appthesis.repositories.SbmlRepositoryDao;


@Service
public class NamesIdentifiersService implements INamesIdentifiersService{


	@Autowired
	PubchemRepositoryDao pubchemRepository;
	
	@Autowired
	KgmlRepositoryDao kgmlRepository;
	
	@Autowired
	SbmlRepositoryDao sbmlRepository;
	
	@Autowired
	ErrorFoundRepositoryDao errorFoundRepository;

	@Override
	public Optional<PubchemIdentifier> getPubchemIdentifierByName(String identifier) {
		Optional<PubchemIdentifier> byName = pubchemRepository.findByName(identifier);
		return byName;
	}

	@Override
	public Optional<KgmlIdentifier> getKgmlIdentifierByName(String identifier) {
		Optional<KgmlIdentifier> byName = kgmlRepository.findByName(identifier);
		return byName;
	}

	@Override
	public Optional<SbmlIdentifier> getSbmlIdentifierByName(String identifier) {
		Optional<SbmlIdentifier> byName = sbmlRepository.findByName(identifier);
		return byName;
	}

	@Override
	public PubchemIdentifier saveIdentifier(PubchemIdentifier identifier) {
		PubchemIdentifier res = pubchemRepository.save(identifier);
		return res;
	}

	@Override
	public KgmlIdentifier saveIdentifier(KgmlIdentifier identifier) {
		KgmlIdentifier res = kgmlRepository.save(identifier);
		return res;
	}

	@Override
	public SbmlIdentifier saveIdentifier(SbmlIdentifier identifier) {
		SbmlIdentifier res = sbmlRepository.save(identifier);
		return res;
	}

	@Override
	public ErrorFound saveErrorFound(ErrorFound er) {
		ErrorFound res = errorFoundRepository.save(er);
		return res;
	}

	
}
