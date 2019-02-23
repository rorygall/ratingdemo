package com.electioncouncil.ratingdemo.entity;

/**
 * TODO
 * <p>NOTE: this is not implemented. It would be more elegant to have the code for the functionality for
 * citizens encapsulated in these methods below.</p>
 * 
 * @author User
 *
 */
public class CitizenImpl implements Citizen {

	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Override
	public void nominateContender() {
		// TODO Auto-generated method stub
	}

	@Override
	public void rateContender(int personId, int rating) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRatingById(int theId) {
		// TODO Auto-generated method stub
		
	}

}
