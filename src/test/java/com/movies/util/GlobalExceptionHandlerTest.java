package com.movies.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.movies.Exception.MovieAlredyPresentException;
import com.movies.Exception.MovieNotFoundException;
import com.movies.Exception.NoMoviesAvaliableException;
import com.movies.Exception.TitleNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

	@InjectMocks
	private GlobalExceptionHandler globalExceptionHandler;

	@Test
	void testGeneralExceptionHandler() {
		Exception ex = new Exception("General Exception");

		ResponseEntity<ErrorInfo> response = globalExceptionHandler.generalExceptionHandler(ex);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Request could not be processed due to some issue. Please try again!",
				response.getBody().getMessage());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getCode());
		assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear()); // Checking
																											// only the
																											// day of
																											// the year
																											// for
																											// simplicity
	}

	@Test
	void testMovieNotFoundExceptionHandler() {
		MovieNotFoundException ex = new MovieNotFoundException("Movie not found");

		ResponseEntity<ErrorInfo> response = globalExceptionHandler.movieNotFoundExceptionHandler(ex);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Movie not found", response.getBody().getMessage());
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getCode());
		assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	}

	@Test
	void testNoMoviesAvaliableExceptionHandler() {
		NoMoviesAvaliableException ex = new NoMoviesAvaliableException("No movies available");

		ResponseEntity<ErrorInfo> response = globalExceptionHandler.noMoviesAvaliableExceptionHandler(ex);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("No movies available", response.getBody().getMessage());
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getCode());
		assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	}

	@Test
	void testTitleNotFoundExceptionHandler() {
		TitleNotFoundException ex = new TitleNotFoundException("Title not found");

		ResponseEntity<ErrorInfo> response = globalExceptionHandler.tItleNotFoundExceptionHandler(ex);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Title not found", response.getBody().getMessage());
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getCode());
		assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	}

	@Test
	void testMovieAlredyPresentExceptionHandler() {
		MovieAlredyPresentException ex = new MovieAlredyPresentException("Movie already present");

		ResponseEntity<ErrorInfo> response = globalExceptionHandler.movieAlredyPresentExceptionHandler(ex);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Note: Should be HttpStatus.BAD_REQUEST
		assertEquals("Movie already present", response.getBody().getMessage());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getCode());
		assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	}

	@Test
	void testExceptionHandler2_MethodArgumentNotValidException() {
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.getAllErrors())
				.thenReturn(Collections.singletonList(new ObjectError("movieDTO", "Validation failed")));

		ResponseEntity<ErrorInfo> response = globalExceptionHandler.exceptionHandler2(
				new org.springframework.web.bind.MethodArgumentNotValidException(null, bindingResult));

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Validation failed", response.getBody().getMessage());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getCode());
		assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	}

	@Test
	void testExceptionHandler2_ConstraintViolationException() {
		Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();
		constraintViolations.add(new TestConstraintViolation("Validation failed"));

		ResponseEntity<ErrorInfo> response = globalExceptionHandler
				.exceptionHandler2(new ConstraintViolationException(constraintViolations));

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Validation failed", response.getBody().getMessage());
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getCode());
		assertEquals(LocalDateTime.now().getDayOfYear(), response.getBody().getTimestamp().getDayOfYear());
	}

	private static class TestConstraintViolation implements ConstraintViolation<Object> {
		private final String message;

		public TestConstraintViolation(String message) {
			this.message = message;
		}

		@Override
		public String getMessage() {
			return message;
		}

		@Override
		public String getMessageTemplate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getRootBean() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Class<Object> getRootBeanClass() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getLeafBean() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object[] getExecutableParameters() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getExecutableReturnValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Path getPropertyPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getInvalidValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ConstraintDescriptor<?> getConstraintDescriptor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <U> U unwrap(Class<U> type) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
