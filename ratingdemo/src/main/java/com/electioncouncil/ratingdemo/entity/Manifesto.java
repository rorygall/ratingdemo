package com.electioncouncil.ratingdemo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
/**
 * <p>This class represents a hinernate Entity for the manifesto table</p>
 * <p>Tere is a 1 to 1 mapping to the person table
 * as well as a 1 to many mapping to the ideas table</p>
 * @author Rory
 *
 */
@Entity
@Table(name="manifesto")
public class Manifesto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="synopsis")
	private String synopsis;

	@OneToOne(mappedBy="manifesto", cascade=CascadeType.DETACH)
	private Person person;
	
	@OneToMany(mappedBy="manifesto", cascade=CascadeType.ALL)
	private List<Idea> ideaList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Idea> getIdeaList() {
		return ideaList;
	}

	public void setIdeaList(List<Idea> ideaList) {
		this.ideaList = ideaList;
	}
	
	public Manifesto() {}

	public void add(Idea idea)
	{
		if(ideaList == null)
		{
			ideaList = new ArrayList<Idea>();
		}
		
		ideaList.add(idea);
		
		idea.setManifesto(this);
	}

	@Override
	public String toString() {
		return "Manifesto [id=" + id + ", synopsis=" + synopsis + "]";
	}

	
	
	
	
}
