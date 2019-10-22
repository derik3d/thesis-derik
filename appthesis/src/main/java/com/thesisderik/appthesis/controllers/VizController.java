package com.thesisderik.appthesis.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("visualization")
public class VizController {


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
	
	
	
}
