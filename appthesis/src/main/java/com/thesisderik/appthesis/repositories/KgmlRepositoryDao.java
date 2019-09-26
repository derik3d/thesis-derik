package com.thesisderik.appthesis.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;

@Repository
public interface KgmlRepositoryDao extends CrudRepository<KgmlIdentifier, Integer>{

	Optional<KgmlIdentifier> findByName(String string);
	
}
