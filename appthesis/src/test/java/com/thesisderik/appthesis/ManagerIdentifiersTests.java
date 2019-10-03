package com.thesisderik.appthesis;


import org.junit.Ignore;
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
import com.thesisderik.appthesis.interfaces.INamesIntegrator;
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
public class ManagerIdentifiersTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private INamesIntegrator iNamesIntegrator;

	
	
	
	@Test
	public void test_Hello() throws Exception {
		this.mvc.perform(get("/identifiersmanager/")).andExpect(status().isOk()).andExpect(content().string("hola"));
	}


	@Test
	public void test_interface_savedb_consult_kgml() throws Exception {
		
		Optional<PubchemIdentifier> pubchemIdentifierByName = iNamesIntegrator.processKgmlIdentifier("C00003",true);
		
		assertTrue(pubchemIdentifierByName.isPresent());
		assertEquals(pubchemIdentifierByName.get().getName(),"3305");
		
		Optional<PubchemIdentifier> result2 = iNamesIntegrator.processKgmlIdentifier("C00003",true);

		assertTrue(result2.isPresent());
		assertEquals(result2.get().getName(),"3305");
		

	}


	@Test
	public void test_interface_savedb_consult_kgml_error() throws Exception {
		
		Optional<PubchemIdentifier> pubchemIdentifierByName = iNamesIntegrator.processKgmlIdentifier("C000000error003",true);

		assertFalse(pubchemIdentifierByName.isPresent());		

	}


	@Test
	public void test_interface_savedb_consult_sbml() throws Exception {
		
		Optional<PubchemIdentifier> pubchemIdentifierByName = iNamesIntegrator.processSbmlIdentifier("atp",true);
		
		assertTrue(pubchemIdentifierByName.isPresent());
		assertEquals(pubchemIdentifierByName.get().getName(),"3304");
		
		Optional<PubchemIdentifier> result2 = iNamesIntegrator.processSbmlIdentifier("atp",true);

		assertTrue(result2.isPresent());
		assertEquals(result2.get().getName(),"3304");
		

	}


	@Test
	public void test_interface_savedb_consult_sbml_error() throws Exception {
		
		Optional<PubchemIdentifier> pubchemIdentifierByName = iNamesIntegrator.processSbmlIdentifier("atperroratp",true);
		
		assertFalse(pubchemIdentifierByName.isPresent());
		

	}
	
	
}