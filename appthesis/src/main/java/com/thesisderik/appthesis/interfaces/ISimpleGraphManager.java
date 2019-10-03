package com.thesisderik.appthesis.interfaces;

import java.util.ArrayList;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.GroupFileDataStructure;

public interface ISimpleGraphManager {

	void createNode(
			String node
			);

	void createFeature(
			String featureName,
			String value,
			String node
			);

	
	void createRelation(
			String relationName,
			String nodeA,
			String nodeB
			);

	void createGroup(
			String groupName,
			String node
			);
	
	void createGroupBulk(GroupFileDataStructure newGroup);
	
	void createExperiment(
			String title,
			String description,
			ArrayList<String> groups,
			ArrayList<String> features,
			String targetTask,
			String taskQuery,
			String featureNameOverride
			);
	
	void createExperiment(
			String title,
			String description,
			ArrayList<String> groups,
			ArrayList<String> features,
			String taskQuery,
			String query
			);
	
	ExperimentRequestFileDataStructure getExperimentData(String string);

	void integrateExperimentResult(ExperimentResultsFileDataStructure expRes);




}
