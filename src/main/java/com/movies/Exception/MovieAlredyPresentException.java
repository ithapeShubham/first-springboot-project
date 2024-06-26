package com.movies.Exception;

public class MovieAlredyPresentException extends Exception {
	private static final long serialVersionUID = 1L;

	public MovieAlredyPresentException(String message) {
		super(message);
	}

}
