package com.thesisderik.appthesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import idscrawler.IdCrawlerService;
import idscrawler.entities.ResponseBigg;
import idscrawler.entities.ResponseData;
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
	public ResponseEntity<ResponseData> kegg2pubchem(@RequestParam(value="id", defaultValue="") String id) {
		
		ResponseData res = idCrawlerService.fromKeggIdGetPubchem(id);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@RequestMapping("bigg2kegg/")
	@ResponseBody
	public ResponseEntity<ResponseData> bigg2kegg(@RequestParam(value="id", defaultValue="") String id) {
		
		ResponseData res = idCrawlerService.fromBiggIdGetKegg(id);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

}
