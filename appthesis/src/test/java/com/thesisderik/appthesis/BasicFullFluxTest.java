package com.thesisderik.appthesis;

import com.thesisderik.appthesis.interfaces.IAnalysisService;
import com.thesisderik.appthesis.interfaces.IGraphBuilder;
import com.thesisderik.appthesis.interfaces.IGraphManagerService;
import com.thesisderik.appthesis.interfaces.INamesIdentifiersService;
import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;
import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;
import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentRequestFileDataStructure;
import com.thesisderik.appthesis.repositories.KgmlRepositoryDao;
import com.thesisderik.appthesis.repositories.PubchemRepositoryDao;
import com.thesisderik.appthesis.repositories.SbmlRepositoryDao;
import com.thesisderik.appthesis.services.GraphBuilder;
import com.thesisderik.appthesis.services.NamesIdentifiersService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.rawgraphparser.RawGraphParser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class BasicFullFluxTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	IGraphManagerService iGraphManagerService;

	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	
	@Autowired
	IAnalysisService iAnalysisService;
	
	
	
    @Test
    public void test_graph_processing() throws Exception{


    	
    	//LOAD GRAPH

		//File file = ResourceUtils.getFile("classpath:testdata/biologicalsourcefiles/sbmlfiles/ecoli_core_model.xml");
		//File file = ResourceUtils.getFile("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00010.xml");
		//final InputStream inputStream = new FileInputStream(file);		
    	
    	//iGraphManagerService.processKgml(inputStream);
    	
    	
    	
    	
    	//LOAD PROCESS SERVICES

		for(String serviceName : iAnalysisService.getServices())
			iSimpleGraphManager.doTask(serviceName);
		
    	

		/*
		//LOAD SMILES
		
		iSimpleGraphManager.createExperiment(
				"smilesbasic",
				"analizar y obtener la representacion SMILES de los datos",
				new ArrayList<String>(Arrays.asList("GP_COMPOUND_NODE")),
				new ArrayList<String>(Arrays.asList("NAME")),
				"SmilesCrawler",
				"",
				"SMILES_PROPERTY"
				);
				
				*/
		
		//ExperimentRequestFileDataStructure data0 = iSimpleGraphManager.getExperimentData("smilesbasic");
		//iAnalysisService.processData(data0);
		
		
		
		
		//LOAD QSAR DATA
		/*
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
		
		*/
		
		
		
		//ExperimentRequestFileDataStructure data1 = iSimpleGraphManager.getExperimentData("qsarloaddescriptors");
		//iAnalysisService.processData(data1);


		ExperimentRequestFileDataStructure scallingqsar;
				
		scallingqsar =  iSimpleGraphManager.getExperimentDataNotAnalized("qsarloaddescriptors");

		while(scallingqsar.getDataRows().size()>0) {
			iAnalysisService.processData(scallingqsar);
			scallingqsar =  iSimpleGraphManager.getExperimentDataNotAnalized("qsarloaddescriptors");
		}


		ExperimentRequestFileDataStructure data2 = iSimpleGraphManager.getExperimentData("qsarloadfingerprints");
		iAnalysisService.processData(data2);
		
		
		scallingqsar =  iSimpleGraphManager.getExperimentDataNotAnalized("qsarloadfingerprints");

		while(scallingqsar.getDataRows().size()>0) {
			iAnalysisService.processData(scallingqsar);
			scallingqsar =  iSimpleGraphManager.getExperimentDataNotAnalized("qsarloadfingerprints");
		}
		
		
		//LOAD MD DATA

		iSimpleGraphManager.createExperiment(
				"testmd",
				"test to build made up data",
				new ArrayList<String>(Arrays.asList("GP_REACTION_NODE")),
				new ArrayList<String>(Arrays.asList("NAME")),
				"MUData",
				"",
				"MUD_PROP"
				);
		
		
		ExperimentRequestFileDataStructure dataM = iSimpleGraphManager.getExperimentData("testmd");
		iAnalysisService.processData(dataM);

		
		
		
		
		
		
		
		
		

		int a=0;
		System.out.println(a++);
    	
        System.out.println("roorrrooo");

    	
    }    
    
}
