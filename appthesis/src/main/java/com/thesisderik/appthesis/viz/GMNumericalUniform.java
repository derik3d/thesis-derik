package com.thesisderik.appthesis.viz;

import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.thesisderik.appthesis.viz.DataMapperUtils.Mappers;

public class GMNumericalUniform extends GeneralMapper{
	
	double bottomRange;
	double topRange;
	
	GMNumericalUniform(Stream<String> values, Color bottomColor, Color topColor) {
		super(values, bottomColor, topColor);
	}
	
	@Override
	public void preProcessData(Stream<String> stream) {
		
		ArrayList<Double> tempBounds = new ArrayList<>();
		tempBounds.add(Double.MAX_VALUE);
		tempBounds.add(Double.MIN_VALUE);
		
		
		Consumer<String> boundsFinder = value -> {

			double curr = Double.parseDouble(value);
			double storedBottom = tempBounds.get(0);
			double storedTop = tempBounds.get(1);
			
			if(curr<storedBottom)
				tempBounds.set(0,curr);
			if(curr>storedTop)
				tempBounds.set(1,curr);
			
		};
		
		stream.forEach(boundsFinder);

		bottomRange = tempBounds.get(0).doubleValue();
		topRange = tempBounds.get(1).doubleValue();
		
	}
	
	@Override
	public String processValue( String value) {
	
		return  colorString(rangeBasedColorLerp( bottomRange, topRange, bottomColor, topColor, Double.valueOf(value) ));
				
	}
	

}
