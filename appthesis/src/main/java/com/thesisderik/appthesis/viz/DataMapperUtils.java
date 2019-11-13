package com.thesisderik.appthesis.viz;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class DataMapperUtils {
	
	public enum Mappers {
		UNIFORM_FLOAT("maps data between the colors");
		
		private String description;

		private Mappers(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
		
	}
	
	public static String colorString(Color c) {
		String rgb = Integer.toHexString(c.getRGB());
		rgb = rgb.substring(2, rgb.length());
		return "#"+rgb;
	}

	public static String processValue(String bottomRange, String topRange, Color bottomColor ,Color topColor, String value, Mappers mapper) {
		
		switch(mapper) {
		
		case UNIFORM_FLOAT:
			return  colorString(uniformFloatFunc( Double.valueOf(bottomRange), Double.valueOf(topRange), bottomColor, topColor, Double.valueOf(value) ));
		default:
			return colorString(Color.BLACK);
		
		}
		
	}
	
	public static Color uniformFloatFunc(double bottomBound, double topBound, Color bottomColor, Color topColor, double value) {
		

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(bottomBound);
		System.out.println(topBound);
		System.out.println(bottomColor);
		System.out.println(topColor);
		System.out.println(value);

		double normValue = normRange(bottomBound, topBound, value);

		System.out.println(normValue);
		System.out.println();
		System.out.println();
		
		
		
		ArrayList<Float> compBottom = new ArrayList<>();
		compBottom.add(((float)bottomColor.getRed())/256f);
		compBottom.add(((float)bottomColor.getGreen())/256f);
		compBottom.add(((float)bottomColor.getBlue())/256f);
		ArrayList<Float> compTop = new ArrayList<>();
		compTop.add(((float)topColor.getRed())/256f);
		compTop.add(((float)topColor.getGreen())/256f);
		compTop.add(((float)topColor.getBlue())/256f);

		ArrayList<Float> color = new ArrayList<>();
		for(int i=0; i<3 ; i++) {

			System.out.println("comptopbot:" +compBottom.get(i));
			System.out.println("comptoptop:" +compTop.get(i));
			color.add(lerp(compBottom.get(i).floatValue(),compTop.get(i).floatValue(),normValue)) ;
			System.out.println("comptop:" +color.get(i));	
			System.out.println();	
			
		}
				
		Color res = new Color( color.get(0), color.get(1), color.get(2));

		System.out.println(res);
		System.out.println(normValue);
		System.out.println();
		
		return res;
	}

	private static float lerp(float bottomBound, float topBound, double normValue) {
		
		
		
		
		float range = topBound - bottomBound;
		float ext = (float) (range*normValue);
		

		System.out.println(range);
		System.out.println(ext);
		
		return bottomBound + ext;
	}

	private static double normRange(double bottomBound, double topBound, double value) {
		
		double range = topBound - bottomBound;
		double res =  (value-bottomBound) / range;
		
		return res;
	}
	
	public static Color stringToColor(String source) {
		return Color.decode(source);
	}
	
	

}
