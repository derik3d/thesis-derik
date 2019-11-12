package com.thesisderik.appthesis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.viz.QueryVizFormat;
import com.thesisderik.appthesis.viz.VizGraphFormat;

@Controller
@RequestMapping("visualization")
public class VizController {



	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	
	

	@RequestMapping(
		      produces = MediaType.APPLICATION_JSON_VALUE)
	public String getViz() {
		return "sigma/index.html";
	}
	



	@RequestMapping(value="/query")
	@ResponseBody
	public VizGraphFormat saveQuery(
	        final QueryVizFormat data, final BindingResult bindingResult, final ModelMap model) {

		
		if (bindingResult.hasErrors()) {
	    	 System.out.println("error prro");
	    }
	    
		VizGraphFormat res = iSimpleGraphManager.getGraphDataFormatedForViz(data);
		
		System.out.println(data);
		
	     return res;
	}


	

	
	@PostMapping(value = "getGraphData", produces = "application/json")
	@ResponseBody
	public VizGraphFormat getGraphFiltered(@RequestBody QueryVizFormat data)  {
		
		VizGraphFormat res = iSimpleGraphManager.getGraphDataFormatedForViz(data);
		
		return res;
	}
	
	
	
}
