package com.thesisderik.appthesis.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;

@Repository
public interface PubchemRepositoryDao extends CrudRepository<PubchemIdentifier, Integer>{

	PubchemIdentifier findByName(String string);

}
