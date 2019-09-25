package com.thesisderik.appthesis.repositories;

import org.springframework.data.repository.CrudRepository;

import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;

public interface IdsRepositoty extends CrudRepository<PubchemIdentifier, Integer>{

}
