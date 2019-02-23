package com.electioncouncil.ratingdemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.electioncouncil.ratingdemo.entity.Idea;
import com.electioncouncil.ratingdemo.entity.Person;
import com.electioncouncil.ratingdemo.entity.Rating;
/**
 * <p>This class is the Hibernate implementation of the DAO for the election</p>
 * @author Rory
 *
 */
@Repository
public class ElectionDaoHibernateImpl implements ElectionDao {

	// Entity Manager
	@Autowired
	EntityManager entityManager;
	
	// constructor injection
	@Autowired
	public ElectionDaoHibernateImpl(EntityManager theEntityManager)
	{
		entityManager = theEntityManager;
	}
	
	@Override
	public List<Person> findAllPeople() {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// create a query
		Query<Person> thePersonQuery = currentSession.createQuery("from Person", Person.class);
		
		// execute query
		List<Person> allPeople = thePersonQuery.getResultList();
		
		// return result
		return allPeople;
	}

	@Override
	public Person findPersonById(int theId) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// Get Person
		Person person = currentSession.get(Person.class, theId);
		
		// Return the person
		return person;
	}

	@Override
	public void savePerson(Person thePerson) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		Transaction tx = currentSession.getTransaction();
		tx.begin();
		// Save the person to DB
		currentSession.saveOrUpdate(thePerson);
		tx.commit();
	}

	@Override
	public void deletePersonById(int theId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Idea findIdeaById(int theId) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// Get Person
		Idea idea = currentSession.get(Idea.class, theId);
		
		// Return the person
		return idea;
	}
	
	@Override
	public void saveIdea(Idea theIdea) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		Transaction tx = currentSession.getTransaction();
		tx.begin();
		// Save the person to DB
		currentSession.saveOrUpdate(theIdea);
		tx.commit();
	}

	public Rating findRatingById(int theId) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// Get Person
		Rating rating = currentSession.get(Rating.class, theId);
		
		// Return the person
		return rating;
	}

	@Override
	public List<Rating> findAllRatings() {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// create a query
		Query<Rating> theRatingQuery = currentSession.createQuery("from Rating", Rating.class);
		
		// execute query
		List<Rating> allRatings = theRatingQuery.getResultList();
		
		// return result
		return allRatings;
	}

	@Override
	public void deleteRating(Rating rating) {
		Session currentSession = entityManager.unwrap(Session.class);
		Transaction tx = currentSession.getTransaction();
		tx.begin();
		// Save the person to DB
		currentSession.delete(rating);
		tx.commit();
	}

}
