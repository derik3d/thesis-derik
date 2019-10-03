package com.thesisderik.appthesis.simplerepositories;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.graph.entities.GraphNode;
import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainNode;

@Repository
public interface SimpleGroupDAO extends CrudRepository<PlainGroup, Long>{

	Set<PlainGroup> findByNameIn(ArrayList<String> groups);

}
