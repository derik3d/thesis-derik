package com.thesisderik.appthesis.services;

import com.thesisderik.appthesis.idscrawler.entities.ResponseData;

public interface INamesCrawlerService {

	ResponseData fromPubchemIdGetSmiles(String id);

	ResponseData fromBiggIdGetKegg(String id);

	ResponseData fromKeggIdGetPubchem(String id);

}