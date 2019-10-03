package com.thesisderik.appthesis.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.GroupFileDataStructure;

@Service
public class SimpleGraphManager implements ISimpleGraphManager {

	@Override
	public void createNode(String node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createFeature(String featureName, String value, String node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createRelation(String relationName, String nodeA, String nodeB) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createGroup(String groupName, String node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createGroupBulk(GroupFileDataStructure newGroup) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createExperiment(String title, String description, ArrayList<String> groups, ArrayList<String> features,
			String targetService, String query, String featureNameOverride) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createExperiment(String title, String description, ArrayList<String> groups, ArrayList<String> features,
			String targetService, String query) {
		// TODO Auto-generated method stub

	}

	@Override
	public ExperimentRequestFileDataStructure getExperimentData(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void integrateExperimentResult(ExperimentResultsFileDataStructure expRes) {
		// TODO Auto-generated method stub

	}

}
