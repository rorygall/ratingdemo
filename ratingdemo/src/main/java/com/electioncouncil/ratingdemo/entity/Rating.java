package com.electioncouncil.ratingdemo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="rate")
public class Rating {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="rating")
	private int rating;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.MERGE, CascadeType.DETACH})
	@JoinColumn(name="id_person")
	@JsonIgnore
	private Person person;

	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.MERGE, CascadeType.DETACH})
	@JoinColumn(name="id_idea")
	@JsonIgnore
	private Idea idea;

	public Rating() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	
	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", rating=" + rating + ", person=" + person + "]";
	}

	
}
