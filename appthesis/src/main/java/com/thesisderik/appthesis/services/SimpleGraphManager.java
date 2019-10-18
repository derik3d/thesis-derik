package com.thesisderik.appthesis.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
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
import com.thesisderik.appthesis.simplerepositories.RelSimpleNodeFeatureDAO;
import com.thesisderik.appthesis.simplerepositories.RelSimpleNodeGroupDAO;
import com.thesisderik.appthesis.simplerepositories.RelSimpleNodeNodeDAO;
import com.thesisderik.appthesis.simplerepositories.SimpleExperimentDAO;
import com.thesisderik.appthesis.simplerepositories.SimpleFeatureDAO;
import com.thesisderik.appthesis.simplerepositories.SimpleGroupDAO;
import com.thesisderik.appthesis.simplerepositories.SimpleNodeDAO;
import com.thesisderik.appthesis.simplerepositories.SimpleRelationDAO;
import com.thesisderik.appthesis.simplerepositories.SimpleTaskDAO;

@Service
public class SimpleGraphManager implements ISimpleGraphManager {
	
	final String defaultSeparator = "_CMDSEP_";
	final String defaultFeatureName = "NAME";
	final String defaultGroupAll = "ALL";

	
	final String defaultCSVName = "DEFCSVNAME";
	final String defaultCSVGroup = "DEFCSVGROUP";
	
	
	final String resultGroupSegment = "RESULT_GP_";
	final String resultFeatureSegment = "RESULT_FT_";
	final String resultPropertyName = "_PROPNAME_";
	final String resultDimentionSegment = "_DIM_";
	
	@Autowired
	RelSimpleNodeFeatureDAO relSimpleNodeFeatureDAO;

	@Autowired
	RelSimpleNodeGroupDAO relSimpleNodeGroupDAO;
	
	@Autowired
	RelSimpleNodeNodeDAO relSimpleNodeNodeDAO;
	
	@Autowired
	SimpleExperimentDAO simpleExperimentDAO;
	
	@Autowired
	SimpleFeatureDAO simpleFeatureDAO;
	
	@Autowired
	SimpleGroupDAO simpleGroupDAO;
	
	@Autowired
	SimpleNodeDAO simpleNodeDAO;
	
	@Autowired
	SimpleRelationDAO simpleRelationDAO;
	
	@Autowired
	SimpleTaskDAO simpleTaskDAO;
	
	

	@Override
	public PlainNode doNode(String name) {
		
		
		PlainNode pn = simpleNodeDAO.findByName(name);
		
		if(pn!=null) {
			return pn;
		}
		
		pn = new PlainNode();
		pn.setName(name);
		simpleNodeDAO.save(pn);
		
		
		////ALL NODES WITH NAME AND ALL PROPERTY
		
		
		createFeature(defaultFeatureName,name,name);
		createGroupRel(defaultGroupAll,name);
		
		
		return pn;
		
	}
	
	@Override
	public PlainFeature doFeature(String featureName) {
		
		PlainFeature feature = simpleFeatureDAO.findByName(featureName);
		
		if(feature!=null) {
			return feature;
		}
		
		feature = new PlainFeature();
		
		feature.setName(featureName);
		feature = simpleFeatureDAO.save(feature);
		return feature;
		
	}
	
	@Override
	public PlainRelation doRelation(String relationName) {
		
		PlainRelation relation = simpleRelationDAO.findByName(relationName);
		
		if(relation!=null) {
			return relation;
		}
		
		relation = new PlainRelation();
		
		relation.setName(relationName);
		relation = simpleRelationDAO.save(relation);
		return relation;
		
	}
	
	@Override
	public PlainTask doTask(String taskName) {
		
		PlainTask task = simpleTaskDAO.findByName(taskName);
				
		if(task!=null) {
			return task;
		}
		
		task = new PlainTask();
		
		task.setName(taskName);
		task = simpleTaskDAO.save(task);
		return task;
		
	}
	

	@Override
	public PlainGroup doGroup(String groupName) {
		
		PlainGroup group = simpleGroupDAO.findByName(groupName);
		
		if(group!=null) {
			return group;
		}
		
		group = new PlainGroup();
		
		group.setName(groupName);
		group = simpleGroupDAO.save(group);
		return group;
	}

	
	@Override
	public NodeFeatureRelation createFeature(String featureName, String value, String nodeName) {
		
		
		PlainFeature feature = doFeature(featureName);
		
		PlainNode node = doNode(nodeName);
		
		NodeFeatureRelation nfr = relSimpleNodeFeatureDAO.findByNodeAndFeature(node, feature);
		
		if(nfr==null) {
			nfr = new NodeFeatureRelation();
			nfr.setNode(node);
			nfr.setFeature(feature);
		}
		
		nfr.setValue(value);
		
		nfr = relSimpleNodeFeatureDAO.save(nfr);
		
		return nfr;

	}

	@Override
	public NodeNodeRelation createRelation(String relationName, String nodeNameA, String nodeNameB) {

		PlainRelation relation = doRelation(relationName);

		PlainNode nodeA = doNode(nodeNameA);

		PlainNode nodeB = doNode(nodeNameB);
		
		NodeNodeRelation nodenode = relSimpleNodeNodeDAO.findByNodeAAndNodeBAndRelation(nodeA,nodeB,relation);

		if(nodenode==null) {
			nodenode = relSimpleNodeNodeDAO.findByNodeAAndNodeBAndRelation(nodeB,nodeA,relation);
		}
		
		if(nodenode==null) {
			nodenode = new NodeNodeRelation();
			nodenode.setRelation(relation);
			nodenode.setNodeA(nodeA);
			nodenode.setNodeB(nodeB);
			
		}
		
		nodenode = relSimpleNodeNodeDAO.save(nodenode);
		
		return nodenode;
	}
	
	@Override
	public NodeGroupRelation createGroupRel(String groupName,String nodeName) {
		
		PlainGroup group = doGroup(groupName);
		
		PlainNode node = doNode(nodeName);
		
		NodeGroupRelation ngr = relSimpleNodeGroupDAO.findByGroupAndNode(group,node);
		
		if(ngr==null) {
			ngr = new NodeGroupRelation();
			ngr.setGroup(group);
			ngr.setNode(node);
		}
		
		ngr = relSimpleNodeGroupDAO.save(ngr);
		
		return ngr;
	}


	@Override
	public PlainExperiment createExperiment(String title, String description, ArrayList<String> groups, ArrayList<String> features,
			String targetTaskCommand, String taskQuery, String featureNameOverride) {
		

		PlainTask task = simpleTaskDAO.findByName(targetTaskCommand);
		
		if(task==null) {
			throw new RuntimeException("invalid service");
		}
		
		Set<PlainGroup> plainGroups = simpleGroupDAO.findAllByNameIn(groups);
		Set<PlainFeature> plainFeatures = simpleFeatureDAO.findAllByNameIn(features);
		
		
		PlainExperiment pe = new PlainExperiment();
		pe.setTitle(title);
		pe.setDescription(description);

		//pe.setPlainGroups(plainGroups);
		//pe.setPlainFeatures(plainFeatures);
		
		pe.setTask(task);
		pe.setTaskDescriptionCommand(taskQuery);
		
		
		String cmdPart = pe.getTask().getName()+defaultSeparator+pe.getTaskDescriptionCommand()+defaultSeparator;

		
		//def name override
		if(featureNameOverride.length()>0) {
			pe.setFeatureNameOverride(cmdPart+featureNameOverride);
		}else {
			
			String givenName = (""+Objects.hash( pe.getTitle(),pe.getDescription(),pe.getTask(),pe.getTaskDescriptionCommand()));
			
			pe.setFeatureNameOverride(cmdPart + givenName);
						
		}
		
		pe = simpleExperimentDAO.save(pe);
		
		//pe.getPlainFeatures().addAll(plainFeatures);
		
		//pe.getPlainGroups().addAll(plainGroups);
		
		
		for(PlainFeature pfitem: plainFeatures)
			pe.addPlainFeature(pfitem);
		
		for(PlainGroup pgitem: plainGroups)
			pe.addPlainGroup(pgitem);
		
		
		pe = simpleExperimentDAO.save(pe);

		
		return pe;
	}

	@Override
	public PlainExperiment createExperiment(String title, String description, ArrayList<String> groups, ArrayList<String> features,
			String targetTask, String taskQuery) {

		return createExperiment(title,description,groups,features,targetTask,taskQuery,"");
		
	}
	
	
	public ArrayList<PlainNode> findNodesForExperiment(PlainExperiment experiment){
		
		
		//Nodes at least in one of the groups
		//Nodes That has all the features, ignoring the features that all nodes doesnt have

		
		TreeSet<NodeGroupRelation> groupsUseByGroup = relSimpleNodeGroupDAO.
				findAllByGroupIn(experiment.getPlainGroups());
		
		
		ArrayList<PlainNode> byGroupNodes = new ArrayList<>( groupsUseByGroup.stream().
				map(NodeGroupRelation::getNode).collect(Collectors.toSet()) );
		
		for( PlainFeature pf : experiment.getPlainFeatures()) {
			
			TreeSet<NodeFeatureRelation> relationsUseByFeatures = relSimpleNodeFeatureDAO.
					findAllByFeature(pf);
		
			byGroupNodes.retainAll(relationsUseByFeatures.stream().map(NodeFeatureRelation::getNode).
					collect(Collectors.toSet()));
		
		}
		
		return byGroupNodes;
	}
	
	public TreeSet<PlainFeature> featuresOfNodesToUseInRange(ArrayList<PlainNode> nodes, Set<PlainFeature> setFeaturesRequired ){
		
		
		for(PlainNode node: nodes) {
			
			TreeSet<NodeFeatureRelation> nodeFeatures = relSimpleNodeFeatureDAO.findAllByNode(node);
			
			setFeaturesRequired.retainAll(nodeFeatures.stream().map(NodeFeatureRelation::getFeature).collect(Collectors.toSet()));
			
		}
		
		
		return new TreeSet<>(setFeaturesRequired);
		
	}
	

	
	public PlainGroup groupOfNodeInRange(PlainNode node, Set<PlainGroup> setFeaturesRequired ){
		
		TreeSet<NodeGroupRelation> groupRelations = relSimpleNodeGroupDAO.findAllByNode(node);
		
		
		List<Long> collectIds = setFeaturesRequired.stream().map(PlainGroup::getId).collect(Collectors.toList());
		
		Iterator<NodeGroupRelation> iterator = groupRelations.iterator();
		
		while(iterator.hasNext()) {
			
			
			long gindex = iterator.next().getGroup().getId();
			
			if(collectIds.contains(simpleGroupDAO.findById(gindex).get().getId()))
				return simpleGroupDAO.findById(gindex).get();
			
			
		}
		
		
		return null;
		
		/*
		TreeSet<PlainGroup> pgs = new TreeSet<>();//groupRelations.stream().map(NodeGroupRelation::getGroup).collect(Collectors.toSet()));
		
		for(NodeGroupRelation ngr : groupRelations)
			pgs.add( simpleGroupDAO.findById(ngr.getGroup().getId()).get() );
		
		pgs.retainAll(setFeaturesRequired);
		
		return pgs.stream().findFirst().get();
	*/	
		
	}
	
	
	public PlainExperiment saveOverrideExperimentName(PlainExperiment experiment, String givenName) {
		
		experiment.setFeatureNameOverride(givenName);
		
		experiment = simpleExperimentDAO.save(experiment);
		
		return experiment;
	}
	

	@Override
	public ExperimentRequestFileDataStructure getExperimentData(String experimentName) {


		PlainExperiment experiment = simpleExperimentDAO.findByTitle(experimentName);
		
		ArrayList<PlainNode> nodesToUse = findNodesForExperiment(experiment);
		
		ArrayList<PlainFeature> featuresToUse = new ArrayList<>(featuresOfNodesToUseInRange(nodesToUse, experiment.getPlainFeatures()));
		
		ArrayList<ArrayList<String>> dataRows = new ArrayList<>();
		
		//first row
		
		ArrayList<String> firstRow = new ArrayList<>();
		firstRow.add(defaultCSVName);
		firstRow.add(defaultCSVGroup);
		firstRow.addAll(featuresToUse.stream().map(PlainFeature::getName).collect(Collectors.toList()));
		
		//next data

		for(int i=0; i<nodesToUse.size(); i++) {
			
			ArrayList<String> row = new ArrayList<>();
			
			PlainNode currRowNode = nodesToUse.get(i);
			//add name
			row.add(currRowNode.getName());
			
			//add class
			row.add(groupOfNodeInRange(currRowNode,experiment.getPlainGroups()).getName());
			
			for(int j = 0; j<featuresToUse.size(); j++ ) {
				NodeFeatureRelation nfr = relSimpleNodeFeatureDAO.findByNodeAndFeature(currRowNode, featuresToUse.get(j));
				row.add(nfr.getValue());
			}
			
			dataRows.add(row);
		}
		
		
		ExperimentRequestFileDataStructure result = new ExperimentRequestFileDataStructure();
		
		
		
			
		result.setFileName(experiment.getFeatureNameOverride());
		
		
		result.setFirstRow(firstRow);
		result.setDataRows(dataRows);

		return result;
	}

	@Override
	public void integrateExperimentResult(ExperimentResultsFileDataStructure expRes) {

		
		ArrayList<ArrayList<String>> nodes = expRes.getDataRows();
		 
		ArrayList<String> namesRow = expRes.getFirstRow(); 

		for(int i = 0; i< nodes.size(); i++) {
			
			ArrayList<String> row = nodes.get(i);
			
			createGroupRel(resultGroupSegment + expRes.getFileName(),row.get(0)); // getting node name, first field
			
			for(int j = 1; j<row.size(); j++) {
				createFeature(resultFeatureSegment + expRes.getFileName() + resultPropertyName + namesRow.get(j) + resultDimentionSegment +j, row.get(j), row.get(0));
			}
			
		}
		
	}

	@Override
	public ArrayList<NodeGroupRelation> createGroupBulk(GroupFileDataStructure newGroup) {
		
		ArrayList<NodeGroupRelation> resNodes = new ArrayList<>();
		
		NodeGroupRelation createGroupRel;
		
		for(String nodeName: newGroup.getNodes()) {
			createGroupRel = createGroupRel(newGroup.getFileName(),nodeName);
			resNodes.add(createGroupRel);
		}
		
		if(resNodes.size()==0)
			return null;
		
		return resNodes;
	}

	@Override
	public String getExperimentDataRawContentUQName(String uqName) {
		
		PlainExperiment experiment = simpleExperimentDAO.findByFeatureNameOverride(uqName);

		ExperimentRequestFileDataStructure data = getExperimentData(experiment.getTitle());
		return data.buildCSVFile().get(1);
	}

	@Override
	public void saveModelDataWithExperimentUQName(String uqName, String modelObjectData, String modelTitlesData) {


		PlainExperiment experiment = simpleExperimentDAO.findByFeatureNameOverride(uqName);

		experiment.setModelObjectData(modelObjectData);
		experiment.setModelTitlesData(modelTitlesData);
		
		experiment = simpleExperimentDAO.save(experiment);

		
	}
	
	

}
