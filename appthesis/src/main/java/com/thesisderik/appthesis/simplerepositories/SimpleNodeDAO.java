package com.thesisderik.appthesis.simplerepositories;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thesisderik.appthesis.persistence.identifiers.entities.ErrorFound;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainNode;

@Repository
public interface SimpleNodeDAO extends CrudRepository<PlainNode, Long>{

	PlainNode findByName(String nodeName);

	TreeSet<PlainNode> findAllByIdIn(Set<Long> nodesIdsToUse);

}
