package com.thesisderik.appthesis.controllers;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thesisderik.appthesis.interfaces.IAnalysisService;
import com.thesisderik.appthesis.interfaces.IGraphManagerService;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.interfaces.IStorageService;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainExperiment;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainTask;

@Controller
@RequestMapping("front")
public class FrontController {
	
	String userDefProp = "USR_DEF_PROP_";
		
	@Autowired
	IStorageService iStorageService;
	
	@Autowired
	IAnalysisService iAnalysisService;
	
	@Autowired
	IGraphManagerService iGraphManagerService;
	
	@Autowired
	ISimpleGraphManager iSimpleGraphManager;

	
	@ModelAttribute("allGroups")
	public List<PlainGroup> populateGroups() {
	    return iSimpleGraphManager.getPlainGroups();
	}
	
	@ModelAttribute("allFeatures")
	public List<PlainFeature> populateFeatures() {
	    return iSimpleGraphManager.getPlainFeatures();
	}
	
	@ModelAttribute("allTasks")
	public List<PlainTask> populateTasks() {
	    return iSimpleGraphManager.getPlainTasks();
	}
	
	@ModelAttribute("allFiles")
	public List<String> populateFiles() {
	    return iStorageService.loadAll();
	}
	
	
	@RequestMapping({"/","/experimentStart"})
    public String listUploadedFiles(Model model){
		model.addAttribute("plainExperiment", new PlainExperiment());
        return "uploadForm";
    }
	


	@RequestMapping(value="/experimentStart", params={"save"})
	public String saveSeedstarter(
	        final PlainExperiment plainExperiment, final BindingResult bindingResult, final ModelMap model) {
	    if (bindingResult.hasErrors()) {
	    	 System.out.println("error prro");
	    }
	    
	    
	    System.out.println("hsvethis: " + plainExperiment.toString());
	    return "redirect:/front/experimentStart";
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("graph")
	public String uploaGraph(
			@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		
		String result = iStorageService.store(file, file.getOriginalFilename());
		
    	try {
			iGraphManagerService.processSbml(iStorageService.loadAsResource(file.getOriginalFilename()).getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + result + " graph!, the sistem will try to instegrate the data on the server");
		return "redirect:/front/";
	}
	
	@PostMapping("integrate")
	public String integrateData(
			@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		
		String result = iStorageService.store(file, file.getOriginalFilename());
		
		iAnalysisService.integrateFeaturesFile(userDefProp + file.getOriginalFilename(), file.getOriginalFilename());
		
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + result + " data! the sistem will try to instegrate the data on the server");
        
		return "redirect:/front/";
	}
	
	
}