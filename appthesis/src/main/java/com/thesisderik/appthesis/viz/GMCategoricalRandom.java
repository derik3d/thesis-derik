package com.thesisderik.appthesis.viz;

import java.awt.Color;
import java.util.stream.Stream;

public class GMCategoricalRandom extends GMCategorical {

	GMCategoricalRandom(Stream<String> values, Color bottomColor, Color topColor) {
		super(values, bottomColor, topColor);
	}

	@Override
	public String processValue(String value) {

		Color color = consistentRandomColor(value);
		
		return colorString(color);
	}

}
