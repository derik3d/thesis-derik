package com.thesisderik.appthesis;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppthesisApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testHello() throws Exception {
		this.mvc.perform(get("/idmanager/")).andExpect(status().isOk()).andExpect(content().string("hola"));
	}

	@Test
	public void test_kegg2pubchem_endpoint_good() throws Exception {
		this.mvc.perform(get("/idmanager/kegg2pubchem/?id=C00002")).andExpect(status().isOk()).andExpect(content().json("{\"query\":\"C00002\",\"result\":\"3304\"}"));
	}

	@Test
	public void test_kegg2pubchem_endpoint_bad() throws Exception {
		this.mvc.perform(get("/idmanager/kegg2pubchem/?id=badidtest")).andExpect(status().isBadRequest());
	}

	@Test
	public void test_bigg2kegg_endpoint_good() throws Exception {
		this.mvc.perform(get("/idmanager/bigg2kegg/?id=atp")).andExpect(status().isOk()).andExpect(content().json("{\"query\":\"atp\",\"result\":\"C00002\"}"));
	}

	@Test
	public void test_bigg2kegg_endpoint_bad() throws Exception {
		this.mvc.perform(get("/idmanager/bigg2kegg/?id=badidtest")).andExpect(status().isBadRequest());
	}

	@Test
	public void test_bigg2pubchem_endpoint_good() throws Exception {
		this.mvc.perform(get("/idmanager/bigg2pubchem/?id=atp")).andExpect(status().isOk()).andExpect(content().json("{\"query\":\"atp\",\"result\":\"3304\"}"));
	}

	@Test
	public void test_bigg2pubchem_endpoint_bad() throws Exception {
		this.mvc.perform(get("/idmanager/bigg2pubchem/?id=badidtest")).andExpect(status().isBadRequest());
	}

}