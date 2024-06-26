package com.movies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movies.Exception.MovieAlredyPresentException;
import com.movies.Exception.MovieNotFoundException;
import com.movies.Exception.NoMoviesAvaliableException;
import com.movies.Exception.TitleNotFoundException;
import com.movies.dto.MovieDTO;
import com.movies.entity.Movie;
import com.movies.repository.MovieRepository;
import com.movies.util.ConfigConstant;

@Service
@Transactional
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	public List<MovieDTO> getAllMovies() throws NoMoviesAvaliableException {
		List<MovieDTO> md = new ArrayList<>();
		Iterable<Movie> movie = movieRepository.findAll();
		for (Movie m : movie) {
			MovieDTO movieDTO = convertToDto(m);
			md.add(movieDTO);
		}
		if (md.isEmpty()) {
			throw new NoMoviesAvaliableException(ConfigConstant.MOVIES_NOT_FOUND);
		}
		return md;
	}

	public MovieDTO getMovieById(Long id) throws MovieNotFoundException {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new MovieNotFoundException(ConfigConstant.MOVIE_NOT_FOUND + id));
		return convertToDto(movie);
	}

	public String createMovie(MovieDTO movieDTO) throws MovieAlredyPresentException {
		Optional<Movie> movie = movieRepository.findById(movieDTO.getId());
		if (!movie.isEmpty()) {
			throw new MovieAlredyPresentException(ConfigConstant.MOVIE_PRESENT + movieDTO.getId());
		}
		Movie mo = convertToEntity(movieDTO);

		Movie savedMovie = movieRepository.save(mo);
		String title = savedMovie.getTitle();
		return title;
	}

	public String updateMovie(Long id, Double rating) throws MovieNotFoundException {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new MovieNotFoundException(ConfigConstant.MOVIE_NOT_FOUND + id));
		movie.setRating(rating);
		return ConfigConstant.MOVIEINFO_UPDATED;

	}

	public String deleteMovie(Long id) throws MovieNotFoundException {
		Movie movie = movieRepository.findById(id)
				.orElseThrow(() -> new MovieNotFoundException(ConfigConstant.MOVIE_NOT_FOUND + id));
		movieRepository.delete(movie);
		return ConfigConstant.MOVIE_DELETED + id;
	}

	public List<MovieDTO> getMoviesByRating(Double minRating, Double maxRating) throws NoMoviesAvaliableException {
		List<Movie> movie = movieRepository.findByRatingBetween(minRating, maxRating);
		List<MovieDTO> md = new ArrayList<>();
		for (Movie m : movie) {
			MovieDTO movieDto = convertToDto(m);
			md.add(movieDto);
		}
		if (md.isEmpty()) {
			throw new NoMoviesAvaliableException(ConfigConstant.MOVIES_NOT_FOUND);
		}
		return md;

	}

	public List<MovieDTO> getMoviesByCategory(String category) throws NoMoviesAvaliableException {
		List<Movie> movie = movieRepository.findByCategoryContaining(category);
		List<MovieDTO> md = new ArrayList<>();
		for (Movie m : movie) {
			MovieDTO movieDto = convertToDto(m);
			md.add(movieDto);
		}
		if (md.isEmpty()) {
			throw new NoMoviesAvaliableException(ConfigConstant.MOVIES_NOT_FOUND);
		}
		return md;
	}

	public String deleteMovieByTitle(String title) throws TitleNotFoundException {
		Optional<Movie> movie = movieRepository.findByTitle(title);
		String message = null;
		if (movie.isPresent()) {
			movieRepository.deleteByTitle(title);
			message = ConfigConstant.MOVIE_DELETED_FOR_TITLE + title;
		} else {
			throw new TitleNotFoundException(ConfigConstant.TITLE_NOT_FOUND+title);
		}
		return message;

	}

	private MovieDTO convertToDto(Movie movie) {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(movie.getId());
		movieDTO.setTitle(movie.getTitle());
		movieDTO.setCategory(movie.getCategory());
		movieDTO.setDirector(movie.getDirector());
		movieDTO.setReleaseDate(movie.getReleaseDate());
		movieDTO.setCast(movie.getCast());
		movieDTO.setSongs(movie.getSongs());
		movieDTO.setRating(movie.getRating());
		return movieDTO;
	}

	private Movie convertToEntity(MovieDTO movieDTO) {
		Movie movie = new Movie();
		movie.setId(movieDTO.getId());
		movie.setTitle(movieDTO.getTitle());
		movie.setCategory(movieDTO.getCategory());
		movie.setDirector(movieDTO.getDirector());
		movie.setReleaseDate(movieDTO.getReleaseDate());
		movie.setCast(movieDTO.getCast());
		movie.setSongs(movieDTO.getSongs());
		movie.setRating(movieDTO.getRating());
		return movie;
	}
}
