package com.thesisderik.appthesis;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.persistence.graph.utilities.GraphBuilder;
import com.thesisderik.appthesis.rawgraphparser.RawGraphParser;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FixedProcessTests {


	@Test
	public void testGraphReadingSBML() throws Exception {

		File file = null;
		file = ResourceUtils.getFile("classpath:testdata/biologicalsourcefiles/sbmlfiles/ecoli_core_model.xml");
		GraphBuilder.createGraph(RawGraphParser.readFileSBML(file));
	}

	@Test
	public void testGraphReadingKGML() throws Exception {

		File file = null;
		file = ResourceUtils.getFile("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00010.xml");
		GraphBuilder.createGraph(RawGraphParser.readFileKGML(file));
		
	}

}