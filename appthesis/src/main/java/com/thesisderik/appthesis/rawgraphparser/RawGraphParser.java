package com.thesisderik.appthesis.rawgraphparser;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.entities.utilities.GraphBuilder;
import com.thesisderik.appthesis.rawgraphparser.entities.GraphKGML;
import com.thesisderik.appthesis.rawgraphparser.entities.GraphSBML;

public class RawGraphParser {
	
	public static GraphSBML readFileSBML(File file) throws Exception{
		
		JAXBContext jaxbContext  = JAXBContext.newInstance(GraphSBML.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		GraphSBML myGraph = (GraphSBML) jaxbUnmarshaller.unmarshal(file);
		return myGraph;
	
	}
	
	public static GraphKGML readFileKGML(File file) throws Exception {
		
		JAXBContext jaxbContext  = JAXBContext.newInstance(GraphKGML.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        GraphKGML myGraph = (GraphKGML) jaxbUnmarshaller.unmarshal(file);
        return myGraph;
	
	}
	

}
