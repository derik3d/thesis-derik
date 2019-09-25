package com.thesisderik.appthesis.services;

import java.util.List;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;

public interface INamesIdentifiersService {

	boolean persistPubchem(PubchemIdentifier data);
	PubchemIdentifier getPubchemIdentifier(int id);
	List<PubchemIdentifier> getPubchemIdentifiers();
	
	boolean persistKgml(KgmlIdentifier data);
	PubchemIdentifier getKgmlIdentifier(int id);
	List<PubchemIdentifier> getKgmlIdentifiers();
	
	boolean persistSbml(SbmlIdentifier data);
	PubchemIdentifier getSbmlIdentifier(int id);
	List<PubchemIdentifier> getSbmlIdentifiers();

}
