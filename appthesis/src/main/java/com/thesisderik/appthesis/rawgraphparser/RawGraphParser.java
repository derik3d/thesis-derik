package com.thesisderik.appthesis.rawgraphparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.rawgraphparser.entities.GraphKGML;
import com.thesisderik.appthesis.rawgraphparser.entities.GraphSBML;
import com.thesisderik.appthesis.services.GraphBuilder;

public class RawGraphParser {
	
	public static GraphSBML readFileSBML(InputStream inputStream) throws Exception{
		
		JAXBContext jc = JAXBContext.newInstance(GraphSBML.class);
		XMLInputFactory xif = XMLInputFactory.newFactory();
		xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false); // this is the magic line
		StreamSource source = new StreamSource(inputStream);
		XMLStreamReader xsr = xif.createXMLStreamReader(source);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		GraphSBML myGraph = (GraphSBML) unmarshaller.unmarshal(xsr);
		
		
		
		return myGraph;
	
	}
	
	public static GraphKGML readFileKGML(InputStream inputStream) throws Exception {
		
		JAXBContext jc = JAXBContext.newInstance(GraphKGML.class);
		XMLInputFactory xif = XMLInputFactory.newFactory();
		xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false); // this is the magic line
		StreamSource source = new StreamSource(inputStream);
		XMLStreamReader xsr = xif.createXMLStreamReader(source);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		GraphKGML myGraph = (GraphKGML) unmarshaller.unmarshal(xsr);
        
        
        return myGraph;
	
	}
	

}
