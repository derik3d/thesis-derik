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


	
	
}
