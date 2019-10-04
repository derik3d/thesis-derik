package com.thesisderik.appthesis;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.interfaces.IAnalisysService;
import com.thesisderik.appthesis.interfaces.IGraphBuilder;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.persistence.graph.entities.Graph;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.GroupFileDataStructure;
import com.thesisderik.appthesis.rawgraphparser.RawGraphParser;
import com.thesisderik.appthesis.services.GraphBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimpleGraphRepoTests {

	
	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	

	@Autowired
	IAnalisysService iAnalisysService;
	

	@Test
	public void simpleLeadGraphTest() throws Exception {

		//create nodes
		iSimpleGraphManager.doNode("A");
		iSimpleGraphManager.doNode("B");
		iSimpleGraphManager.doNode("C");
		iSimpleGraphManager.doNode("D");
		
		//features

		iSimpleGraphManager.createFeature("Height","5","A");
		iSimpleGraphManager.createFeature("Height","10,5","B");
		iSimpleGraphManager.createFeature("Height","12","C");
		iSimpleGraphManager.createFeature("Height","12","D");
		
		iSimpleGraphManager.createFeature("Age","3","A");
		iSimpleGraphManager.createFeature("Age","3","B");
		iSimpleGraphManager.createFeature("Age","7","C");

		
		//relation created with nodes and relation names
		//which automatically creates a group tag with relation name
		iSimpleGraphManager.createRelation("Graph1","A","B");
		iSimpleGraphManager.createRelation("Graph1","A","C");
		
		iSimpleGraphManager.createRelation("Graph2","C","B");
		iSimpleGraphManager.createRelation("Graph2","A","D");
		iSimpleGraphManager.createRelation("Graph2","D","A");
				
		
		//group creation
		iSimpleGraphManager.createGroupRel("mygroup1","A");
		iSimpleGraphManager.createGroupRel("mygroup1","B");
		iSimpleGraphManager.createGroupRel("mygroup1","C");
		iSimpleGraphManager.createGroupRel("mygroup1","D");
		
		iSimpleGraphManager.createGroupRel("mygroup2","A");
		iSimpleGraphManager.createGroupRel("mygroup2","B");
		iSimpleGraphManager.createGroupRel("mygroup2","C");
		
		iSimpleGraphManager.createGroupRel("mygroup3","D");
		
		GroupFileDataStructure newGroup = new GroupFileDataStructure();
		newGroup.setFileName("testbulkgroup");
		newGroup.setNodes(new ArrayList<String>(Arrays.asList("A","B")));
		
		iSimpleGraphManager.createGroupBulk(newGroup);
		
		iSimpleGraphManager.doTask("AgeAnalizer");
		
		iSimpleGraphManager.createExperiment(
				"analisis1",
				"sirve para analizar el comportamiento de la edad y la estatura",
				new ArrayList<String>(Arrays.asList("mygroup2","mygroup3")),
				new ArrayList<String>(Arrays.asList("Age","Height")),
				"AgeAnalizer",
				"no query",
				"ageanalisisresult"
				);
		
		ExperimentRequestFileDataStructure data = iSimpleGraphManager.getExperimentData("analisis1");

		System.out.println(data);

		
		System.out.println();
		
		ExperimentResultsFileDataStructure ExpRes = iAnalisysService.processData(data);

		System.out.println();
		
		iSimpleGraphManager.integrateExperimentResult(ExpRes);

		
	}

	@Test
	@Ignore
	public void simpleGraphFailTest() throws Exception {

		iSimpleGraphManager.createGroupRel("mygroup15","A");
		iSimpleGraphManager.createGroupRel("mygroup15","A");

		iSimpleGraphManager.createFeature("Height12","12","C");
		iSimpleGraphManager.createFeature("Height12","12","C");
	}

}