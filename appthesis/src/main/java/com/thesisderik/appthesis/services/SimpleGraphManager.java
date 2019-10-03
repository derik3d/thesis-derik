package com.thesisderik.appthesis.services;

import java.util.ArrayList;
import java.util.Set;

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
		
		PlainNode pn = new PlainNode();
		pn.setName(name);
		simpleNodeDAO.save(pn);
		
		return pn;
		
	}
	
	@Override
	public PlainFeature doFeature(String featureName) {
		
		PlainFeature feature = new PlainFeature();
		feature.setName(featureName);
		feature = simpleFeatureDAO.save(feature);
		return feature;
		
	}
	
	@Override
	public PlainRelation doRelation(String featureName) {
		
		PlainRelation relation = new PlainRelation();
		relation.setName(featureName);
		relation = simpleRelationDAO.save(relation);
		return relation;
		
	}
	
	@Override
	public PlainTask doTask(String taskName) {
		
		PlainTask task = new PlainTask();
		task.setName(taskName);
		task = simpleTaskDAO.save(task);
		return task;
		
	}
	

	@Override
	public PlainGroup doGroup(String groupName) {
		
		PlainGroup task = new PlainGroup();
		task.setName(groupName);
		task = simpleGroupDAO.save(task);
		return task;
	}

	
	@Override
	public NodeFeatureRelation createFeature(String featureName, String value, String nodeName) {
		
		
		PlainFeature feature = doFeature(featureName);
		
		PlainNode node = doNode(nodeName);
		
		NodeFeatureRelation nfr = new NodeFeatureRelation();
		nfr.setFeature(feature);
		nfr.setNode(node);
		nfr.setValue(value);
		
		relSimpleNodeFeatureDAO.save(nfr);
		
		return nfr;

	}

	@Override
	public NodeNodeRelation createRelation(String relationName, String nodeNameA, String nodeNameB) {

		PlainRelation relation = doRelation(relationName);

		PlainNode nodeA = doNode(nodeNameA);

		PlainNode nodeB = doNode(nodeNameB);
		
		NodeNodeRelation nodenode = new NodeNodeRelation();
		nodenode.setRelation(relation);
		nodenode.setNodeA(nodeA);
		nodenode.setNodeB(nodeB);
		
		nodenode = relSimpleNodeNodeDAO.save(nodenode);
		
		return nodenode;
	}
	
	@Override
	public NodeGroupRelation createGroupRel(String groupName,String nodeName) {
		
		PlainGroup group = doGroup(groupName);
		
		PlainNode node = doNode(nodeName);
		
		NodeGroupRelation ngr = new NodeGroupRelation();
		ngr.setGroup(group);
		ngr.setNode(node);
		relSimpleNodeGroupDAO.save(ngr);
		
		return ngr;
	}


	@Override
	public PlainExperiment createExperiment(String title, String description, ArrayList<String> groups, ArrayList<String> features,
			String targetTaskCommand, String taskQuery, String featureNameOverride) {
		

		PlainTask task = simpleTaskDAO.findByName(targetTaskCommand);
		Set<PlainGroup> plainGroups = simpleGroupDAO.findByNameIn(groups);
		Set<PlainFeature> plainFeatures = simpleFeatureDAO.findByNameIn(groups);
		
		
		PlainExperiment pe = new PlainExperiment();
		pe.setTitle(title);
		pe.setDescription(description);
		pe.setPlainGroups(plainGroups);
		pe.setPlainFeatures(plainFeatures);
		pe.setTask(task);
		pe.setTaskDescriptionCommand(taskQuery);
		pe.setFeatureNameOverride(featureNameOverride);
		pe = simpleExperimentDAO.save(pe);
		
		return pe;
	}

	@Override
	public PlainExperiment createExperiment(String title, String description, ArrayList<String> groups, ArrayList<String> features,
			String targetTask, String taskQuery) {

		return createExperiment(title,description,groups,features,targetTask,taskQuery,null);
		
	}

	@Override
	public ExperimentRequestFileDataStructure getExperimentData(String string) {


		PlainExperiment experiment = simpleExperimentDAO.getByName(string);
		
		ExperimentRequestFileDataStructure result = new ExperimentRequestFileDataStructure();
		
		
		
		
		
		return result;
	}

	@Override
	public void integrateExperimentResult(ExperimentResultsFileDataStructure expRes) {

		ArrayList<ArrayList<String>> nodes = expRes.getDataRows();
		 
		ArrayList<String> namesRow = expRes.getFirstRow(); 

		for(int i = 0; i< nodes.size(); i++) {
			
			ArrayList<String> row = nodes.get(i);
			
			createGroupRel("RESULT_GP_"+ expRes.getFileName(),row.get(0)); // getting node name, first field
			
			for(int j = 1; j<row.size(); j++) {
				createFeature("RESULT_FT_"+ expRes.getFileName() +"_PROPNAME_"+ namesRow.get(j) + "_DIM_"+j, row.get(j), row.get(0));
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
	
	

}
