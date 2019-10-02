package com.thesisderik.appthesis.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.services.INamesIntegrator;

@RestController
@RequestMapping("identifiersmanager/")
public class IdentifiersManagerController {
	
	@Autowired
	INamesIntegrator iNamesIntegrator;


	@RequestMapping("/") 
	public String gretings() {
		return "hola";
	}


	@RequestMapping("process/kgml/")
	public ResponseEntity<PubchemIdentifier> getKgmlByName(@RequestParam(value="id", defaultValue="") String name) {
		
		PubchemIdentifier res = iNamesIntegrator.processKgmlIdentifier(name,true).get();
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	

	@RequestMapping("process/sbml/")
	public ResponseEntity<PubchemIdentifier> getSbmlByName(@RequestParam(value="id", defaultValue="") String name) {
		
		PubchemIdentifier res = iNamesIntegrator.processSbmlIdentifier(name,true).get();
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	

}
