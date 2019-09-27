package com.thesisderik.appthesis;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IdenditfiersDBTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void test_Hello() throws Exception {
		this.mvc.perform(get("/identifierspersist/")).andExpect(status().isOk()).andExpect(content().string("hola"));
	}

	

	@Test
	@Order(1)
	public void test_save_success() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.post("/identifierspersist/persist/pubchem/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"idpubchem\"}")).andExpect(status().isOk()).andExpect(content().json("{\"name\":\"idpubchem\"}"));
		
	}
	
/*
	@Test
	@Order(2)
	public void test_save_success_2() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.post("/identifierspersist/persist/pubchem/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"idpubchem\"}")).andExpect(status().isOk()).andExpect(content().json("{\"name\":\"idpubchem\"}"));
		
	}

	/*
	@Test
	public void testHello() throws Exception {
		this.mvc.perform(get("/identifierspersist/")).andExpect(status().isOk()).andExpect(content().string("hola"));
	}

	@Test
	public void test_bigg2kegg_endpoint_bad() throws Exception {
		this.mvc.perform(get("/identifierspersist/getByName/pubchem/")).andExpect(status().isBadRequest());
	}
*/


}