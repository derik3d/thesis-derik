package com.thesisderik.appthesis.processservices;
import padeldescriptor.PaDELDescriptorApp;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.persistence.simplegraph.datastructure.ExperimentResultsFileDataStructure;

@Service
public class QSARProcessService extends BaseProcessService{
	
	static Set<String> failed;
	
	static {
		failed = new HashSet<>();
	}

    @Autowired
    private TaskExecutor taskExecutor;
	
    
	public String getServiceName(){
		return  "QSAR";
	}

	
	public ResultFormat setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames, String dataFileName) {
		
		ArrayList<Integer> ignoreList = new ArrayList<>();
		ArrayList<String> resultsTags = new ArrayList<>();
		ArrayList<ArrayList<String>> result = new ArrayList<>();
	
		String currName = null;
		
		int max=dataForEveryInstance.size();
		

		for(int i = 0; i<dataForEveryInstance.size();i++) {
			
			System.out.print(i+" "+max);
			if(i<max) {
				StringBuilder dataToAnalize = new StringBuilder();
	
				
				if(failed.contains(dataForEveryInstance.get(i).get(0))) {
					result.add(null);
					ignoreList.add(i);
					continue;
				}
				
				
				
				
				currName = dataForEveryInstance.get(i).get(0);
				dataToAnalize.append(dataForEveryInstance.get(i).get(1) + "\t" + dataForEveryInstance.get(i).get(0) + "\n");
			
			
			
					//////////////////////////////////HERE
				StringBuilder sb = processSmilesEntry(args,currName,dataToAnalize.toString());
		
				if(sb==null) {
	
					result.add(null);
					ignoreList.add(i);
					continue;
				}
		        
		        ArrayList<String> arrFile = new ArrayList<>();
		        arrFile.add("noname");
		        arrFile.add(sb.toString());
		
		        
		        ExperimentResultsFileDataStructure dataStructure = fileToStructure(arrFile);
		        	
				//////////////////////////////////HERE
	
	
		        if(resultsTags.size()==0)
		        	resultsTags.addAll(dataStructure.getFirstRow().subList(1, dataStructure.getFirstRow().size()));
	        
				
				int size = dataStructure.getDataRows().get(0).size();
				
				List<String> subList = dataStructure.getDataRows().get(0).subList(1, size);
				
				ArrayList<String> ansArr = new ArrayList<String>();
				
		        ansArr.addAll(subList);
		        
				result.add(ansArr);
				
			}else {
	
				result.add(null);
				ignoreList.add(i);
				
			}
			
		}
        
	        
		
		return new ResultFormat(result, resultsTags, ignoreList);
		
	}
	
	
	public StringBuilder processSmilesEntry(String args, String currName, String dataToAnalize){
		

		System.out.println(dataToAnalize.toString());

		
		try {
			Thread.sleep(1000);

			
		File input = new ClassPathResource("datapadel/"+args+"input.smi").getFile();

		PrintWriter prw= new PrintWriter(input);
	    prw.print(dataToAnalize.toString());          
		prw.close();
		Thread.sleep(500);

	    
		File output = new ClassPathResource("datapadel/"+args+"output.csv").getFile();
		

		PrintWriter prwout= new PrintWriter(output);
		prwout.print("");          
		prwout.close();
		Thread.sleep(500);

		class CustomThread extends Thread {
			
			boolean finished = false;
			
			public void run() {
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

				System.out.println("oki");
				try {

					PaDELDescriptorApp application = PaDELDescriptorApp.getApplication();
					application.launchCommandLine(cmdArgs);
					System.out.println("doki");
					
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
				System.out.println("doooki");
				finished = true;
			}
			
		};
		
		
		CustomThread myThread = new CustomThread();
		
		System.out.println("ThreadLauched");

		taskExecutor.execute(myThread);
		
		int waitSeconds=30 , currSeconds=0;
		while(waitSeconds>currSeconds) {
			++currSeconds;
			Thread.sleep(1000);
			if(myThread.finished) {
				System.out.println("breaking by finishing");
				break;
			}
		}
		
		if(!myThread.finished) {
			System.out.println("interrupting");
			myThread.interrupt();
			failed.add(currName);
			return null;
		}else {
			System.out.println("finished");
		}
		

		Thread.sleep(500);
		StringBuilder sb = new StringBuilder();
        
        FileReader fr = 
        	      new FileReader(output); 
        	  
        	    int value;
        	    while ((value=fr.read()) != -1) 
        	    	sb.append((char) value); 
		
        	    

        System.out.println(output);
        System.out.println(sb.toString());
        	   
        
	    return sb;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
}
