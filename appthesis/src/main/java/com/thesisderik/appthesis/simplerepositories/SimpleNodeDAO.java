package com.thesisderik.appthesis.simplerepositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainNode;

@Repository
public interface SimpleNodeDAO extends CrudRepository<PlainNode, Long>{

}
