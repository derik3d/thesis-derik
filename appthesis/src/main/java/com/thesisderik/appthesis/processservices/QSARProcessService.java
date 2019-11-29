package com.thesisderik.appthesis.processservices;
import padeldescriptor.PaDELDescriptorApp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;


public class QSARProcessService extends BaseProcessService{
	
	
	public String getServiceName(){
		return  "QSAR";
	}

	
	public ResultFormat setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames, String dataFileName) {
		
		ArrayList<Integer> ignoreList = new ArrayList<>();
		ArrayList<String> resultsTags = new ArrayList<>();
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		try {
			File input = new ClassPathResource("datapadel/"+args+"input.smi").getFile();

			StringBuilder dataToAnalize = new StringBuilder();
			
			int max=1;//batches limit example 10
			
			if(dataForEveryInstance.size()<max)
				max = dataForEveryInstance.size();

			for(int i =0; i<max;i++) {

				dataToAnalize.append(dataForEveryInstance.get(i).get(1) + "\t" + dataForEveryInstance.get(i).get(0) + "\n");
			}
			
			PrintWriter prw= new PrintWriter(input);
		    prw.print(dataToAnalize.toString());          
			prw.close();
			Thread.sleep(500);

		    
			File output = new ClassPathResource("datapadel/"+args+"output.csv").getFile();
			
			String[] cmdArgs = {
					"-maxruntime",
					"30000",
					"-removesalt",
					"-"+args,
					"-dir",
					input.getPath(),
					"-file",
					output.getAbsolutePath(),
                    "-retainorder"
			};
			boolean error= false;
			try {
				PaDELDescriptorApp.getApplication().launchCommandLine(cmdArgs);
			}catch (Exception ex) {
				error= true;
				ex.printStackTrace();
			}
			Thread.sleep(500);
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
				
				if(i<max) {
				
					int size = dataStructure.getDataRows().get(i).size();
					List<String> subList = dataStructure.getDataRows().get(i).subList(1, size);
					ArrayList<String> ansArr = new ArrayList<String>();
			        ansArr.addAll(subList);
					result.add(ansArr);
				
				}else {
					result.add(null);

					ignoreList.add(i);
				}
			}
	        
	        
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return new ResultFormat(result, resultsTags, ignoreList);
		
	}
	
}
