package com.thesisderik.appthesis.simplerepositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.graph.entities.GraphNode;
import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainNode;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainTask;

@Repository
public interface SimpleFeatureDAO extends CrudRepository<PlainFeature, Long>{

	Set<PlainFeature> findAllByNameIn(ArrayList<String> feature);

	PlainFeature findByName(String featureName);

	Collection<? extends PlainFeature> findByNameStartsWith(String aGroupName);

}
