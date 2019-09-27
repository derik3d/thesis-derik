package com.thesisderik.appthesis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thesisderik.appthesis.services.INamesIntegrator;

@Controller
@RequestMapping("identifiersmanager/")
public class IdentifiersManagerController {
	
	@Autowired
	INamesIntegrator iNamesIntegrator;
	
	
	

}
