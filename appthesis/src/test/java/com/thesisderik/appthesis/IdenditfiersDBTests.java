package com.thesisderik.appthesis;


import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.services.INamesIdentifiersService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IdenditfiersDBTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private INamesIdentifiersService iNamesIdentifiersService;

	@Test
	@Order(1)
	public void test_Hello() throws Exception {
		this.mvc.perform(get("/identifierspersist/")).andExpect(status().isOk()).andExpect(content().string("hola"));
	}

	

	@Test
	public void test_save_success() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.post("/identifierspersist/persist/pubchem/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"idtest1\"}")).andExpect(status().isOk()).andExpect(content().json("{\"name\":\"idtest1\"}"));
		
	}
	

	@Test
	public void test_save_consult() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.post("/identifierspersist/persist/pubchem/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"idtest2\"}")).andExpect(status().isOk()).andExpect(content().json("{\"name\":\"idtest2\"}"));
		
		
		this.mvc.perform(get("/identifierspersist/getByName/pubchem/?id=idtest2")).andExpect(status().isOk()).andExpect(content().json("{\"name\":\"idtest2\"}"));

	}
	

	

	@Test
	public void test_interface_save_consult() throws Exception {
		
		iNamesIdentifiersService.saveIdentifier(new PubchemIdentifier("idtest3"));
		Optional<PubchemIdentifier> pubchemIdentifierByName = iNamesIdentifiersService.getPubchemIdentifierByName("idtest3");
		assertTrue(pubchemIdentifierByName.isPresent());
		

	}
	
	
	
	/*

	@Test
	@Order(4)
	public void test_save_success3() throws Exception {
		ResultActions perform = this.mvc.perform(MockMvcRequestBuilders.post("/identifierspersist/persist/pubchem/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"idpubchem\"}"));
		System.out.println(perform.toString());
		
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