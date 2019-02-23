package com.electioncouncil.ratingdemo.rest;

public class IdeaNotFoundException extends RuntimeException{

	public IdeaNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IdeaNotFoundException(String arg0) {
		super(arg0);
	}

	public IdeaNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
