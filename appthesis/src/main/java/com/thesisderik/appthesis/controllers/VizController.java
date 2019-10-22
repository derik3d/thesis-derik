package com.thesisderik.appthesis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.viz.VizGraphFormat;

@Controller
@RequestMapping("visualization")
public class VizController {



	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	
	

	@RequestMapping(value = "getGraphByGroupTest", 
		      produces = MediaType.APPLICATION_JSON_VALUE)
	public String getGraphByGroupTest() {
		return "testvizgraph.json";
	}

	@RequestMapping(value = "getViz", 
		      produces = MediaType.APPLICATION_JSON_VALUE)
	public String getViz() {
		return "sigma/index.html";
	}
	

	

	
	@RequestMapping(value = "getGraphByGroup", produces = "application/json")
	@ResponseBody
	public VizGraphFormat getGraphByGroup(
			@RequestParam(value="group", defaultValue="ALL") String group) throws JsonProcessingException {//QUITAR DEF VALUE
		

		VizGraphFormat res = iSimpleGraphManager.getGraphFormatedWithGroup(group);
		
		
		return res;
	}
	
	
	
}
