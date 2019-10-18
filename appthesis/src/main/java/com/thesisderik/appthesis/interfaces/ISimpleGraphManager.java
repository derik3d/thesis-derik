package com.thesisderik.appthesis.interfaces;

import java.util.ArrayList;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.GroupFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.entities.NodeFeatureRelation;
import com.thesisderik.appthesis.persistence.simplegraph.entities.NodeGroupRelation;
import com.thesisderik.appthesis.persistence.simplegraph.entities.NodeNodeRelation;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainExperiment;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainFeature;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainGroup;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainNode;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainRelation;
import com.thesisderik.appthesis.persistence.simplegraph.entities.PlainTask;

public interface ISimpleGraphManager extends IExperimentDataIntegrator{

	public PlainNode doNode(
			String nodeName
			);
	
	public PlainFeature doFeature(
			String featureName
			);
	
	public PlainRelation doRelation(
			String featureName
			);
	
	public PlainGroup doGroup(
			String groupName
			);
	
	public PlainTask doTask(
			String taskName
			);



	public NodeFeatureRelation createFeature(
			String featureName,
			String value,
			String nodeName
			);

	
	public NodeNodeRelation createRelation(
			String relationName,
			String nodeNameA,
			String nodeNameB
			);

	public NodeGroupRelation createGroupRel(
			String groupName,
			String nodeName
			);
		
	public PlainExperiment createExperiment(
			String title,
			String description,
			ArrayList<String> groups,
			ArrayList<String> features,
			String targetTaskCommand,
			String taskQuery,
			String featureNameOverride
			);
	
	public PlainExperiment createExperiment(
			String title,
			String description,
			ArrayList<String> groups,
			ArrayList<String> features,
			String targetTaskCommand,
			String taskQuery
			);
	
	
	
	public ExperimentRequestFileDataStructure getExperimentData(String experimentName);
	
	public ExperimentRequestFileDataStructure getExperimentData(String experimentName, boolean includeAllGroup);

	public void integrateExperimentResult(ExperimentResultsFileDataStructure expRes);

	public ArrayList<NodeGroupRelation> createGroupBulk(GroupFileDataStructure newGroup);
	

	public String getExperimentDataRawContentUQName(String uqName);

	public boolean saveModelDataWithExperimentUQName(String uqName, String modelObjectData, String modelTitlesData);

	public String getExperimentModelObjectWithUQName(String uqName);

	public String getExperimentModelLabelsWithUQName(String uqName);

	public String getDataForEvaluateRawContentUQName(String uqName);





}
