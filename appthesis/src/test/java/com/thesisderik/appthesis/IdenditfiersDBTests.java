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

import com.thesisderik.appthesis.interfaces.INamesIdentifiersService;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	

	

	@Test
	public void test_interface__consult_error() throws Exception {
		
		Optional<PubchemIdentifier> pubchemIdentifierByName = iNamesIdentifiersService.getPubchemIdentifierByName("idtest4");
		assertFalse(pubchemIdentifierByName.isPresent());
		

	}
	

}