package com.thesisderik.appthesis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thesisderik.appthesis.graphparser.GraphParser;

@SpringBootApplication
public class AppthesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppthesisApplication.class, args);
		//GraphParser.runtestKGML();
		//GraphParser.runtestSBML();
	}

	
	
}
