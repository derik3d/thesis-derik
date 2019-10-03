package com.thesisderik.appthesis.persistence.simplegraph.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.Mapping;

@Entity
@Table(
	name = "GRAPH_PLAIN_TASKS"
)
public class PlainTask {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME",  nullable = false, length = 200)
	private String name;
	
	
		
	@OneToMany(mappedBy="task")
	private Set<PlainExperiment> plainExperiments;

}
