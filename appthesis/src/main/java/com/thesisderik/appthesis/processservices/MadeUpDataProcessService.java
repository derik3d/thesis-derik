package com.thesisderik.appthesis.processservices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisderik.appthesis.interfaces.ISimpleGraphManager;

@Service
public class MadeUpDataProcessService extends BaseProcessService{
	
	@Autowired
	ISimpleGraphManager iSimpleGraphManager;
	
	public String getServiceName(){
		return  "MUData";
	}
	
	
	public ResultFormat setData(String args, ArrayList<ArrayList<String>> dataForEveryInstance,ArrayList<String> featureNames, String dataFileName) {
		

		ArrayList<String> resultsTags = new ArrayList<>();
		ArrayList<Integer> ignoreList = new ArrayList<>();
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		
		
		Set<String> featuresToEnforce = new HashSet<>();
		
		Set<String> featuresToRemove = new HashSet<>();
		featuresToRemove.add("NAME");
		
		
		Map<String,Function<ArrayList<String>,String>> operators = new HashMap<>();
		
		Function<ArrayList<String>, String> add = arr -> {
			
			double ans = 0;
			
			for(String val : arr)
				ans+= Double.parseDouble(val);
			
			return ""+ans;
		};
		
		operators.put("ADD",add);
		
		Function<ArrayList<String>, String> top = arr -> {
			
			double ans = 0;
			
			for(String val : arr) {
				if(Double.parseDouble(val)>ans)
					ans=Double.parseDouble(val);
			}
			return ""+ans;
		};
		
		operators.put("TOP",top);
		
		
		
		ArrayList<ArrayList<Map<String,String>>> recolectedDataFromAllNodes = new ArrayList<>();
		
		for(ArrayList<String> dataOneInstance : dataForEveryInstance) {
			
			//fur now just need names and must be the only propertyy
			String nodeName = dataOneInstance.get(0);
			
			Set<String> relatedNodesNames = iSimpleGraphManager.getRelatedNodesForNodeByNodeName(nodeName);
			
			ArrayList<Map<String,String>> dataRecolectedForANode = new ArrayList<>();
			
			ArrayList<String> commonFeatures = null;
			
			for(String aNodeName : relatedNodesNames) {
				//prop -> data
				Map<String,String> propDataForInstance = iSimpleGraphManager.getMappedDataOfNodeByName(aNodeName);
				
				removeKeysFromMap(propDataForInstance, featuresToRemove);
				
				dataRecolectedForANode.add(propDataForInstance);
				
				if(commonFeatures instanceof Object) {
					commonFeatures.retainAll(propDataForInstance.keySet());
				}else {
					commonFeatures = new ArrayList<>();
					commonFeatures.addAll(propDataForInstance.keySet());
				}
				
				
				
			}
			
			final ArrayList<String> commonFeaturesFinal = new ArrayList<>();
			commonFeaturesFinal.addAll(commonFeatures);

			
			System.out.println(dataRecolectedForANode);
			
			//remove features not present on all nodes related to a node
			for(Map<String,String> propDataForRelatedNode :dataRecolectedForANode) {
				
				propDataForRelatedNode.keySet().removeIf(n ->{ return !commonFeaturesFinal.contains(n);} );
				
				for(Map.Entry<String, String> prop : propDataForRelatedNode.entrySet()) {
					if(!commonFeatures.contains(prop.getKey()))
						propDataForRelatedNode.remove(prop.getKey());
				}
				
			}
			
			recolectedDataFromAllNodes.add(dataRecolectedForANode);
			
		}
		
		

		//unify data with operators
		
		ArrayList<Map<String,String>> unifiedDataForEveryNode = new ArrayList<>();
		
		for(ArrayList<Map<String,String>> dataForANode : recolectedDataFromAllNodes) {
			
			Map<String,ArrayList<String>> dataAnStepCloser = new HashMap<>();
			
			//getting on every dim all the values of a feature
			for(Map<String,String> oneNodeData : dataForANode) {
				
				for(Map.Entry<String, String> nodeFeature : oneNodeData.entrySet()) {
				
					if(dataAnStepCloser.containsKey(nodeFeature.getKey())) {
						dataAnStepCloser.get(nodeFeature.getKey()).add(nodeFeature.getValue());
					}else{
						dataAnStepCloser.put(nodeFeature.getKey(), new ArrayList<>());
						dataAnStepCloser.get(nodeFeature.getKey()).add(nodeFeature.getValue());
					}
					
				}
			}
			
			//having a prop - > arrvals
			//appy operators
			
			Map<String,String> uniformDataForNode = new HashMap<>();
			
			for(Map.Entry<String,ArrayList<String>> dimDataForNode: dataAnStepCloser.entrySet()) {
				
				for(Map.Entry<String,Function<ArrayList<String>,String>> op : operators.entrySet()) {
					
					uniformDataForNode.put(""+dimDataForNode.getKey()+"_"+op.getKey(), op.getValue().apply(dimDataForNode.getValue()));
					
				}
				
			}
			
			unifiedDataForEveryNode.add(uniformDataForNode);
			
		}
		
		
		//to build the result array all nodes must have the same properties or ignore nodes with missing properties
		//ignore features or ignore nodes to enforce features
		
		//remove nodes that dont have the required propertiess
		
		for( int i = 0; i < unifiedDataForEveryNode.size(); i++) {
			Map<String,String> dataForNode = unifiedDataForEveryNode.get(i);
			boolean removeThis = false;
			for(String feature : featuresToEnforce) {
				if(!dataForNode.keySet().contains(feature)) {
					removeThis = true;
					break;
				}
			}
			if(removeThis) {
				unifiedDataForEveryNode.set(i, null);
			}
		}
		
		//find features shared for all, then remove the features not shared by all nodes
		
		Set<String> featuresShared = null; 
		
		for(Map<String,String> dataForNode : unifiedDataForEveryNode) {
			
			Set<String> currNodeFeat = dataForNode.keySet();
			
			if(featuresShared instanceof Object) {
				featuresShared.retainAll(currNodeFeat);
			}else {
				featuresShared = new HashSet<>();
				featuresShared.addAll(currNodeFeat);
			}
			
		}
		//features to really use
		resultsTags.addAll(featuresShared);
		
		//remove nodes not compilant
		
		for( int i = 0; i < unifiedDataForEveryNode.size(); i++) {
			Map<String,String> dataForNode = unifiedDataForEveryNode.get(i);
			
			if(dataForNode instanceof Object) {
				result.add(getDataOrderedByFeatures(resultsTags, dataForNode));
			}else {
				result.add(null);
				ignoreList.add(i);
			}
			
		}
		
		return new ResultFormat(result, resultsTags, ignoreList);
	}
	
	private void removeKeysFromMap(Map<String, String> aMap, Set<String> keysToRemove) {
		
		for(String rem : keysToRemove) {
			if(aMap.containsKey(rem))
				aMap.remove(rem);
		}
		
	}


	public ArrayList<String> getDataOrderedByFeatures(ArrayList<String> featuresShared, Map<String,String> dataForNode){
		
		ArrayList<String> res = new ArrayList<>();
		
		for(String feature : featuresShared) {
			res.add(dataForNode.get(feature));
		}
		
		return res;
	}
	
	
}
