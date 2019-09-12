package com.thesisderik.appthesis.graphparser;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.util.ResourceUtils;

import com.thesisderik.appthesis.entities.utilities.GraphBuilder;

public class GraphParser {

	
	public static void runtestSBML() {
		
		
		File file = null;
		

		try {
			file = ResourceUtils.getFile("classpath:testdata/biologicalsourcefiles/sbmlfiles/ecoli_core_model.xml");
			GraphBuilder.createGraph(GraphParser.readFileSBML(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	public static void runtestKGML() {
		
		
		File file = null;
		
		
		try {
			file = ResourceUtils.getFile("classpath:testdata/biologicalsourcefiles/kgmlfiles/hsa00010.xml");
			GraphBuilder.createGraph(GraphParser.readFileKGML(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("bad");
			e.printStackTrace();
		}
		
		
	}
	
	public static GraphSBML readFileSBML(File file) {
		
		try {
			JAXBContext jaxbContext  = JAXBContext.newInstance(GraphSBML.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			GraphSBML myGraph = (GraphSBML) jaxbUnmarshaller.unmarshal(file);
			System.out.println(myGraph);
			return myGraph;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
		return null;
	
	}
	
	public static GraphKGML readFileKGML(File file) {
		
		try {
			JAXBContext jaxbContext  = JAXBContext.newInstance(GraphKGML.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            GraphKGML myGraph = (GraphKGML) jaxbUnmarshaller.unmarshal(file);
            System.out.println(myGraph);
            return myGraph;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
		return null;
	
	}
	

}
