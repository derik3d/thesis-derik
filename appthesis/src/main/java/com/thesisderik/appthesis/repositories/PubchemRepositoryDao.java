package com.thesisderik.appthesis.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;

@Repository
public interface PubchemRepositoryDao extends CrudRepository<PubchemIdentifier, Integer>{

	Optional<PubchemIdentifier> findByName(String string);

}
