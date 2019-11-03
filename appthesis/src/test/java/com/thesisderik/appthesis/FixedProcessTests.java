package com.thesisderik.appthesis;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.interfaces.IGraphBuilder;
import com.thesisderik.appthesis.persistence.graph.entities.Graph;
import com.thesisderik.appthesis.rawgraphparser.RawGraphParser;
import com.thesisderik.appthesis.services.GraphBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FixedProcessTests {

	
	@Autowired
	IGraphBuilder iGraphBuilder;
	
	
	@Test
	public void testGraphReadingSBML() throws Exception {

		File file = ResourceUtils.getFile("classpath:testdata/biologicalsourcefiles/sbmlfiles/ecoli_core_model.xml");
		final InputStream inputStream = new FileInputStream(file);		
		iGraphBuilder.loadSbml(inputStream);
	}

	@Test
	public void testGraphReadingKGML() throws Exception {

		File file = ResourceUtils.getFile("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00010.xml");
		final InputStream inputStream = new FileInputStream(file);		
		iGraphBuilder.loadKgml(inputStream);
		
	}

}