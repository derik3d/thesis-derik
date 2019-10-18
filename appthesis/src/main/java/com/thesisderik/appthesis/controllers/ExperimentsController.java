package com.thesisderik.appthesis.controllers;

import java.util.ArrayList;
import java.util.Arrays;

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
import com.thesisderik.appthesis.interfaces.IAnalysisService;
import com.thesisderik.appthesis.interfaces.INamesCrawlerService;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;

@RestController
@RequestMapping("experiments/")
public class ExperimentsController {

	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	
	@Autowired
	IAnalysisService iAnalysisService;
	
	
	@RequestMapping("/") 
	public String gretings() {
		return "hola";
	}

	
	@RequestMapping(value = "dataForTrain/", produces = "text/plain")
	public ResponseEntity<String> getDataForTrain(@RequestParam(value="uqname", defaultValue="") String uqName) {

		String res = iSimpleGraphManager.getExperimentDataRawContentUQName(uqName);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "saveModelTrained/", produces = "text/plain")
	public ResponseEntity<String> saveModel(
			@RequestParam(value="uqname") String uqName,
			@RequestParam(value="model") String model,
			@RequestParam(value="classes") String classes,
			@RequestParam(value="features") String features) {
		
		boolean res = iSimpleGraphManager.saveModelDataWithExperimentUQName(uqName, model, classes, features);

		
		if (!res) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>("ok",HttpStatus.OK);
	}
	

	
	
	
	@RequestMapping(value = "getModelObjectTrained/", produces = "text/plain")
	public ResponseEntity<String> returnModelObject(
			@RequestParam(value="uqname") String uqName) {
		
		String res = iSimpleGraphManager.getExperimentModelObjectWithUQName(uqName);
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "getModelClassesTrained/", produces = "text/plain")
	public ResponseEntity<String> returnModelClasses(
			@RequestParam(value="uqname") String uqName) {
		
		String res =iSimpleGraphManager.getExperimentModelClassesWithUQName(uqName);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "getModelFeaturesTrained/", produces = "text/plain")
	public ResponseEntity<String> returnModelFeatures(
			@RequestParam(value="uqname") String uqName) {
		
		String res =iSimpleGraphManager.getExperimentModelFeaturesWithUQName(uqName);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}


	
	@RequestMapping(value = "dataForEvaluate/", produces = "text/plain")
	public ResponseEntity<String> getDataForEvaluate(@RequestParam(value="uqname", defaultValue="") String uqName) {

		String res = iSimpleGraphManager.getDataForEvaluateRawContentUQName(uqName);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "integrateData/", produces = "text/plain")
	public ResponseEntity<String> saveModel(
			@RequestParam(value="uqname") String uqName,
			@RequestParam(value="csvdata") String data) {
		

		boolean res = iAnalysisService.integrateFeaturesFile(new ArrayList<String>(Arrays.asList(uqName,data)));
		
		
		if (!res) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>("ok",HttpStatus.OK);
	}
	
	
	

}
