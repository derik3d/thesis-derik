package com.thesisderik.appthesis.services;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.interfaces.IExperimentDataIntegrator;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.layout.HierarchyRelation;
import com.thesisderik.appthesis.layout.LayoutItem;
import com.thesisderik.appthesis.layout.LayoutManager;
import com.thesisderik.appthesis.layout.LayoutManager.Layouts;
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
import com.thesisderik.appthesis.viz.ColorDataMapper;
import com.thesisderik.appthesis.viz.DataMapperUtils;
import com.thesisderik.appthesis.viz.EdgeViz;
import com.thesisderik.appthesis.viz.GeneralMapper;
import com.thesisderik.appthesis.viz.InfoNodeFormat;
import com.thesisderik.appthesis.viz.NodeViz;
import com.thesisderik.appthesis.viz.QueryVizFormat;
import com.thesisderik.appthesis.viz.VizGraphFormat;

@Service
public class SimpleGraphManager implements ISimpleGraphManager, IExperimentDataIntegrator {
	
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
	private TreeSet<NodeFeatureRelation> findAllByNode;
	
	

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
		
		synchronized(simpleFeatureDAO){
		 feature = simpleFeatureDAO.findByName(featureName);
		
		if(feature!=null) {
			return feature;
		}
		
		feature = new PlainFeature();
		
		feature.setName(featureName);
		feature = simpleFeatureDAO.save(feature);
		return feature;
		}
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
		
		
		synchronized(simpleGroupDAO){
			 group = simpleGroupDAO.findByName(groupName);
			
			if(group!=null) {
				return group;
			}
			
			group = new PlainGroup();
			
			group.setName(groupName);
			group = simpleGroupDAO.save(group);
			return group;
		}
	}

	
	@Override
	public NodeFeatureRelation createFeature(String featureName, String value, String nodeName) {
		
		
		PlainFeature feature = doFeature(featureName);
		
		PlainNode node = doNode(nodeName);
		
		NodeFeatureRelation nfr = relSimpleNodeFeatureDAO.findByNodeAndFeature(node, feature);
		
		if(nfr==null && value instanceof Object) {
			nfr = new NodeFeatureRelation();
			nfr.setNode(node);
			nfr.setFeature(feature);
			nfr.setValue(value);
			
			//System.out.println(nfr);
			
			nfr = relSimpleNodeFeatureDAO.save(nfr);
			
			return nfr;
		}
		
		else return nfr;
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
			ngr = relSimpleNodeGroupDAO.save(ngr);
			return ngr;
		}else
			return ngr;
	}



	@Override
	public PlainExperiment createExperiment(String title, String description, ArrayList<String> groups, ArrayList<String> features,
			String targetTask, String taskQuery) {

		return createExperiment(title,description,groups,features,targetTask,taskQuery,"");
		
	}
	

	@Override
	public PlainExperiment createExperiment(String title, String description,
			ArrayList<String> groups, ArrayList<String> features,
			String taskName, String taskQuery, String featureNameOverride) {
		
		return  createExperiment(title, description, groups, features,
				taskName, taskQuery, featureNameOverride);
		
	}
	

	

	@Override
	public PlainExperiment createExperiment(String title, String description,
			ArrayList<String> groups, ArrayList<String> features,
			String taskName, String taskQuery, String featureNameOverride, ArrayList<String> addFeaturesOfFTGroups) {
		

		PlainTask task = simpleTaskDAO.findByName(taskName);
		
		if(task==null) {
			throw new RuntimeException("invalid service");
		}
		
		Set<PlainGroup> plainGroups = simpleGroupDAO.findAllByNameIn(groups);
		Set<PlainFeature> plainFeatures = simpleFeatureDAO.findAllByNameIn(features);
		
		if(addFeaturesOfFTGroups instanceof Object) {
			
			for(String aGroupName : addFeaturesOfFTGroups) {
				plainFeatures.addAll(getFeaturesOfGroup(aGroupName));
			}
			
		}
		
		
		return  createExperiment(title, description, plainGroups, plainFeatures,
			 task, taskQuery, featureNameOverride);
		
	}

	private Collection<? extends PlainFeature> getFeaturesOfGroup(String aGroupName) {
		
		
		if(aGroupName.contains(resultGroupSegment)) {
			
			
			aGroupName.replace(resultGroupSegment, resultFeatureSegment);
			
			return simpleFeatureDAO.findByNameStartsWith(aGroupName);

			
		}
		
		return null;
	}

	@Override
	public PlainExperiment createExperiment(String title,
			String description, Set<PlainGroup> plainGroups, Set<PlainFeature> plainFeatures,
			PlainTask task, String taskQuery, String featureNameOverride) {
		
		task = simpleTaskDAO.findById(task.getId()).get();

		PlainExperiment pe = new PlainExperiment();
		pe.setTitle(title);
		pe.setDescription(description);

		//pe.setPlainGroups(plainGroups);
		//pe.setPlainFeatures(plainFeatures);
		
		pe.setTask(task);
		pe.setTaskDescriptionCommand(taskQuery);
		
		
		String cmdPart = pe.getTask().getName()+defaultSeparator+pe.getTaskDescriptionCommand()+defaultSeparator;

		
		//def name override
		if(featureNameOverride instanceof Object && featureNameOverride.length()>0) {
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
	
	
	public ArrayList<PlainNode> findNodesForExperiment(PlainExperiment experiment, boolean includeAllGroup, List<PlainGroup> ignoringGroups){
		
		
		final List<Long> nodesToIgnoreList;
		
		
		final ArrayList<Long> ignoringGroupsList;
		
		
		
		if(ignoringGroups != null) {
			
			//System.out.println("alf1");
			//System.out.println("ignoring size"+ignoringGroups.size());

			ignoringGroupsList = (ArrayList<Long>) ignoringGroups.stream().map(PlainGroup::getId).collect(Collectors.toList());
		}
		else {
			//System.out.println("alf2");

			ignoringGroupsList=null;
		}
		//Nodes at least in one of the groups
		//Nodes That has all the features, ignoring the features that all nodes doesnt have

		ArrayList<PlainNode> byGroupNodes;
		
		
		Set<PlainGroup> pgList;
		
		
		if(includeAllGroup) {
			
			pgList = new HashSet<PlainGroup>();
			pgList.add(simpleGroupDAO.findByName(defaultGroupAll));
			
		}else {
			
			pgList = experiment.getPlainGroups();
		
		}
			TreeSet<NodeGroupRelation> groupsUseByGroup = relSimpleNodeGroupDAO.
				findAllByGroupIn(pgList);
			
			
			
			//exclude from group
			if(ignoringGroups!=null) {
				

				List<NodeGroupRelation> allRels = (List<NodeGroupRelation>) relSimpleNodeGroupDAO.findAll();
				
				//System.out.println(ignoringGroupsList);
				
				List<Long> nodesToIgnore = new ArrayList<>();
				
				nodesToIgnore.addAll(
						
						allRels.stream().filter(  n -> { 
							if(ignoringGroupsList==null)
								return false;
							
							Long currgpid = n.getGroup().getId();
							//System.out.println(currgpid);
							return ignoringGroupsList.contains(currgpid);
						
						} ).map(NodeGroupRelation::getNode).map(PlainNode::getId).collect(Collectors.toList())
						);
				
				nodesToIgnoreList = nodesToIgnore;
				
			}else {
				nodesToIgnoreList = null;
			}
			
			//System.out.println("ig list size: " + nodesToIgnoreList!=null?nodesToIgnoreList.size():"null");
		
			byGroupNodes = new ArrayList<>( groupsUseByGroup.stream().
				map(NodeGroupRelation::getNode).collect(Collectors.toSet()) );
		
			
		
		for( PlainFeature pf : experiment.getPlainFeatures()) {
			
			TreeSet<NodeFeatureRelation> relationsUseByFeatures = relSimpleNodeFeatureDAO.
					findAllByFeature(pf);
		
			byGroupNodes.retainAll(relationsUseByFeatures.stream().map(NodeFeatureRelation::getNode).
					collect(Collectors.toSet()));
		
		}
		
		
		if(nodesToIgnoreList instanceof Object) {
			
			byGroupNodes = new ArrayList<>(byGroupNodes.stream().filter( n -> {
				return !nodesToIgnoreList.contains(n.getId());
				}).collect(Collectors.toList()));
			
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
		return getExperimentData(experimentName,false,null);
	}
	
	@Override
	public ExperimentRequestFileDataStructure getExperimentData(String experimentName, boolean includeAllGroup, List<PlainGroup> ignoreGroupList) {


		PlainExperiment experiment = simpleExperimentDAO.findByTitle(experimentName);
		
		ArrayList<PlainNode> nodesToUse = findNodesForExperiment(experiment,includeAllGroup, ignoreGroupList);
		
		ArrayList<PlainFeature> featuresToUse = new ArrayList<>(featuresOfNodesToUseInRange(nodesToUse, experiment.getPlainFeatures()));
		
		ArrayList<ArrayList<String>> dataRows = new ArrayList<>();
		
		//first row
		
		ArrayList<String> firstRow = new ArrayList<>();
		firstRow.add(defaultCSVName);
		firstRow.add(defaultCSVGroup);
		firstRow.addAll(featuresToUse.stream().map(PlainFeature::getName).collect(Collectors.toList()));
		
		//next data

		
		Set<PlainGroup> experimentGroupsList = experiment.getPlainGroups();
		if(ignoreGroupList instanceof Object && includeAllGroup) {
			experimentGroupsList.add(simpleGroupDAO.findByName(defaultGroupAll));
		}
		
		
		for(int i=0; i<nodesToUse.size(); i++) {
			
			ArrayList<String> row = new ArrayList<>();
			
			PlainNode currRowNode = nodesToUse.get(i);
			//add name
			row.add(currRowNode.getName());
			
			//add class
			row.add(groupOfNodeInRange(currRowNode, experimentGroupsList).getName());
			
			for(int j = 0; j<featuresToUse.size(); j++ ) {
				NodeFeatureRelation nfr = relSimpleNodeFeatureDAO.findByNodeAndFeature(currRowNode, featuresToUse.get(j));
				row.add(nfr.getValue());
			}
			
			dataRows.add(row);
		}
		
		
		ExperimentRequestFileDataStructure result = new ExperimentRequestFileDataStructure();
		
		
		
			
		result.setFileName(experiment.getFeatureNameOverride());
		
		result.setUQName(experiment.getFeatureNameOverride());
		result.setFirstRow(firstRow);
		result.setDataRows(dataRows);

		return result;
	}

	@Override
	public void integrateExperimentResult(ExperimentResultsFileDataStructure expRes) {

		
		
		ArrayList<ArrayList<String>> nodes = expRes.getDataRows();
		 
		ArrayList<String> namesRow = expRes.getFirstRow(); 

		
		
		
		
		
		
		Consumer<? super ArrayList<String>> createGroupRelation = row -> {
			
			createGroupRel(resultGroupSegment + expRes.getFileName(), row.get(0));
			
			IntConsumer createFeature = index -> {
				
				createFeature(resultFeatureSegment + expRes.getFileName() + resultPropertyName + namesRow.get(index) + resultDimentionSegment + index, row.get(index), row.get(0));
				
			};
			
			
			IntStream.range(1, row.size()).forEach( createFeature );
			
		};
		nodes.stream().forEach( createGroupRelation );
		
		
		
		
		
		
		
		
		/*
		
		for(int i = 0; i< nodes.size(); i++) {
			
			ArrayList<String> row = nodes.get(i);
			
			createGroupRel(resultGroupSegment + expRes.getFileName(),row.get(0)); // getting node name, first field
			
			for(int j = 1; j<row.size(); j++) {
				createFeature(resultFeatureSegment + expRes.getFileName() + resultPropertyName + namesRow.get(j) + resultDimentionSegment +j, row.get(j), row.get(0));
			}
			
		}
		
		
		
		*/
		
		
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
		//System.out.println(data);
		return data.buildCSVFile().get(1);
	}

	@Override
	public String getExperimentDataRawContentByGroupNodes(String uqName) {
		
		PlainGroup plainGroup = simpleGroupDAO.findByName(uqName);
		Set<String> set = relSimpleNodeGroupDAO.findAllByGroup(plainGroup).stream().map(NodeGroupRelation::getNode).map(PlainNode::getName).collect(Collectors.toSet());

		return "name"+"\n"+String.join("\n", set)+"\n";
	}

	@Override
	public boolean saveModelDataWithExperimentUQName(String uqName, String modelObjectData,String modelClassesData, String modelFeaturesData) {


		PlainExperiment experiment = simpleExperimentDAO.findByFeatureNameOverride(uqName);

		experiment.setModelClassesData(modelClassesData);
		experiment.setModelFeaturesData(modelFeaturesData);
		experiment.setModelObjectData(modelObjectData);
		
		experiment = simpleExperimentDAO.save(experiment);

		return true;
		
	}

	@Override
	public String getExperimentModelObjectWithUQName(String uqName) {
		PlainExperiment experiment = simpleExperimentDAO.findByFeatureNameOverride(uqName);
		
		String res = experiment.getModelObjectData();
		
		if(res.length()>0) {
			return res;
		}
		
		return null;
	}

	@Override
	public String getExperimentModelFeaturesWithUQName(String uqName) {
		PlainExperiment experiment = simpleExperimentDAO.findByFeatureNameOverride(uqName);

		String res = experiment.getModelFeaturesData();
		
		if(res.length()>0) {
			return res;
		}
		
		return null;

	}

	@Override
	public String getExperimentModelClassesWithUQName(String uqName) {
		PlainExperiment experiment = simpleExperimentDAO.findByFeatureNameOverride(uqName);

		String res = experiment.getModelClassesData();
		
		if(res.length()>0) {
			return res;
		}
		
		return null;

	}

	@Override
	public String getDataForEvaluateRawContentUQName(String uqName) {

		PlainExperiment experiment = simpleExperimentDAO.findByFeatureNameOverride(uqName);

		ExperimentRequestFileDataStructure data = getExperimentData(experiment.getTitle(),true,null);
		
		return data.buildCSVFile().get(1);
	}

	@Override
	public VizGraphFormat getGraphDataFormatedForViz(QueryVizFormat data) {
		
		Set<PlainNode> nodes;
			
		if(!(data.getGroups()==null) && data.getGroups().size()>0) {
			nodes = new HashSet<PlainNode>( relSimpleNodeGroupDAO.findAllByGroupIn(data.getGroups()).stream().map(NodeGroupRelation::getNode).collect(Collectors.toList()));

		}else {
			nodes = new HashSet<PlainNode>( relSimpleNodeGroupDAO.findAll().stream().map(NodeGroupRelation::getNode).collect(Collectors.toList()));
		}
		
		List<NodeNodeRelation> allRelations = (List<NodeNodeRelation>) relSimpleNodeNodeDAO.findAll();
		
		allRelations.removeIf((item)-> {
			PlainNode plainNodeA = item.getNodeA();
			PlainNode plainNodeB = item.getNodeB();
			if(nodes.contains(plainNodeA) && nodes.contains(plainNodeB))
				return false;
			else
				return true;
			});
		
		final VizGraphFormat res = new VizGraphFormat();
		final ArrayList<ColorDataMapper> mapper = new ArrayList<>();
		
		if(data.getMappers()!=null)
			mapper.addAll(data.getMappers());
		
		Random r = new Random();
		
		
		ArrayList<Integer> numList = new ArrayList<>();
		for(int i=0 ; i<8; i++)
			numList.add(i);
		
		//find ranges
		
		
		ArrayList<GeneralMapper> generalMappers = new ArrayList<>();
		

		for(int i=0 ; i<8; i++)
			generalMappers.add(null);
		
		if(mapper.size()>0) {
			for(int i=0 ; i<8; i++) {
				
				//para cada mapper
				
				if(mapper.get(i) instanceof Object && mapper.get(i).isEnabled()==true 
						&& mapper.get(i).isUseGroup()==false && mapper.get(i).getMapper() instanceof Object) {
					
					

					PlainFeature pfea = mapper.get(i).getBindingPlainFeature();
					TreeSet<NodeFeatureRelation> findAllByFeature = relSimpleNodeFeatureDAO.findAllByFeature(pfea);
					
					GeneralMapper genMapper = DataMapperUtils.getMapper(findAllByFeature.stream().map(NodeFeatureRelation::getValue) ,mapper.get(i).getBottom(), mapper.get(i).getTop(), mapper.get(i).getMapper());
					
					generalMappers.set(i,genMapper);
					
				}
				
				
			}
		}
		
		
		Consumer<PlainNode> nodeParser = plainNode -> {
		
			NodeViz nv = new NodeViz();
			
			nv.setId(plainNode.getName());
			nv.setLabel(plainNode.getName());
			//nv.setX(r.nextInt(10));
			//nv.setY(r.nextInt(10));
			nv.setX(0);
			nv.setY(0);
			nv.setSize(3);
			
			if(mapper.size()>0){
			
			ArrayList<String> colors = new ArrayList<>();
			
			for(int i=0 ; i<8; i++)
				colors.add(null);
			

			Consumer<Integer> processNodes = ivalue -> {
					
				if(mapper.get(ivalue).isEnabled()) {

					if(mapper.get(ivalue).isUseGroup() ) {
						
						PlainGroup pg = mapper.get(ivalue).getBindingPlainGroup();
						
						NodeGroupRelation findByGroupAndNode = relSimpleNodeGroupDAO.findByGroupAndNode(pg, plainNode);

						//System.out.println("node: "+ plainNode);
						//System.out.println("group: "+ pg);

						if(findByGroupAndNode instanceof Object) {
							
							System.out.println("cr "+ mapper.get(ivalue).getBottom());
							
							String color = GeneralMapper.colorString(mapper.get(ivalue).getBottom());
							
							colors.set(ivalue,color);
							
						}
						
					}else {
					
						PlainFeature pf = mapper.get(ivalue).getBindingPlainFeature();

						NodeFeatureRelation findByFeatureAndNode = relSimpleNodeFeatureDAO.findByFeatureAndNode(pf, plainNode);
						
						if(findByFeatureAndNode instanceof Object) {
							
							String color = generalMappers.get(ivalue).processValue(findByFeatureAndNode.getValue());
							//String color =  DataMapperUtils.processValue(boundsList.get(ivalue).get(0), boundsList.get(ivalue).get(1), mapper.get(ivalue).getBottom() , mapper.get(ivalue).getTop() , findByFeatureAndNode.getValue() , mapper.get(ivalue).getMapper() ) ;
							
							colors.set(ivalue,color);
							
						}
						
					}
				
				}
				
			};
			
			
			
			numList.stream().forEach(processNodes);
			
			boolean enableCopy = true;
			
			for(int i =1; i<8;i++) {
				if(mapper.get(i).isEnabled() && generalMappers.get(i) instanceof Object) {
					enableCopy = false;
				}else {
					enableCopy = true;
				}
				if(colors.get(i)==null && enableCopy) {
					colors.set(i,colors.get(i-1));
				}
			}
			
			nv.setAllColors(colors);
			
			}
			
			res.addNode(nv);
			
		};
		
		
		nodes.stream().forEach(nodeParser);
		
		
		
		Iterator<NodeNodeRelation> relIt = allRelations.iterator();	
		
		int num = 0;
		while(relIt.hasNext()) {
			num++;
			NodeNodeRelation nnrl = relIt.next();
			PlainRelation pr =  nnrl.getRelation();
			EdgeViz ev = new EdgeViz();
			ev.setSource(nnrl.getNodeA().getName());
			ev.setTarget(nnrl.getNodeB().getName());
			//ev.setId(pr.getName());
			ev.setId("edge "+num);
			res.addEdge(ev);
		}

		Set<String> draggedNodes = null;
	
		PlainGroup compound = simpleGroupDAO.findByName("GP_COMPOUND_NODE");
		
		if(compound instanceof Object) {
			
			Predicate<NodeGroupRelation> fromSelectedAndHasGroup = gnrel -> {
				
				return gnrel.getGroup().getId() == compound.getId() && nodes.contains(gnrel.getNode());
				
			};
			
			draggedNodes = relSimpleNodeGroupDAO.findAll().stream()
					.filter(fromSelectedAndHasGroup).map(NodeGroupRelation::getNode)
					.map(PlainNode::getName).collect(Collectors.toSet());
			
		}
		

		
		Map<Integer,Layouts> layersLayouts = layersLayouts(data.getRelations());
		
		Map<Integer, Set<String>> nodesGroups = unfoldDataForLayout(res.getNodes().stream().map(NodeViz::getId).collect(Collectors.toSet()), data.getLayoutItems() );
		
		LayoutManager.layoutGraph(res, layersLayouts, nodesGroups, draggedNodes);
		
		
		
		return res;
	}

	
	public Map<Integer,Layouts> layersLayouts(List<HierarchyRelation> relations){
		
		Map<Integer,Layouts> mapRes = new HashMap<>();
		
		if(relations instanceof Object) {
			for(HierarchyRelation rel : relations) {
				if(rel.isEnabled()) {
						mapRes.put(rel.getLayerNumber(), rel.getLay());
				}
			}
		}
		
		return mapRes;
		
	}
	
	public Map<Integer, Set<String>> unfoldDataForLayout(Set<String> nodes, List<LayoutItem> layItems){
		
		Map<Integer, Set<String>> nodeGroupsForLayout = new HashMap<>();

		System.out.println();
		System.out.println();
		System.out.println(layItems);
		
		if(layItems instanceof Object) {
			
			for(LayoutItem it: layItems) {
				if(it.isEnabled()) {
					nodeGroupsForLayout = new HashMap<>();
					break;
				}
			}
			
			
			for(LayoutItem it: layItems) {
				if(it.isEnabled()) {
					
					if(nodeGroupsForLayout.containsKey(it.getLayer())) {
						nodeGroupsForLayout.get(it.getLayer()).addAll(getFilteredNodes(nodes, it));
					}else {
						nodeGroupsForLayout.put(it.getLayer(), getFilteredNodes(nodes, it));
					}
					if(nodes.size()==0) {
						break;
					}
					
				}
			}
		
		}
		
		return nodeGroupsForLayout;
	}
	
	public Set<String> getFilteredNodes(Set<String> availableNodes, LayoutItem layItem){
				
		TreeSet<NodeGroupRelation> relationsNodeWithGroup = relSimpleNodeGroupDAO.findAllByGroup(layItem.getGroup());
		Predicate<NodeGroupRelation> insideListAvailable = relGr -> {
			return availableNodes.contains(relGr.getNode().getName());
		};
		
		final Set<String> filteredResult = relationsNodeWithGroup.parallelStream().filter(insideListAvailable).map(NodeGroupRelation::getNode).map(PlainNode::getName).collect(Collectors.toSet());
		
		Set<String> newfilteredResult = null;
		
		if(layItem.isFilter()) {
			
			Predicate<NodeFeatureRelation> keepCondition;
			
			if(layItem.getValue1() instanceof Object && layItem.getValue1().length() > 0) {
				//range
				keepCondition = rel -> {
					double r0 = Double.parseDouble(layItem.getValue0());
					double r1 = Double.parseDouble(layItem.getValue1());
					double val = Double.parseDouble(rel.getValue());
					
					
					return r0<= val && r1>= val;
				};
			}else {
				//equals
				keepCondition = rel -> {
					return  rel.getValue().equals(layItem.getValue0());
				};
			}
			

			Predicate<NodeFeatureRelation> insideListAvailableFeat = relft -> {
				return filteredResult.contains(relft.getNode().getName());
			};
			
			newfilteredResult = relSimpleNodeFeatureDAO.findAllByFeature(layItem.getFeature()).parallelStream().
			filter(insideListAvailableFeat).filter(keepCondition).map(NodeFeatureRelation::getNode)
			.map(PlainNode::getName).collect(Collectors.toSet());
						
		}
		
		if(newfilteredResult instanceof Object) {
			availableNodes.removeAll(newfilteredResult);
			return newfilteredResult;
		}else {
			availableNodes.removeAll(filteredResult);
			return filteredResult;
		}
	}

	@Override
	public List<PlainGroup> getPlainGroups() {

		return (List<PlainGroup>) simpleGroupDAO.findAll();
	}

	@Override
	public List<PlainFeature> getPlainFeatures() {

		return (List<PlainFeature>) simpleFeatureDAO.findAll();
	}

	@Override
	public List<PlainTask> getPlainTasks() {

		return (List<PlainTask>) simpleTaskDAO.findAll();
	}

	@Override
	public List<PlainExperiment> getExperiments() {

		return (List<PlainExperiment>) simpleExperimentDAO.findAll();
	}

	@Override
	public ExperimentRequestFileDataStructure getExperimentDataNotAnalized(String experimentName) {
		
		PlainExperiment foundExperiment = simpleExperimentDAO.findByTitle(experimentName);
		String groupName = foundExperiment.getFeatureNameOverride();
		String filteredName = groupName.substring(groupName.lastIndexOf(defaultSeparator)+defaultSeparator.length(),groupName.length());
		String groupResultName = resultGroupSegment+filteredName;
		
		List<PlainGroup> groupIgnore = new ArrayList<PlainGroup>();
		
		
		PlainGroup groupFound = simpleGroupDAO.findByName(groupResultName);
		
		//System.out.println(groupFound);

		
		if(groupFound!=null) {
			//System.out.println("adding");

			groupIgnore.add(groupFound);
		}
		//System.out.println(groupIgnore.get(0));
		if(groupIgnore.size()>0)
			return getExperimentData(experimentName,true,groupIgnore);
		return null;
	}

	@Override
	public Map<String, String> getMappedDataOfNodeByName(String nodeName) {
		
		Map<String, String> featuresOfNode = new HashMap<>();

		PlainNode PlainNode = simpleNodeDAO.findByName(nodeName);
		
		TreeSet<NodeFeatureRelation> featuresOfThisNode = relSimpleNodeFeatureDAO.findAllByNode(PlainNode);
		
		for(NodeFeatureRelation featRel : featuresOfThisNode) {
			featuresOfNode.put(featRel.getFeature().getName(), featRel.getValue());
		}

		return featuresOfNode;
	}

	@Override
	public Set<String> getRelatedNodesForNodeByNodeName(String nodeName) {

		
		PlainNode PlainNode = simpleNodeDAO.findByName(nodeName);

		List<NodeNodeRelation> relatedNodesRelationsUseB = relSimpleNodeNodeDAO.findAllByNodeAId(PlainNode.getId());
		List<NodeNodeRelation> relatedNodesRelationsUseA = relSimpleNodeNodeDAO.findAllByNodeBId(PlainNode.getId());
		
		Set<String> relatedNodesNames = new HashSet<>();
		
		for(NodeNodeRelation nodeRel :relatedNodesRelationsUseA) {
			relatedNodesNames.add(nodeRel.getNodeA().getName());
		}
		
		for(NodeNodeRelation nodeRel :relatedNodesRelationsUseB) {
			relatedNodesNames.add(nodeRel.getNodeB().getName());
		}

		return relatedNodesNames;
	}

	@Override
	public InfoNodeFormat getSimpleNodeDataFormat(String nodeName) {
		InfoNodeFormat res = new InfoNodeFormat();
		
		PlainNode node = simpleNodeDAO.findByName(nodeName);
		
		List<String> groups = new ArrayList<>();
		Map<String,String> features = new HashMap<>();
		res.setNodeName(nodeName);
		res.setGroups(groups);
		res.setFeatures(features);
		
		Consumer<NodeFeatureRelation> getFeatures = nf -> {
			
			features.put(nf.getFeature().getName(), nf.getValue());
			
		};
		
		if(node instanceof Object) {
			
			groups.addAll(relSimpleNodeGroupDAO.findAllByNode(node).parallelStream().map(NodeGroupRelation::getGroup).map(PlainGroup::getName).collect(Collectors.toList()));
			
			relSimpleNodeFeatureDAO.findAllByNode(node).parallelStream().forEach(getFeatures);
			
		}
		
		return res;
	}
	
	
	

}
