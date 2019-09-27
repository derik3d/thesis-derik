package com.thesisderik.appthesis.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;

@Repository
public interface ErrorFoundRepositoryDao extends CrudRepository<ErrorFound, Integer>{

}
