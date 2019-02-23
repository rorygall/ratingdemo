package com.electioncouncil.ratingdemo.rest;

public class ManifestoNotFoundException extends RuntimeException{

	public ManifestoNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ManifestoNotFoundException(String arg0) {
		super(arg0);
	}

	public ManifestoNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
