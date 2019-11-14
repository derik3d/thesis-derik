package com.thesisderik.appthesis.viz;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class GMCategorical extends GeneralMapper {
	
	ArrayList<String> options;

	GMCategorical(Stream<String> values, Color bottomColor, Color topColor) {
		super(values, bottomColor, topColor);
	}

	@Override
	public void preProcessData(Stream<String> stream) {
		
		options = new ArrayList<>();
		
		final Set<String> optionsSet = new TreeSet<>();

		Consumer<String> optionsFinder = value -> {

			optionsSet.add(value);
			
		};
		
		stream.forEach(optionsFinder);
		options.addAll(optionsSet);

	}

}
