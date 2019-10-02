package com.thesisderik.appthesis.services;

import org.springframework.stereotype.Service;

@Service
public interface IGraphManagerService {

	void processSbml(String string);

	void processKgml(String string);

}
