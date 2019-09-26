package com.thesisderik.appthesis;

import com.thesisderik.appthesis.persistence.identifiers.entities.KgmlIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.PubchemIdentifier;
import com.thesisderik.appthesis.persistence.identifiers.entities.SbmlIdentifier;
import com.thesisderik.appthesis.repositories.KgmlRepositoryDao;
import com.thesisderik.appthesis.repositories.PubchemRepositoryDao;
import com.thesisderik.appthesis.repositories.SbmlRepositoryDao;
import com.thesisderik.appthesis.services.INamesIdentifiersService;
import com.thesisderik.appthesis.services.NamesIdentifiersService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersistenceTest {
	
	@Autowired
	PubchemRepositoryDao pubchemRepositoryDao;
	
	@Autowired
	KgmlRepositoryDao kgmlRepositoryDao;
	
	@Autowired
	SbmlRepositoryDao sbmlRepositoryDao;
	
    @Test
    public void testFindByName(){

    	
    	sbmlRepositoryDao.save(new SbmlIdentifier("sbmlid",(new KgmlIdentifier("kgmlId", new PubchemIdentifier( "pubchemId" )))));
    	

    	Optional<SbmlIdentifier> datasbml = sbmlRepositoryDao.findByName("sbmlid");
     	
        assertThat(datasbml.get()).extracting(SbmlIdentifier::getName).asString().isEqualTo("sbmlid");

    	Optional<KgmlIdentifier> datagkml = kgmlRepositoryDao.findByName("kgmlId");
     	
        assertThat(datagkml.get()).extracting(KgmlIdentifier::getName).asString().isEqualTo("kgmlId");

    	Optional<PubchemIdentifier> datapubchem = pubchemRepositoryDao.findByName("pubchemId");
     	
        assertThat(datapubchem.get()).extracting(PubchemIdentifier::getName).asString().isEqualTo("pubchemId");
        
        
    }    
    
}
