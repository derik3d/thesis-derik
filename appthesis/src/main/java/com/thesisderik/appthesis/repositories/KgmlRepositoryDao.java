package com.thesisderik.appthesis.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;

@Repository
public interface KgmlRepositoryDao extends CrudRepository<KgmlIdentifier, Integer>{

}
