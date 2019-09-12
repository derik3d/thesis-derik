package com.thesisderik.appthesis;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestController {
	
	@RequestMapping("/")
	@ResponseBody
	public String gretings() {
		return "hola";
	}

}
