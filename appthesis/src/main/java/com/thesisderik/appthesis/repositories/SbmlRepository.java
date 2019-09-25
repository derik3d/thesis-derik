package com.thesisderik.appthesis.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;

@Repository
public interface SbmlRepository extends CrudRepository<SbmlIdentifier, Integer>{

}
