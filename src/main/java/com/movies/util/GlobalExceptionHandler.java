package com.movies.util;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.movies.Exception.MovieAlredyPresentException;
import com.movies.Exception.MovieNotFoundException;
import com.movies.Exception.NoMoviesAvaliableException;
import com.movies.Exception.TitleNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception ex) {

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorInfo.setMessage(ConfigConstant.GENERAL_EXCEPTION_MESSAGE);
		errorInfo.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<ErrorInfo> movieNotFoundExceptionHandler(MovieNotFoundException ex) {

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setCode(HttpStatus.NOT_FOUND.value());
		errorInfo.setMessage(ex.getMessage());
		errorInfo.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoMoviesAvaliableException.class)
	public ResponseEntity<ErrorInfo> noMoviesAvaliableExceptionHandler(NoMoviesAvaliableException ex) {

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setCode(HttpStatus.NOT_FOUND.value());
		errorInfo.setMessage(ex.getMessage());
		errorInfo.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TitleNotFoundException.class)
	public ResponseEntity<ErrorInfo> tItleNotFoundExceptionHandler(TitleNotFoundException ex) {

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setCode(HttpStatus.NOT_FOUND.value());
		errorInfo.setMessage(ex.getMessage());
		errorInfo.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MovieAlredyPresentException.class)
	public ResponseEntity<ErrorInfo> movieAlredyPresentExceptionHandler(MovieAlredyPresentException ex) {

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setMessage(ex.getMessage());
		errorInfo.setTimestamp(LocalDateTime.now());

		return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
	public ResponseEntity<ErrorInfo> exceptionHandler2(Exception ex) {

		String errorMsg;

		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException manve = (MethodArgumentNotValidException) ex;
			errorMsg = manve.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
					.collect(Collectors.joining(", "));
		} else {
			ConstraintViolationException cve = (ConstraintViolationException) ex;

			errorMsg = cve.getConstraintViolations().stream().map(x -> x.getMessage())
					.collect(Collectors.joining(", "));
		}

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setMessage(errorMsg);
		errorInfo.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

}
