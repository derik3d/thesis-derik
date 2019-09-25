package com.thesisderik.appthesis.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;
import com.thesisderik.appthesis.repositories.KgmlRepositoryDao;
import com.thesisderik.appthesis.repositories.PubchemRepositoryDao;
import com.thesisderik.appthesis.repositories.SbmlRepositoryDao;

public class NamesIdentifiersService implements INamesIdentifiersService{


	@Autowired
	PubchemRepositoryDao pubchemRepository;
	
	@Autowired
	KgmlRepositoryDao kgmlRepository;
	
	@Autowired
	SbmlRepositoryDao sbmlRepository;


	@Override
	public boolean persistPubchem(PubchemIdentifier data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PubchemIdentifier getPubchemIdentifier(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PubchemIdentifier> getPubchemIdentifiers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean persistKgml(KgmlIdentifier data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PubchemIdentifier getKgmlIdentifier(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PubchemIdentifier> getKgmlIdentifiers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean persistSbml(SbmlIdentifier data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PubchemIdentifier getSbmlIdentifier(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PubchemIdentifier> getSbmlIdentifiers() {
		// TODO Auto-generated method stub
		return null;
	}


	
	
}
