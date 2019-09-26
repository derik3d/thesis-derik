package com.thesisderik.appthesis;

import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.repositories.KgmlRepositoryDao;
import com.thesisderik.appthesis.repositories.PubchemRepositoryDao;
import com.thesisderik.appthesis.repositories.SbmlRepositoryDao;
import com.thesisderik.appthesis.services.INamesIdentifiersService;
import com.thesisderik.appthesis.services.NamesIdentifiersService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersistenceTest {
	
	
	
	@Autowired
	PubchemRepositoryDao pubchemRepository;
	
	@Autowired
	KgmlRepositoryDao kgmlRepository;
	
	@Autowired
	SbmlRepositoryDao sbmlRepository;
	
	@Autowired
	INamesIdentifiersService iNamesIdentifiersService;

    @Test
    public void testFindByName() throws InterruptedException {

    	
    	pubchemRepository.save(new PubchemIdentifier("Bratislava"));

    	Optional<PubchemIdentifier> data = pubchemRepository.findByName("Bratislava");
     	
    	
        assertThat(data.get()).extracting(PubchemIdentifier::getName).asString().isEqualTo("Bratislava");
        
        
    }
/*
    @Test
    public void testInterface() throws InterruptedException {
    	
    	PubchemIdentifier pubchemIdentifier= new PubchemIdentifier();
    	
    	pubchemIdentifier.setName("pubchemid1");

		iNamesIdentifiersService.saveIdentifier(pubchemIdentifier);
    	
    	pubchemIdentifier= new PubchemIdentifier();
    	
    	pubchemIdentifier.setName("pubchemid2");

		iNamesIdentifiersService.saveIdentifier(pubchemIdentifier);
		
		Optional<PubchemIdentifier> data1 = iNamesIdentifiersService.getPubchemIdentifierByName("pubchemid1");
		
        assertThat(data1.get()).extracting(PubchemIdentifier::getId).asString().isEqualTo(1);
        assertThat(data1.get()).extracting(PubchemIdentifier::getName).asString().isEqualTo("pubchemid1");


		
		Optional<PubchemIdentifier> data2 = iNamesIdentifiersService.getPubchemIdentifierByName("pubchemid2");
		
        assertThat(data2.get()).extracting(PubchemIdentifier::getId).asString().isEqualTo(2);
        assertThat(data2.get()).extracting(PubchemIdentifier::getName).asString().isEqualTo("pubchemid2");


        
    }
    
    */
    
}
