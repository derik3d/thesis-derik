package com.thesisderik.appthesis.simplerepositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.graph.entities.GraphNode;
import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainNode;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainRelation;

@Repository
public interface SimpleRelationDAO extends CrudRepository<PlainRelation, Long>{

	PlainRelation findByName(String relationName);

}
