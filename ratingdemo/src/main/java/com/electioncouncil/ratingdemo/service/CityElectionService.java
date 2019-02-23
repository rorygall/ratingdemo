package com.electioncouncil.ratingdemo.service;

import java.util.List;

import com.electioncouncil.ratingdemo.entity.Citizen;
import com.electioncouncil.ratingdemo.entity.Contender;
import com.electioncouncil.ratingdemo.entity.Idea;
import com.electioncouncil.ratingdemo.entity.Manifesto;
import com.electioncouncil.ratingdemo.entity.Person;
import com.electioncouncil.ratingdemo.entity.Rating;

public interface CityElectionService {
	public List<Person> findAllPeople();
	
	public Person findPersonById(int theId);
	
	public Citizen findCitizenById(int theId);
	
	public Contender findContenderById(int theId);
	
	public void savePerson(Person thePerson);
	
	public void deletePersonById(int theId);

	public Manifesto getContenderManifesto(int contenderId);

	public Idea findIdeaById(int theId);
	
	public void saveIdea(Idea theIdea);

	void deleteRating(Rating rating);
	
	public List<Rating> findAllRatings();

	public void nominateContender(int citizenId);

	public void addContenderManifesto(int contenderId, String synopsis);

	public void addIdeaToManifesto(int contenderId, String ideaTitle, String ideaDescription);

	public void rateIdea(int citizenId, int ideaId, int ratingValue);

	public void removeRating(int citizenId, int ratingId);

	public Person determinElectionWinner();


}
