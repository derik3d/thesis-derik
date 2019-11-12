package com.thesisderik.appthesis.viz;

import java.awt.Color;

public class DMUniform extends ColorDataMapper {

	{
		name = "Uniform mapper for features";
		description = "Uses the defined range to map values";
	}
	
	@Override
	public Color processValue(Object value) {
		
		double valueParsed = Double.parseDouble((String)value);
		
		return new Color((int)(255*valueParsed),100,40);
	}

}
