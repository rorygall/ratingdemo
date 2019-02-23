package com.electioncouncil.ratingdemo.entity;

/**
 * <p>NOTE: this is not fully implemented. It would be more elegant to have the code for the functionality for
 * contenders encapsulated in these methods below.</p>
 
 * @author User
 *
 */
public class ContenderImpl extends Person implements  Contender {

	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Override
	public void postManifesto(String manifesto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postIdea(String idea) {
		// TODO Auto-generated method stub

	}

}
