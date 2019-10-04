package com.thesisderik.appthesis.simplerepositories;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.graph.entities.GraphNode;
import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.simplegraph.entities.NodeFeatureRelation;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainExperiment;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainNode;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainTask;

@Repository
public interface RelSimpleNodeFeatureDAO extends CrudRepository<NodeFeatureRelation, Long>{

	TreeSet<NodeFeatureRelation> findAllByFeature(PlainFeature pf);

	NodeFeatureRelation findByNodeAndFeature(PlainNode currRowNode, PlainFeature plainFeature);

	TreeSet<NodeFeatureRelation> findAllByNode(PlainNode node);

}
