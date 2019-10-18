package com.thesisderik.appthesis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import com.thesisderik.appthesis.idscrawler.entities.ResponseBiggKegg;
import com.thesisderik.appthesis.idscrawler.entities.ResponseData;
import com.thesisderik.appthesis.idscrawler.entities.ResponseKegg;
import com.thesisderik.appthesis.interfaces.INamesCrawlerService;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;

@RestController
@RequestMapping("experiments/")
public class ExperimentsController {

	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	
	
	@RequestMapping("/") 
	public String gretings() {
		return "hola";
	}

	
	@RequestMapping(value = "datafortrain/", produces = "text/plain")
	public ResponseEntity<String> getDataForExperiment(@RequestParam(value="uqname", defaultValue="") String uqName) {

		String res = iSimpleGraphManager.getExperimentDataRawContentUQName(uqName);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}


}
