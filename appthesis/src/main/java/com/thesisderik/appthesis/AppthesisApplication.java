package com.thesisderik.appthesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thesisderik.appthesis.rawgraphparser.RawGraphParser;

@SpringBootApplication
public class AppthesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppthesisApplication.class, args);
		RawGraphParser.runtestKGML();
		RawGraphParser.runtestSBML();
	}

	
	
}
