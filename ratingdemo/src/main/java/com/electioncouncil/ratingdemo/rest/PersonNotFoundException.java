package com.electioncouncil.ratingdemo.rest;

public class PersonNotFoundException extends RuntimeException{

	public PersonNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PersonNotFoundException(String arg0) {
		super(arg0);
	}

	public PersonNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
