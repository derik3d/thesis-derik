package com.thesisderik.appthesis.viz;

import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.Stream;


public abstract class GeneralMapper {
	

	Color bottomColor;
	Color topColor;
	
	GeneralMapper (Stream<String> values,Color bottomColor ,Color topColor){
		this.bottomColor = bottomColor;
		this.topColor = topColor;
		preProcessData(values);
	}
	
	public abstract void preProcessData(Stream<String> values);
	public abstract String processValue(String value);
	

	static float lerp(float bottomBound, float topBound, double normValue) {
		
		float range = topBound - bottomBound;
		float ext = (float) (range*normValue);
		
		
		return bottomBound + ext;
	}

	static double normRange(double bottomBound, double topBound, double value) {
		
		double range = topBound - bottomBound;
		double res =  (value-bottomBound) / range;
		
		return res;
	}
	
	public static Color stringToColor(String source) {
		return Color.decode(source);
	}
	
	public static String colorString(Color c) {
		String rgb = Integer.toHexString(c.getRGB());
		rgb = rgb.substring(2, rgb.length());
		return "#"+rgb;
	}

	public static Color lerpColor(Color bottomColor, Color topColor, double normValue) {
		


		
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

			color.add(lerp(compBottom.get(i).floatValue(),compTop.get(i).floatValue(),normValue)) ;
			
		}
			
		Color res = new Color( color.get(0), color.get(1), color.get(2));

		
		return res;
	}
	

	
	public static Color rangeBasedColorLerp(double bottomBound, double topBound, Color bottomColor, Color topColor, double value) {

		double normValue = normRange(bottomBound, topBound, value);
		
		return lerpColor(bottomColor,topColor, normValue);

	
	}
	


	public Color consistentRandomColor(String value) {
		
		long hashCode= hash(value);
		

		int f0 = (int) bitExtracted(hashCode, 7, 0);
		int f1 = (int) bitExtracted(hashCode, 7, 8);
		int f2 = (int) bitExtracted(hashCode, 7, 16);
		
		
		
		Color color = new Color(reformatRange(f0),reformatRange(f1),reformatRange(f2));
		

		return color;
	}
	
	int reformatRange(int val) {
		
		return (int) ((val*0.7)+(255*0.27));
		
	}

	long bitExtracted(long number, int k, int p) 
	{ 
	    return (((1 << k) - 1) & (number >> (p))); 
	} 
	
	public static long hash(String string) {
		  long h = 1125899906842597L; // prime
		  int len = string.length();

		  for (int i = 0; i < len; i++) {
		    h = 31*h + string.charAt(i);
		  }
		  return h;
		}
	
}
