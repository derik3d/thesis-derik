package com.thesisderik.appthesis;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.rawgraphparser.RawGraphParser;
import com.thesisderik.appthesis.services.GraphBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OutsideIdentifiersTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testHello() throws Exception {
		this.mvc.perform(get("/outsideidmanager/")).andExpect(status().isOk()).andExpect(content().string("hola"));
	}

	@Test
	public void test_kegg2pubchem_endpoint_good() throws Exception {
		this.mvc.perform(get("/outsideidmanager/kegg2pubchem/?id=C00002")).andExpect(status().isOk()).andExpect(content().json("{\"query\":\"C00002\",\"result\":\"3304\"}"));
	}

	@Test
	public void test_kegg2pubchem_endpoint_bad() throws Exception {
		this.mvc.perform(get("/outsideidmanager/kegg2pubchem/?id=badidtest")).andExpect(status().isBadRequest());
	}

	@Test
	public void test_bigg2kegg_endpoint_good() throws Exception {
		this.mvc.perform(get("/outsideidmanager/bigg2kegg/?id=atp")).andExpect(status().isOk()).andExpect(content().json("{\"query\":\"atp\",\"result\":\"C00002\"}"));
	}

	@Test
	public void test_bigg2kegg_endpoint_bad() throws Exception {
		this.mvc.perform(get("/outsideidmanager/bigg2kegg/?id=badidtest")).andExpect(status().isBadRequest());
	}

	@Test
	public void test_bigg2pubchem_endpoint_good() throws Exception {
		this.mvc.perform(get("/outsideidmanager/bigg2pubchem/?id=atp")).andExpect(status().isOk()).andExpect(content().json("{\"query\":\"atp\",\"result\":\"3304\"}"));
	}

	@Test
	public void test_bigg2pubchem_endpoint_bad() throws Exception {
		this.mvc.perform(get("/outsideidmanager/bigg2pubchem/?id=badidtest")).andExpect(status().isBadRequest());
	}

	
	
	
	@Test
	public void test_bigg2biocyc_endpoint_good() throws Exception {
		this.mvc.perform(get("/outsideidmanager/bigg2biocyc/?id=q8h2")).andExpect(status().isOk()).andExpect(content().json("{\"query\":\"q8h2\",\"result\":\"META:CPD-9956\"}"));
	}

	@Test
	public void test_bigg2biocyc_endpoint_bad() throws Exception {
		this.mvc.perform(get("/outsideidmanager/bigg2biocyc/?id=badidtest")).andExpect(status().isBadRequest());
	}

	@Test
	public void test_bigg2biocyc2pubchem_endpoint_good() throws Exception {
		this.mvc.perform(get("/outsideidmanager/bigg2biocyc2pubchem/?id=q8h2")).andExpect(status().isOk()).andExpect(content().json("{\"query\":\"q8h2\",\"result\":\"25074411\"}"));
	}

	@Test
	public void test_bigg2biocyc2pubchem_endpoint_bad() throws Exception {
		this.mvc.perform(get("/outsideidmanager/bigg2biocyc2pubchem/?id=badidtest")).andExpect(status().isBadRequest());
	}

	
}