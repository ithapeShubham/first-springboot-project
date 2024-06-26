package com.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movies.Exception.MovieAlredyPresentException;
import com.movies.Exception.MovieNotFoundException;
import com.movies.Exception.NoMoviesAvaliableException;
import com.movies.Exception.TitleNotFoundException;
import com.movies.dto.MovieDTO;
import com.movies.service.MovieService;
import com.movies.util.ConfigConstant;
import com.movies.util.ErrorInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/movies")
@Validated
public class MovieController {

	@Autowired
	private MovieService movieService;

	@Operation(summary = "Get all movies", description = "Get a list of all movies")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieDTO.class)))),
			@ApiResponse(responseCode = "404", description = "Movies not found", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorInfo.class))) })
	@GetMapping
	public ResponseEntity<List<MovieDTO>> getAllMovies() throws NoMoviesAvaliableException {
		List<MovieDTO> movies = movieService.getAllMovies();
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}

	@Operation(summary = "Get a movie by ID", description = "Get a movie by its unique ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movie found", content = @Content(schema = @Schema(implementation = MovieDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "404", description = "Movie not found", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorInfo.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<MovieDTO> getMovieById(@PathVariable @NotNull(message = "please provide id") Long id)
			throws MovieNotFoundException {
		MovieDTO movie = movieService.getMovieById(id);
		return new ResponseEntity<>(movie, HttpStatus.OK);
	}

	@Operation(summary = "Get movies by category", description = "Get a list of movies by their category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list of movies", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieDTO.class)))),
			@ApiResponse(responseCode = "400", description = "Invalid category supplied", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "404", description = "Movies not found", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorInfo.class))) })
	@GetMapping("/category/{category}")
	public List<MovieDTO> getMoviesByCategory(
			@PathVariable("category") @NotBlank(message = "please provide category") String category)
			throws NoMoviesAvaliableException {
		return movieService.getMoviesByCategory(category);
	}

	@Operation(summary = "Get movies by rating range", description = "Get a list of movies with ratings between the specified range")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieDTO.class)))),
			@ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "404", description = "Movies not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class))) })
	@GetMapping("/by-rating")
	public ResponseEntity<List<MovieDTO>> getMoviesByRating(
			@RequestParam @NotNull(message = "please provide minRating") Double minRating,
			@RequestParam @NotNull(message = "please provide maxRating") Double maxRating)
			throws NoMoviesAvaliableException {
		List<MovieDTO> movies = movieService.getMoviesByRating(minRating, maxRating);
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}

	@Operation(summary = "Add a new movie", description = "Create a new movie with the provided data")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movie created", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorInfo.class))) })
	@PostMapping
	public ResponseEntity<String> createMovie(@Valid @RequestBody MovieDTO movieDTO)
			throws MovieAlredyPresentException {
		String title = movieService.createMovie(movieDTO);
		String message = title + ConfigConstant.MOVIEINFO_ADDED;
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	@Operation(summary = "Update a movie", description = "Update an existing movie with the provided data")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movie updated", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "404", description = "Movie not found", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorInfo.class))) })
	@PutMapping("/{id}")
	public ResponseEntity<String> updateMovie(@PathVariable @NotNull(message = "please provide id") Long id,
			@RequestParam Double rating) throws MovieNotFoundException {
		String message = movieService.updateMovie(id, rating);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@Operation(summary = "Delete a movie", description = "Delete a movie by its unique ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movie deleted", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "404", description = "Movie not found", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorInfo.class))) })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable @NotNull(message = "please provide id") Long id)
			throws MovieNotFoundException {
		String message = movieService.deleteMovie(id);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@Operation(summary = "Delete a movie by title", description = "Delete a movie by its title")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movie deleted", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "404", description = "Movie not found", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorInfo.class))) })
	@DeleteMapping("/by-title")
	public ResponseEntity<String> deleteMovieByTitle(
			@RequestParam @NotBlank(message = "please provide title") String title) throws TitleNotFoundException {
		movieService.deleteMovieByTitle(title);
		return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully");
	}

}
