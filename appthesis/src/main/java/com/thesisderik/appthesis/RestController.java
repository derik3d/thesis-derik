package com.thesisderik.appthesis;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseKegg kegg2pubchem(@RequestParam(value="id", defaultValue="") String id) {
		return idCrawlerService.fromKeggIdGetPubchem(id);
	}
	
	@RequestMapping("bigg2kegg/")
	@ResponseBody
	public String bigg2kegg() {
		return idCrawlerService.fromBiggIdGetKegg();
	}

}
