package com.electioncouncil.ratingdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.electioncouncil.ratingdemo.dao.ElectionDao;
import com.electioncouncil.ratingdemo.entity.Citizen;
import com.electioncouncil.ratingdemo.entity.CitizenImpl;
import com.electioncouncil.ratingdemo.entity.Contender;
import com.electioncouncil.ratingdemo.entity.ContenderImpl;
import com.electioncouncil.ratingdemo.entity.Idea;
import com.electioncouncil.ratingdemo.entity.Manifesto;
import com.electioncouncil.ratingdemo.entity.Person;
import com.electioncouncil.ratingdemo.entity.Rating;
import com.electioncouncil.ratingdemo.rest.IdeaNotFoundException;
import com.electioncouncil.ratingdemo.rest.ManifestoNotFoundException;
import com.electioncouncil.ratingdemo.rest.PersonNotFoundException;

/**
 * <p>This class is the implementation of the service layer. 
 * It contains most business logic for the application</p>
 * @author Rory
 *
 */
@Service
public class CityElectionServieImpl implements CityElectionService {

	// our DAO object
	private ElectionDao electionDao;
	@Autowired
	public CityElectionServieImpl(ElectionDao theElectionDao)
	{
		electionDao = theElectionDao;
	}
	

	/**
	 * <p>Get All people in the system. Delegate to the DAO to get the People from storage.</p>
	 */
	@Override
	@Transactional
	public List<Person> findAllPeople() {
		
		List<Person> people = electionDao.findAllPeople();
		return people;
	}

	/**
	 * <p>Get a single person from the system. Delegate to the DAO to get the Person from storage.</p>
	 */
	@Override
	@Transactional
	public Person findPersonById(int theId) {
		return electionDao.findPersonById(theId);
	}

	/**
	 * <p>Save a person in the system. Delegate to the DAO to persist to storage.</p>
	 */
	@Override
	@Transactional
	public void savePerson(Person thePerson) {
		electionDao.savePerson(thePerson);
	}

	/**
	 * <p>Delete a person from the system. Delegate to the DAO to delete from storage.</p>
	 */
	@Override
	@Transactional
	public void deletePersonById(int theId) {
		electionDao.deletePersonById(theId);
	}

	/**
	 * <p>Find an idea by id from the system. Delegate to the DAO to find in storage.</p>
	 */
	@Override
	@Transactional
	public Idea findIdeaById(int theId) {
		return electionDao.findIdeaById(theId);
	}
	
	/**
	 * <p>Save an idea to the system. Delegate to the DAO to persist to storage.</p>
	 */
	@Override
	@Transactional
	public void saveIdea(Idea theIdea) {
		electionDao.saveIdea(theIdea);
	}

	/**
	 * <p>Save an idea to the system. Delegate to the DAO to persist to storage.</p>
	 */
	@Override
	@Transactional
	public void deleteRating(Rating rating){
		electionDao.deleteRating(rating);	
	}


	/**
	 * <p>Get All the ratings in the system. Delegate to DAO to retrieve from storage.</p>
	 */
	@Override
	public List<Rating> findAllRatings() {
		return electionDao.findAllRatings();
	}


	/**
	 * <p>Get a citizen from the system. A Citizen is a person without the contender flag set.</p>
	 * <p>This method gets a person with the passed in Id and Sets the person into a new CitizenImpl object</p>
	 */
	@Override
	public Citizen findCitizenById(int theId) {
		Citizen citizen = new CitizenImpl();
		
		Person person = findPersonById(theId);
		if(person != null && !person.isContender())
		{
			citizen.setPerson(person);
		}
		return citizen;
	}
	
	/**
	 * <p>Get a contender from the system. A Contender is a person with the contender flag set.</p>
	 * <p>This method gets a person with the passed in Id and Sets the person into a new ContenderImpl object</p>
	 */
	@Override
	public Contender findContenderById(int theId) {
		Contender contender = new ContenderImpl();
		
		Person person = findPersonById(theId);
		if(person != null && person.isContender())
		{
			contender.setPerson(person);
		}
		return contender;
	}

	/**
	 * <p>This method will convert a citizen into a contender</p>
	 * <p>For simplicity this is done using a flag.  Time permitting a better solution would be
	 * to have a list of roles associated with a person to indicate whether they are citizens, contenders or others.</p>
	 * <p>We could create a role table in the database and have a 1 to many mapping to it to allow multiple roles for a person</p>
	 */
	@Override
	public void nominateContender(int citizenId) {
		Citizen citizen = findCitizenById(citizenId);
		if( citizen == null )
			throw new RuntimeException("The citizen does not exist with id:" + citizenId);

		// Set as contender
		citizen.getPerson().setContender(true);
		
		// Save their new state
		savePerson(citizen.getPerson());
	}


	/**
	 * <p>This method will return the manfesto for the contender with the passed in ID.</p>
	 * <p>First it will try to find the contender. If successful it will create a Manifesto object
	 *   and place it into the Contender object. The contender object will then be saved which
	 *   will cascade the saving of the Manifesto also</p>
	 *  <p>If a manifesto already exists it will be overwritten</p>
	 */
	@Override
	public void addContenderManifesto(int contenderId, String synopsis) {

		Contender contender = findContenderById(contenderId);
		if( contender == null )
			throw new RuntimeException("The contender does not exist with id:" + contenderId);
		
		Manifesto manifesto = new Manifesto();
		manifesto.setSynopsis(synopsis);
		
		contender.getPerson().setManifesto(manifesto);
		
		savePerson(contender.getPerson());
		
	}

	/**
	 * <p>This method will get the Manifesto for a contender</p>
	 * <p>It will try to get the contender and return the
	 * manifesto from that object.</p>
	 */
	@Override
	public Manifesto getContenderManifesto(int contenderId) {
		Manifesto manifesto = null;
		
		Contender contender = findContenderById(contenderId);
		if( contender == null )
			throw new PersonNotFoundException("The contender does not exist with id:" + contenderId);
		
		try {
			manifesto = contender.getPerson().getManifesto();
		}catch (Exception e)
		{
			throw new ManifestoNotFoundException("The manifesto does not exist for contentder with id:" + contenderId);
		}
		
		return manifesto;
	}

	/**
	 * <p>This method will add an idea to a Manifesto</p>
	 * <p>If the manifesto already has 3 ideas it will throw and Exception</p>
	 * <p>The idea will get added to the ideas list for the manifesto and then
	 * 	it will be saved via a cascade when the contender is saved.</p>
	 * <p>The method will try to email the followers of the contender </p>
	 */
	@Override
	public void addIdeaToManifesto(int contenderId, String ideaTitle, String ideaDescription) {
		Contender contender = findContenderById(contenderId);
		if( contender == null )
			throw new PersonNotFoundException("The contender does not exist with id:" + contenderId);
		
		if(StringUtils.isEmpty(ideaTitle))
			throw new RuntimeException("Please provide a valid Idea Title");
		
		if(StringUtils.isEmpty(ideaDescription))
			throw new RuntimeException("Please provide a valid Idea Description");
		
		Manifesto manifesto = contender.getPerson().getManifesto();
		if(manifesto == null)
			throw new ManifestoNotFoundException("The manifesto does not exist for contentder with id:" + contenderId);
			
		List<Idea> ideaList = manifesto.getIdeaList();
		if(ideaList == null)
			ideaList = new ArrayList<Idea>();
		
		if(ideaList.size() >= 3)
			throw new RuntimeException("Sorry but you can only provide 3 ideas Max.");			
		
		Idea idea = new Idea();
		idea.setTitle(ideaTitle);
		idea.setDescription(ideaDescription);
		idea.setManifesto(manifesto);
		
		ideaList.add(idea);
		
		manifesto.setIdeaList(ideaList);
		
		savePerson(contender.getPerson());
		
		// EMAIL FOLLOWERS
		sendEmailToFollowers(contender.getPerson());
	}

	/**
	 * <p>This method will email all the followers of the contender. It will also email
	 * any followers of the recipients.</p>
	 *  
	 */
	public void sendEmailToFollowers(Person contender)
	{
		// TODO Still need to implement getting the followers from DB
		
		List<Person> followers = getContendersFollowers(contender);
		//post to all followers of this contender
		for(Person recipient: followers)
		{
			if(!StringUtils.isEmpty(recipient.getEmail()))
				System.out.println("Sending email to nextFollower:" + recipient.getEmail());

			// Check each recipient to see if they have any followers 
			// If they do then send an email to them also
			List<Person> followersRecipient = getContendersFollowers(recipient);
			for(Person recipientFollower: followersRecipient)
			{
				if(!StringUtils.isEmpty(recipientFollower.getEmail()))
					System.out.println("Sending email to recipientFollower:" + recipientFollower.getEmail());				
			}
		}
		
	}

	/**
	 * <p>This is not implemented correctly. due to time restrictions Ive just hardcoded a few entries </p>
	 * <p>If time permist best Solution is to have a database table with 2 columns for contender and follower. I think the
	 * many to many mapping in hibernate would work here</p>
	 * @param contender
	 * @return
	 */
	public List<Person> getContendersFollowers(Person contender)
	{
		List<Person> followers = new ArrayList<Person>();
		Person followerA = findPersonById(9);
		Person followerB = findPersonById(10);
		followers.add(followerA);
		followers.add(followerB);
		return followers;		
	}
	
	/**
	 * <p>This method rates an idea based on a citizenID for the person performing the rating, 
	 * the idea id to apply the rating to and the actual value for the rating<.p>
	 * <p>We will also perform some post processing via a call to performPostRatingActions</p>
	 */
	@Override
	public void rateIdea(int citizenId, int ideaId, int ratingValue) {
		Citizen citizen = findCitizenById(citizenId);
		if( citizen == null )
			throw new PersonNotFoundException("The citizen does not exist with id:" + citizenId);

		Idea idea = findIdeaById(ideaId);
		if( idea == null )
			throw new IdeaNotFoundException("The idea does not exist with id:" + ideaId);
		
		List<Rating> ratingList = idea.getRatingList();
		if(ratingList == null)
			ratingList = new ArrayList<Rating>();		
		
		Rating rating = new Rating();
		rating.setPerson(citizen.getPerson());
		rating.setIdea(idea);
		rating.setRating(ratingValue);
		ratingList.add(rating);
		
		idea.setRatingList(ratingList);
		
		saveIdea(idea);
		
		// Check the ratings values for this Contender
		try {
			performPostRatingActions(idea, ratingValue, ratingList);
		} catch (Exception e)
		{
			// We wont do anything here yet
			// We could maybe send a notification to somebody to indidate that there was some
			// error with this processing
		}
	}


	/**
	 * <p>After a rating is added to an idea we need to perform some processing.</p>
	 * <p>We need to check if there are more than 3 ratings under 5 for the idea. 
	 * If so we remove the contender - we will just set their flag to false to do this.</p>
	 * @param idea
	 * @param ratingValue
	 * @param ratingList
	 */
	private void performPostRatingActions(Idea idea, int ratingValue, List<Rating> ratingList) {
		// How many under 5 ratings for this idea
		int countUnderFive = 0;

		// Loop and add up the under 5 ratings
		for(Rating nextRating: ratingList)
			if(nextRating.getRating() < 5)
				countUnderFive++;
		
		// The contender to whom the idea belongs
		Person contender = idea.getManifesto().getPerson();
		
		// Check the new rating is under 5 - if it is then increment the count of under 5 ratings
		if(ratingValue < 5)
			countUnderFive ++;
		
		// if more than 3 ratings on this idea under 5 we need to remove the contender.
		// For now we will just set their flag back to false
		if(countUnderFive > 3)
		{
			System.out.print("3 bad ratings for an idea. idea id=" + idea.getId() 
				+". Removing the contender that owns the idea, id = " + contender.getId());
			// then remove the contender
			contender.setContender(false);
			
			savePerson(contender);
			
			// Nothing left to do here as contender is being removed
			return;
		}
		
		// TODO If rating is more than 5 create a follower
		if(ratingValue > 5) {
			// TODO Havent got to this yet - We want a many to many or even a 1 to many mapping
			// from people to contenders and add 1 here
		}

	}


	/**
	 * <p>This method removes a rating using the citizenID the rating belongs to and the ratingID itself.</p>
	 * <p>In an ideal application scenario with proper authorisation we would have stronger checks that the
	 * citizen can delete the rating. For now we will just use their id to confirm they can delete it.</p>
	 */
	@Override
	public void removeRating(int citizenId, int ratingId) {
		Rating rating = electionDao.findRatingById(ratingId);
		if(rating.getPerson().getId() == citizenId)
			deleteRating(rating);
		else
			throw new RuntimeException("The citizen cant remove this rating as they dont own it");
	}

	/**
	 * <p>This method will determine which person is the winner of the election</p>
	 * <p>It will filter out all the contenders into a list and then ask each
	 *  contender to calculate its average rating value. This value will be saved in
	 *  the contender and will be used to sort the list. The winner will be the person
	 *  at the top of the list</p>
	 */
	@Override
	public Person determinElectionWinner() {
		Person electionWinner = null;
		
		List<Person> allPeople = findAllPeople();
		
		// Get the contenders from all the people
		List<Person> contenders = allPeople.stream()
				.filter(contender -> contender.isContender())
				.collect(Collectors.toList());
		
		// each contender will calculate their average
		contenders.forEach(contender->{
			contender.calculteContendersTotalAverageRatings();			
		});
		
		// Sort the list based on the average ratings
		contenders.stream().sorted((o1, o2)->o1.getTotalAverageRating().
                compareTo(o2.getTotalAverageRating())).
                collect(Collectors.toList());
		
		// Winner is the top of the sorted list
		electionWinner = contenders.get(0);
		
		// return the winner
		return electionWinner;
	}




}
