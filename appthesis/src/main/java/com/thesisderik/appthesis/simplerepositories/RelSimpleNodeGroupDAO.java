package com.thesisderik.appthesis.simplerepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.graph.entities.GraphNode;
import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.simplegraph.entities.NodeGroupRelation;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainExperiment;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainNode;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainTask;

@Repository
public interface RelSimpleNodeGroupDAO extends CrudRepository<NodeGroupRelation, Long>{

	TreeSet<NodeGroupRelation> findAllByGroupIn(Set<PlainGroup> plainGroups);

	TreeSet<NodeGroupRelation> findAllByNode(PlainNode node);

	NodeGroupRelation findByGroupAndNode(PlainGroup group, PlainNode node);
	
	List<NodeGroupRelation> findAll();

}
