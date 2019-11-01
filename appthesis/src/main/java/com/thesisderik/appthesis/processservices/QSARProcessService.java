package com.thesisderik.appthesis.processservices;
import padeldescriptor.PaDELDescriptorApp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;


public class QSARProcessService extends BaseProcessService{
	
	public static final String serviceName = "QSAR";
	
	public static ArrayList<String> resultsTags = new ArrayList<>();
	
	ArrayList<ArrayList<String>> result = new ArrayList<>();

	

	public String getServiceName(){
		return serviceName;
	}
	public ArrayList<String> getFeaturesNames(){
		return resultsTags;
	}
	public ArrayList<ArrayList<String>> getResult() {
		return result;
	}
	
	
	
	public void setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames, String dataFileName) {
		

		
		
		try {
			File input = new ClassPathResource("datapadel/"+args+"input.smi").getFile();

			StringBuilder dataToAnalize = new StringBuilder();

			for(int i =0; i<dataForEveryInstance.size();i++) {

				dataToAnalize.append(dataForEveryInstance.get(i).get(1) + "\t" + dataForEveryInstance.get(i).get(0) + "\n");
			}
			
			PrintWriter prw= new PrintWriter(input);
		    prw.print(dataToAnalize.toString());          
			prw.close();
			Thread.sleep(500);

		    
			File output = new ClassPathResource("datapadel/"+args+"output.csv").getFile();
			
			String[] cmdArgs = {
					"-maxruntime",
					"-1",
					"-"+args,
					"-dir",
					input.getPath(),
					"-file",
					output.getAbsolutePath(),
                    "-retainorder"
			};
			
	        PaDELDescriptorApp.getApplication().launchCommandLine(cmdArgs);
			
	        StringBuilder sb = new StringBuilder();
	        
	        FileReader fr = 
	        	      new FileReader(output); 
	        	  
	        	    int value;
	        	    while ((value=fr.read()) != -1) 
	        	    	sb.append((char) value); 
	        
	        ArrayList<String> arrFile = new ArrayList<>();
	        arrFile.add("noname");
	        arrFile.add(sb.toString());
	        
	        ExperimentResultsFileDataStructure dataStructure = fileToStructure(arrFile);
	        	
	        
	        resultsTags.addAll(dataStructure.getFirstRow().subList(1, dataStructure.getFirstRow().size()));
	        
			for(int i =0; i<dataForEveryInstance.size();i++) {
		        
				result.add(new ArrayList<String>(dataStructure.getDataRows().get(i).subList(1, dataStructure.getDataRows().get(i).size())));
			}
	        
	        
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
	}
	
}
