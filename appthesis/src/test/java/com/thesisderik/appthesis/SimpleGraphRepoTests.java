package com.thesisderik.appthesis;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.interfaces.IAnalysisService;
import com.thesisderik.appthesis.interfaces.IGraphBuilder;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.persistence.graph.entities.Graph;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.GroupFileDataStructure;
import com.thesisderik.appthesis.rawgraphparser.RawGraphParser;
import com.thesisderik.appthesis.services.GraphBuilder;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimpleGraphRepoTests {

	
	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	

	@Autowired
	IAnalysisService iAnalisysService;
	

	@Test
	public void simpleLeadGraphTest() throws Exception {

		//create nodes
		iSimpleGraphManager.doNode("A");
		iSimpleGraphManager.doNode("B");
		iSimpleGraphManager.doNode("C");
		iSimpleGraphManager.doNode("D");
		
		//features

		iSimpleGraphManager.createFeature("Height","5","A");
		iSimpleGraphManager.createFeature("Height","10.5","B");
		iSimpleGraphManager.createFeature("Height","12","C");
		iSimpleGraphManager.createFeature("Height","12","D");
		
		iSimpleGraphManager.createFeature("Age","3","A");
		iSimpleGraphManager.createFeature("Age","3","B");
		iSimpleGraphManager.createFeature("Age","7","D");

		
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
		iSimpleGraphManager.createGroupRel("mygroup3","A");
		
		GroupFileDataStructure newGroup = new GroupFileDataStructure();
		newGroup.setFileName("testbulkgroup");
		newGroup.setNodes(new ArrayList<String>(Arrays.asList("A","B")));
		
		iSimpleGraphManager.createGroupBulk(newGroup);
		
		iAnalisysService.setExperimentDataIntegrator(iSimpleGraphManager);

		
		for(String serviceName : iAnalisysService.getServices())
			iSimpleGraphManager.doTask(serviceName);
		
		iSimpleGraphManager.createExperiment(
				"smilesbasic",
				"analizar y obtener la representacion SMILES de los datos",
				new ArrayList<String>(Arrays.asList("ALL")),
				new ArrayList<String>(Arrays.asList("NAME")),
				"SmilesCrawler",
				"s",
				"SMILES_PROPERTY"
				);
		

		
		iSimpleGraphManager.createExperiment(
				"statisticsbasic",
				"sirve para analizar estadisticamente algunas las variables",
				new ArrayList<String>(Arrays.asList("mygroup1","mygroup2","mygroup3")),
				new ArrayList<String>(Arrays.asList("Age","Height")),
				"Statistics",
				""
				);
		

		
		iSimpleGraphManager.createExperiment(
				"clusterbasic",
				"intenta hacer un clustering con las c",
				new ArrayList<String>(Arrays.asList("mygroup2","mygroup3")),
				new ArrayList<String>(Arrays.asList("Age","Height")),
				"Clustering",
				"2"
				);

		
		

		
		
		ExperimentRequestFileDataStructure data0 = iSimpleGraphManager.getExperimentData("smilesbasic");
		ExperimentRequestFileDataStructure data1 = iSimpleGraphManager.getExperimentData("statisticsbasic");
		ExperimentRequestFileDataStructure data2 = iSimpleGraphManager.getExperimentData("clusterbasic");



		System.out.println(data0);
		System.out.println(data1);
		System.out.println(data2);

		System.out.println();
		System.out.println();
		System.out.println(data0.buildCSVFile());
		System.out.println();
		System.out.println();
		System.out.println(data1.buildCSVFile());
		System.out.println();
		System.out.println();
		System.out.println(data2.buildCSVFile());
		System.out.println();
		System.out.println();

		
		
		System.out.println();
		
		iAnalisysService.processData(data0);
		iAnalisysService.processData(data1);
		iAnalisysService.processData(data2);

		
		ArrayList<String> arr = new ArrayList<>();
		
		arr.add("test_loader_file");
		
		File resource = new ClassPathResource("pendingfeatures/"+arr.get(0)+".ans").getFile();
		
		arr.add(new String(Files.readAllBytes(resource.toPath())));
		
		iAnalisysService.integrateFeaturesFile(arr);

		System.out.println();

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