package com.thesisderik.appthesis.interfaces;

import java.util.Optional;

import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;

public interface INamesIntegrator {

	Optional<PubchemIdentifier> processKgmlIdentifierNotSavingError(String name);
	Optional<PubchemIdentifier> processSbmlIdentifierNotSavingError(String name);
	Optional<PubchemIdentifier> processSbmlIdentifier(String name, boolean saveError);
	Optional<PubchemIdentifier> processKgmlIdentifier(String name, boolean saveError);

}