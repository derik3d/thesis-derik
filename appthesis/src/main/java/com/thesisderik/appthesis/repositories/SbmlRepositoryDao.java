package com.thesisderik.appthesis.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;

@Repository
public interface SbmlRepositoryDao extends CrudRepository<SbmlIdentifier, Integer>{

	Optional<SbmlIdentifier> findByName(String string);

}
