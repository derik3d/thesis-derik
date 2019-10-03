package com.thesisderik.appthesis.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.idscrawler.entities.ResponseData;
import com.thesisderik.appthesis.interfaces.INamesCrawlerService;
import com.thesisderik.appthesis.interfaces.INamesIdentifiersService;
import com.thesisderik.appthesis.interfaces.INamesIntegrator;
import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
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
	public Optional<PubchemIdentifier> processKgmlIdentifier(String name,boolean saveError) {

		PubchemIdentifier pubchemIdentifier;
		
		Optional<KgmlIdentifier> isSavedOnDBKgml = iNamesIdentifiersService.getKgmlIdentifierByName(name);
		
		//is present this kgml id on db
		if(isSavedOnDBKgml.isPresent()) {
			
			//return related pubchem entity
			pubchemIdentifier = isSavedOnDBKgml.get().getTarget();
			
		}else {
			
			//crawl pubchem id for this kgml id
			ResponseData fromKeggIdGetPubchem = iNamesCrawlerService.fromKeggIdGetPubchem(name);
			
			//found a result
			if(fromKeggIdGetPubchem instanceof Object) {				
				
				//try to find result on db
				Optional<PubchemIdentifier> isThePubchemIdentifieralreadyOnDB = iNamesIdentifiersService.getPubchemIdentifierByName(fromKeggIdGetPubchem.getResult());

				//we have a record
				if(isThePubchemIdentifieralreadyOnDB.isPresent()) {
					pubchemIdentifier = isThePubchemIdentifieralreadyOnDB.get();
				}
				// we dont have a record, create it
				else {
					pubchemIdentifier = new PubchemIdentifier(fromKeggIdGetPubchem.getResult());
					pubchemIdentifier = iNamesIdentifiersService.saveIdentifier(pubchemIdentifier);
				}
				
				
				KgmlIdentifier kgmlIdentifier = new KgmlIdentifier();
				
				kgmlIdentifier.setName(name);
				kgmlIdentifier.setTarget(pubchemIdentifier);
				
				iNamesIdentifiersService.saveIdentifier(kgmlIdentifier);
				
				}
			//not found a result
			else {
				if(saveError)
					iNamesIdentifiersService.saveErrorFound(new ErrorFound(name,"Kgml"));
				
				pubchemIdentifier = null;
				
			}
			
			
		}

		if(pubchemIdentifier == null)
			return Optional.empty();
		
		Optional<PubchemIdentifier> res = Optional.of(pubchemIdentifier);
		
		return res;
	}

	@Override
	public Optional<PubchemIdentifier> processSbmlIdentifier(String name, boolean saveError) {

		KgmlIdentifier kgmlIdentifier;
		PubchemIdentifier pubchemIdentifier;
		
		Optional<SbmlIdentifier> isSavedOnDBSgml = iNamesIdentifiersService.getSbmlIdentifierByName(name);
		

		//is present this sgml id on db
		if(isSavedOnDBSgml.isPresent()) {
			
			//return related pubchem entity
			pubchemIdentifier = isSavedOnDBSgml.get().getTarget().getTarget();
			
		}else {
			
			//crawl kgml id for this sbml id
			ResponseData fromBiggIdGetKegg = iNamesCrawlerService.fromBiggIdGetKegg(name);
			
			//found a result
			if(fromBiggIdGetKegg instanceof Object) {
				
				//try to find result on db
				Optional<KgmlIdentifier> isTheKgmlIdentifieralreadyOnDB = iNamesIdentifiersService.getKgmlIdentifierByName(fromBiggIdGetKegg.getResult());

				//we have a record
				if(isTheKgmlIdentifieralreadyOnDB.isPresent()) {
					kgmlIdentifier = isTheKgmlIdentifieralreadyOnDB.get();
					pubchemIdentifier = kgmlIdentifier.getTarget();
				}
				// we dont have a record, create it
				else {
					
					Optional<PubchemIdentifier> resultKgmlAfterTryToSaveOnDB = processKgmlIdentifier(fromBiggIdGetKegg.getResult(),saveError);
					
					if(resultKgmlAfterTryToSaveOnDB.isPresent())
					{
						pubchemIdentifier  = resultKgmlAfterTryToSaveOnDB.get();
						kgmlIdentifier = iNamesIdentifiersService.getKgmlIdentifierByName(fromBiggIdGetKegg.getResult()).get();

						SbmlIdentifier sbmlIdentifier = new SbmlIdentifier();
						
						sbmlIdentifier.setName(name);
						sbmlIdentifier.setTarget(kgmlIdentifier);
						
						iNamesIdentifiersService.saveIdentifier(sbmlIdentifier);
						
					}
					else {
						pubchemIdentifier=null;
					}
				}
				
				
				}
			//not found a result
			else {
				
				if(saveError)
					iNamesIdentifiersService.saveErrorFound(new ErrorFound(name,"Sbml"));
				
				pubchemIdentifier = null;
				
			}
			
			
		}
		
		if(pubchemIdentifier == null) {
			////byocyc query????
		}

		
		
		if(pubchemIdentifier == null) 
			return Optional.empty();
		
		Optional<PubchemIdentifier> res = Optional.of(pubchemIdentifier);
		
		return res;
	}

	@Override
	public Optional<PubchemIdentifier> processKgmlIdentifierNotSavingError(String name) {
		return processKgmlIdentifier(name,false);
	}

	@Override
	public Optional<PubchemIdentifier> processSbmlIdentifierNotSavingError(String name) {
		return processSbmlIdentifier(name,false);
	}
	

	
}
