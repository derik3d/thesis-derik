package com.thesisderik.appthesis.services;

import java.nio.file.Path;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thesisderik.appthesis.interfaces.IStorageService;


@Service
public class StorageService implements IStorageService{

	@Value("${ftp.credentials.url}")
    private String FTP_ADDRESS;
	@Value("${ftp.credentials.user}")
	private String LOGIN;
	@Value("${ftp.credentials.pass}")
	private String PSW;
	@Value("${ftp.credentials.folder-data}")
	private String FOLDER_DATA;
	
	/*
	public String store(@RequestParam("file") MultipartFile file,
									@RequestParam("rename") String rename,
	                               RedirectAttributes redirectAttributes) {
		
	*/
	
	@Override
	public String store(MultipartFile file, String rename) {

	    FTPClient con = null;

	    System.out.print("preparing step");
	    
	    try {
	        con = new FTPClient();
	        con.connect(FTP_ADDRESS);
	        

		    System.out.print("stepaaaaa 35");

	        if (con.login(LOGIN, PSW)) {
	            con.enterLocalPassiveMode(); // important!
	            con.setFileType(FTP.BINARY_FILE_TYPE);
	            //
	            String fullURL = FOLDER_DATA+rename;
	            boolean result = con.storeFile(fullURL, file.getInputStream());
	            con.logout();
	            con.disconnect();
	            
	            System.out.println(result+ " result");

	            //redirectAttributes.addFlashAttribute("message",
	            //        "You successfully uploaded " + file.getOriginalFilename() + "!");
	            
	            return fullURL;
	            
	        }
	    } catch (Exception e) {
	    	
	    	
	    	
	    	e.printStackTrace();
	        //redirectAttributes.addFlashAttribute("message",
	       //         "Could not upload " + file.getOriginalFilename() + "!");
	        
	        //return "failed";
	    }
	       
	    

	    return "false";
	    //return "redirect:/";
	}

	
	
	public String getFTP_ADDRESS() {
		return FTP_ADDRESS;
	}




	public void setFTP_ADDRESS(String fTP_ADDRESS) {
		FTP_ADDRESS = fTP_ADDRESS;
	}




	public String getLOGIN() {
		return LOGIN;
	}




	public void setLOGIN(String lOGIN) {
		LOGIN = lOGIN;
	}




	public String getPSW() {
		return PSW;
	}




	public void setPSW(String pSW) {
		PSW = pSW;
	}



	public String getFOLDER_DATA() {
		return FOLDER_DATA;
	}



	public void setFOLDER_DATA(String fOLDER_DATA) {
		FOLDER_DATA = fOLDER_DATA;
	}



	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public List<String> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Path load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Resource loadAsResource(String filename) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
