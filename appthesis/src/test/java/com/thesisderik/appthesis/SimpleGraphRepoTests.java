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

import com.thesisderik.appthesis.idscrawler.entities.ResponseData;
import com.thesisderik.appthesis.interfaces.IAnalysisService;
import com.thesisderik.appthesis.interfaces.IGraphBuilder;
import com.thesisderik.appthesis.interfaces.INamesCrawlerService;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.persistence.graph.entities.Graph;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.GroupFileDataStructure;
import com.thesisderik.appthesis.rawgraphparser.RawGraphParser;
import com.thesisderik.appthesis.services.GraphBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class SimpleGraphRepoTests {

	

	@Autowired
	private INamesCrawlerService iNamesCrawlerService;
	
	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	

	@Autowired
	IAnalysisService iAnalysisService;
	

	@Test
	public void simpleLeadGraphTest() throws Exception {

		//create nodes
		iSimpleGraphManager.doNode("A");
		iSimpleGraphManager.doNode("B");
		iSimpleGraphManager.doNode("C");
		iSimpleGraphManager.doNode("D");
		iSimpleGraphManager.doNode("3527");
		iSimpleGraphManager.doNode("3530");
		//iSimpleGraphManager.doNode("3304");
		
		//features

		iSimpleGraphManager.createFeature("Height","5","A");
		iSimpleGraphManager.createFeature("Height","10.5","B");
		iSimpleGraphManager.createFeature("Height","12","C");
		iSimpleGraphManager.createFeature("Height","12","D");
		
		iSimpleGraphManager.createFeature("Age","3","A");
		iSimpleGraphManager.createFeature("Age","3","B");
		iSimpleGraphManager.createFeature("Age","7","D");
		
		

		iSimpleGraphManager.createFeature("OLDAGE","30","A");
		iSimpleGraphManager.createFeature("OLDAGE","33","B");

		
		//madeUprelations to crawl letters data
		iSimpleGraphManager.createRelation("testMadeUpRel","3530","A");
		iSimpleGraphManager.createRelation("testMadeUpRel","3530","B");
		iSimpleGraphManager.createRelation("testMadeUpRel","3527","B");
		iSimpleGraphManager.createRelation("testMadeUpRel","3527","C");
		

		

		iSimpleGraphManager.createGroupRel("testMadeUp","3530");
		iSimpleGraphManager.createGroupRel("testMadeUp","3527");
		
		
		//relation created with nodes and relation names
		//which automatically creates a group tag with relation name
		iSimpleGraphManager.createRelation("Graph1","A","B");
		iSimpleGraphManager.createRelation("Graph1","A","C");
		
		iSimpleGraphManager.createRelation("Graph2","C","B");
		iSimpleGraphManager.createRelation("Graph2","A","D");
		iSimpleGraphManager.createRelation("Graph2","D","A");

		
		//iSimpleGraphManager.createRelation("GraphChem","3527","3530");
		//iSimpleGraphManager.createRelation("GraphChem","3527","3304");
		//iSimpleGraphManager.createRelation("GraphChem","3304","3530");
				
		
		//group creation

		
		
		iSimpleGraphManager.createGroupRel("chemnodes","3527");
		iSimpleGraphManager.createGroupRel("chemnodes","3530");
		//iSimpleGraphManager.createGroupRel("chemnodes","3304");
		
		
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
		
		

		
		for(String serviceName : iAnalysisService.getServices())
			iSimpleGraphManager.doTask(serviceName);
		
		
		
		System.out.println(iSimpleGraphManager.getPlainTasks());
		
		
		iSimpleGraphManager.createExperiment(
				"smilesbasic",
				"analizar y obtener la representacion SMILES de los datos",
				new ArrayList<String>(Arrays.asList("chemnodes")),
				new ArrayList<String>(Arrays.asList("NAME")),
				"SmilesCrawler",
				"",
				"SMILES_PROPERTY"
				);
		
		ExperimentRequestFileDataStructure data0 = iSimpleGraphManager.getExperimentData("smilesbasic");
		iAnalysisService.processData(data0);
		
		
		System.out.println("finished prev stage");
		
		iSimpleGraphManager.doNode("3304");

		
		iAnalysisService.processData(iSimpleGraphManager.getExperimentDataNotAnalized("smilesbasic"));

		

		
		iSimpleGraphManager.createExperiment(
				"qsarloaddescriptors",
				"sirve para cargar los descriptores qsar de padel",
				new ArrayList<String>(Arrays.asList("ALL")),
				new ArrayList<String>(Arrays.asList("NAME","RESULT_FT_SMILES_PROPERTY_PROPNAME_SMILES_DIM_1")),
				"QSAR",
				"2d",
				"QSAR_DESCR"
				);
		

		
		iSimpleGraphManager.createExperiment(
				"qsarloadfingerprints",
				"sirve para cargar los fingerprint qsar de padel",
				new ArrayList<String>(Arrays.asList("ALL")),
				new ArrayList<String>(Arrays.asList("NAME","RESULT_FT_SMILES_PROPERTY_PROPNAME_SMILES_DIM_1")),
				"QSAR",
				"fingerprints",
				"QSAR_FINGPR"
				);

		ExperimentRequestFileDataStructure data1 = iSimpleGraphManager.getExperimentData("qsarloaddescriptors");
		ExperimentRequestFileDataStructure data2 = iSimpleGraphManager.getExperimentData("qsarloadfingerprints");

		//iAnalysisService.processData(data1);
		//iAnalysisService.processData(data2);
		
		

		
		iSimpleGraphManager.createExperiment(
				"statisticsbasic",
				"sirve para analizar estadisticamente algunas las variables",
				new ArrayList<String>(Arrays.asList("mygroup1","mygroup2","mygroup3")),
				new ArrayList<String>(Arrays.asList("Age","Height")),
				"Statistics",
				""
				);
		

		
		iSimpleGraphManager.createExperiment(
				"mltest",
				"intenta hacer ML",
				new ArrayList<String>(Arrays.asList("mygroup2","mygroup3")),
				new ArrayList<String>(Arrays.asList("Age","Height")),
				"ML",
				"",
				""
				);
		
		
		
		
		ExperimentRequestFileDataStructure data3 = iSimpleGraphManager.getExperimentData("statisticsbasic");
		ExperimentRequestFileDataStructure data4 = iSimpleGraphManager.getExperimentData("mltest");

		
		iAnalysisService.processData(data3);

		//iAnalysisService.processData(data4);
		
		

		iSimpleGraphManager.createExperiment(
				"testmd",
				"test to build made up data",
				new ArrayList<String>(Arrays.asList("testMadeUp")),
				new ArrayList<String>(Arrays.asList("NAME")),
				"MUData",
				"",
				"MUD_PROP"
				);
		
		
		ExperimentRequestFileDataStructure dataM = iSimpleGraphManager.getExperimentData("testmd");
		iAnalysisService.processData(dataM);

		
		

		int a=0;
		System.out.println(a++);
	}
	
	@Test
	@Ignore
	public void integrateDataFromFileTest() throws IOException {

		ArrayList<String> arr = new ArrayList<>();
		
		arr.add("test_loader_file");
		
		File resource = new ClassPathResource("pendingfeatures/"+arr.get(0)+".ans").getFile();
		
		arr.add(new String(Files.readAllBytes(resource.toPath())));
		
		iAnalysisService.integrateFeaturesFile(arr);
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