package com.thesisderik.appthesis.viz;

import java.awt.Color;
import java.util.stream.Stream;

public class GMCategoricalUniform extends GMCategorical {

	GMCategoricalUniform(Stream<String> values, Color bottomColor, Color topColor) {
		super(values, bottomColor, topColor);
	}

	@Override
	public String processValue(String value) {
		
		Color color = rangeBasedColorLerp(0, options.size()-1, bottomColor, topColor, options.indexOf(value));
		
		return colorString(color);
	}

}
