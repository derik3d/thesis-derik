package com.thesisderik.appthesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import idscrawler.IdCrawlerService;
import idscrawler.entities.ResponseKegg;

@Controller
public class RestController {

	
	@Autowired
	private IdCrawlerService idCrawlerService;
	
	
	@RequestMapping("/")
	@ResponseBody
	public String gretings() {
		return "hola";
	}

	
	@RequestMapping("kegg2pubchem/")
	@ResponseBody
	public ResponseEntity kegg2pubchem(@RequestParam(value="id", defaultValue="") String id) {
		
		ResponseKegg res = idCrawlerService.fromKeggIdGetPubchem(id);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
		
		//ResponseKegg res = new ResponseKegg();
		//res.setCpd(id);
		//return res;
	}
	
	@RequestMapping("bigg2kegg/")
	@ResponseBody
	public String bigg2kegg() {
		return idCrawlerService.fromBiggIdGetKegg();
	}

}
