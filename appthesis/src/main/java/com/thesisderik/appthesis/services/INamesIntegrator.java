package com.thesisderik.appthesis.services;

import java.util.Optional;

import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;

public interface INamesIntegrator {

	Optional<PubchemIdentifier> processKgmlIdentifier(String name);
	Optional<PubchemIdentifier> processSbmlIdentifier(String name);

}