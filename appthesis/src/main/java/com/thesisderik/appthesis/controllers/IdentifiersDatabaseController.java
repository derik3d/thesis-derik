package com.thesisderik.appthesis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thesisderik.appthesis.idscrawler.entities.ResponseData;
import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;
import com.thesisderik.appthesis.services.INamesCrawlerService;
import com.thesisderik.appthesis.services.INamesIdentifiersService;

@RestController
@RequestMapping("identifierspersist/")
public class IdentifiersDatabaseController {

	
	@Autowired
	private INamesIdentifiersService iNamesIdentifiersService;
	
	
	@RequestMapping("/") 
	public String gretings() {
		return "hola";
	}

	
	@RequestMapping("persist/pubchem/")
	public ResponseEntity<PubchemIdentifier> createPubchemItem(@RequestBody PubchemIdentifier pubchemIdentifier) {
		
		PubchemIdentifier res = iNamesIdentifiersService.saveIdentifier(pubchemIdentifier);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	
	@RequestMapping("persist/kgml/")
	public ResponseEntity<KgmlIdentifier> createKgmlItem(@RequestBody KgmlIdentifier kgmlIdentifier) {
		
		KgmlIdentifier res = iNamesIdentifiersService.saveIdentifier(kgmlIdentifier);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	
	@RequestMapping("persist/sbml/")
	public ResponseEntity<SbmlIdentifier> createSbmlItem(@RequestBody SbmlIdentifier sbmlIdentifier) {
		
		SbmlIdentifier res = iNamesIdentifiersService.saveIdentifier(sbmlIdentifier);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	
	@RequestMapping("getByName/pubchem/")
	public ResponseEntity<PubchemIdentifier> getPubchemByName(@RequestParam(value="id", defaultValue="") String name) {
		
		PubchemIdentifier res = iNamesIdentifiersService.getPubchemIdentifierByName(name).get();
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	
	@RequestMapping("getByName/kgml/")
	public ResponseEntity<KgmlIdentifier> getKgmlByName(@RequestParam(value="id", defaultValue="") String name) {
		
		KgmlIdentifier res = iNamesIdentifiersService.getKgmlIdentifierByName(name).get();
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	
	@RequestMapping("getByName/sbml/")
	public ResponseEntity<KgmlIdentifier> getSbmlByName(@RequestParam(value="id", defaultValue="") String name) {
		
		KgmlIdentifier res = iNamesIdentifiersService.getKgmlIdentifierByName(name).get();
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	

}