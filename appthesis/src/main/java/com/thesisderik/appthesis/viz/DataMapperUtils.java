package com.thesisderik.appthesis.viz;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class DataMapperUtils {
	
	public enum Mappers {
		NUMERICAL_UNIFORM("maps data between the colors numerically"),
		CATEGORICAL_UNIFORM("maps data between the colors numerically"),
		CATEGORICAL_RANDOM("");
		private String description;

		private Mappers(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
		
	}
	


	public static GeneralMapper getMapper(Stream<String> values, Color bottomColor ,Color topColor,Mappers mapper) {

		switch(mapper) {
		case NUMERICAL_UNIFORM:
			return new GMNumericalUniform(values, bottomColor, topColor);
		case CATEGORICAL_UNIFORM:
			return new GMCategoricalUniform(values, bottomColor, topColor);
		case CATEGORICAL_RANDOM:
			return new GMCategoricalRandom(values, bottomColor, topColor);
		default:
			return null;
		}
	}
	

}
