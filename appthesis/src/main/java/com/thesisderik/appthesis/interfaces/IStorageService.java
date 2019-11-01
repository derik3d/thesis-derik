package com.thesisderik.appthesis.interfaces;

import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface IStorageService {
	
    void init();

    String store(MultipartFile file, String rename);

    List<String> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

	String getTextDataFromResource(Resource resource);

	String getTextDataFromFileName(String filename);
	
}
