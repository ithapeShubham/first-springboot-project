package com.movies.Exception;

public class NoMoviesAvaliableException extends Exception {
	private static final long serialVersionUID = 1L;
	public NoMoviesAvaliableException(String message) {
		super(message);
	}

}
