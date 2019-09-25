package com.thesisderik.appthesis.services;

import java.util.List;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;

public interface INamesIdentifiersService {

	PubchemIdentifier createPubchemEntry();

	PubchemIdentifier getPubchemEntry();

}
