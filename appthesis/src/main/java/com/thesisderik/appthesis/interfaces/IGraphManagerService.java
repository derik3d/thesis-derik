package com.thesisderik.appthesis.interfaces;

import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public interface IGraphManagerService {

	void processSbml(InputStream inputStream);

	void processKgml(InputStream inputStream);

}
