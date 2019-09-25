package com.thesisderik.appthesis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thesisderik.appthesis.idscrawler.entities.ResponseData;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
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

	
	@RequestMapping("persistPubchem/")
	public ResponseEntity<PubchemIdentifier> createPubchemItem() {
		
		PubchemIdentifier res = iNamesIdentifiersService.createPubchemEntry();
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	
	@RequestMapping("getPubchemById/")
	public ResponseEntity<PubchemIdentifier> getpubchemwithid() {
		
		PubchemIdentifier res = iNamesIdentifiersService.getPubchemEntry();
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	

}