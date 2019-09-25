package com.thesisderik.appthesis;

import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.repositories.KgmlRepositoryDao;
import com.thesisderik.appthesis.repositories.PubchemRepositoryDao;
import com.thesisderik.appthesis.repositories.SbmlRepositoryDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersistenceTest {

	@Autowired
	PubchemRepositoryDao pubchemRepository;
	
	@Autowired
	KgmlRepositoryDao kgmlRepository;
	
	@Autowired
	SbmlRepositoryDao sbmlRepository;

    @Test
    public void testFindByName() throws InterruptedException {

    	
    	pubchemRepository.save(new PubchemIdentifier("Bratislava"));
    	pubchemRepository.save(new PubchemIdentifier("Budapest"));

    	PubchemIdentifier data = pubchemRepository.findByName("Bratislava");
     	
        assertThat(data).extracting(PubchemIdentifier::getName).asString().isEqualTo("Bratislava");
        
        
    }
}
