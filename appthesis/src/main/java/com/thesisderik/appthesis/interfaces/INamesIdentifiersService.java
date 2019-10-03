package com.thesisderik.appthesis.interfaces;

import java.util.Optional;

import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;

public interface INamesIdentifiersService {

	Optional<PubchemIdentifier> getPubchemIdentifierByName(String identifier);
	
	Optional<KgmlIdentifier> getKgmlIdentifierByName(String identifier);

	Optional<SbmlIdentifier> getSbmlIdentifierByName(String identifier);

	PubchemIdentifier saveIdentifier(PubchemIdentifier identifier);

	KgmlIdentifier saveIdentifier(KgmlIdentifier identifier);

	SbmlIdentifier saveIdentifier(SbmlIdentifier identifier);
	
	ErrorFound saveErrorFound(ErrorFound er);


}