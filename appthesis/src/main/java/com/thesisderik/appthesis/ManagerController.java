package com.thesisderik.appthesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import com.thesisderik.appthesis.idscrawler.IdCrawlerService;
import com.thesisderik.appthesis.idscrawler.entities.ResponseBigg;
import com.thesisderik.appthesis.idscrawler.entities.ResponseData;
import com.thesisderik.appthesis.idscrawler.entities.ResponseKegg;

@RestController
@RequestMapping("idmanager/")
public class ManagerController {

	
	@Autowired
	private IdCrawlerService idCrawlerService;
	
	
	@RequestMapping("/")
	public String gretings() {
		return "hola";
	}

	
	@RequestMapping("kegg2pubchem/")
	public ResponseEntity<ResponseData> kegg2pubchem(@RequestParam(value="id", defaultValue="") String id) {
		
		ResponseData res = idCrawlerService.fromKeggIdGetPubchem(id);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@RequestMapping("bigg2kegg/")
	public ResponseEntity<ResponseData> bigg2kegg(@RequestParam(value="id", defaultValue="") String id) {
		
		ResponseData res = idCrawlerService.fromBiggIdGetKegg(id);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@RequestMapping("bigg2pubchem/")
	public ResponseEntity<ResponseData> bigg2pubchem(@RequestParam(value="id", defaultValue="") String id) {
		
		ResponseData resinitial = idCrawlerService.fromBiggIdGetKegg(id);
		
		if (resinitial == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		ResponseData resfinal = idCrawlerService.fromKeggIdGetPubchem(resinitial.getResult());
		
		if (resfinal == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		resfinal.setQuery(resinitial.getQuery());
		
		return new ResponseEntity<>(resfinal,HttpStatus.OK);
	}
	

	
	@RequestMapping("pubchem2smiles/")
	public ResponseEntity<ResponseData> pubchem2smiles(@RequestParam(value="id", defaultValue="") String id) {
		
		ResponseData res = idCrawlerService.fromPubchemIdGetSmiles(id);
		
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

}
