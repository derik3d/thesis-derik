package com.thesisderik.appthesis;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;
import com.thesisderik.appthesis.repositories.KgmlRepositoryDao;
import com.thesisderik.appthesis.repositories.PubchemRepositoryDao;
import com.thesisderik.appthesis.repositories.SbmlRepositoryDao;
import com.thesisderik.appthesis.services.GraphBuilder;
import com.thesisderik.appthesis.services.IGraphBuilder;
import com.thesisderik.appthesis.services.IGraphManagerService;
import com.thesisderik.appthesis.services.INamesIdentifiersService;
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
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GraphManagerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	IGraphManagerService iGraphManagerService;
	
    @Test
    public void test_graph_processing() throws Exception{

    	iGraphManagerService.processSbml("classpath:testdata/biologicalsourcefiles/sbmlfiles/ecoli_core_model.xml");
    	iGraphManagerService.processSbml("classpath:testdata/biologicalsourcefiles/sbmlfiles/e_coli_corefrombigg.xml");
    	//iGraphManagerService.processSbml("classpath:testdata/biologicalsourcefiles/sbmlfiles/recon2model.v02.xml");
  	
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00010.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00020.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00030.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00040.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00051.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00053.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00500.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00520.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00620.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00630.xml");
    	iGraphManagerService.processKgml("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00640.xml");
    	assertTrue(true);
    
    	
        System.out.println("roorrrooo");

    	
    }    
    
}
