package com.thesisderik.appthesis.simplerepositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.graph.entities.GraphNode;
import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.simplegraph.entities.NodeNodeRelation;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainExperiment;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainNode;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainRelation;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainTask;

@Repository
public interface RelSimpleNodeNodeDAO extends CrudRepository<NodeNodeRelation, Long>{

	NodeNodeRelation findByNodeAAndNodeBAndRelation(PlainNode nodeA, PlainNode nodeB, PlainRelation relation);

	List<NodeNodeRelation> AllByNodeAId(Long nodeAId);
	List<NodeNodeRelation> AllByNodeBId(Long nodeBId);

}
