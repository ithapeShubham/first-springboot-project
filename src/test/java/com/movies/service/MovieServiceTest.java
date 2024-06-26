package com.movies.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.movies.Exception.MovieAlredyPresentException;
import com.movies.Exception.MovieNotFoundException;
import com.movies.Exception.NoMoviesAvaliableException;
import com.movies.Exception.TitleNotFoundException;
import com.movies.dto.MovieDTO;
import com.movies.entity.Movie;
import com.movies.repository.MovieRepository;
import com.movies.util.ConfigConstant;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

	@InjectMocks
	private MovieService movieService;

	@Mock
	private MovieRepository movieRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	private Date createDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}

	private Movie createTestMovie() {
		Movie movie = new Movie();
		movie.setId(1L);
		movie.setTitle("Test Movie");
		movie.setCategory("Action");
		movie.setDirector("Test Director");
		movie.setReleaseDate(createDate(2022, Calendar.JANUARY, 1));
		movie.setCast("Test Cast");
		movie.setSongs("Test Songs");
		movie.setRating(4.5);
		return movie;
	}

	private MovieDTO createTestMovieDTO() {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(1L);
		movieDTO.setTitle("Test Movie");
		movieDTO.setCategory("Action");
		movieDTO.setDirector("Test Director");
		movieDTO.setReleaseDate(createDate(2022, Calendar.JANUARY, 1));
		movieDTO.setCast("Test Cast");
		movieDTO.setSongs("Test Songs");
		movieDTO.setRating(4.5);
		return movieDTO;
	}

	@Test
	public void testGetAllMovies() throws NoMoviesAvaliableException {
		List<Movie> movies = new ArrayList<>();
		movies.add(createTestMovie());

		when(movieRepository.findAll()).thenReturn(movies);

		List<MovieDTO> movieDTOs = movieService.getAllMovies();

		assertNotNull(movieDTOs);
		assertFalse(movieDTOs.isEmpty());
		assertEquals(1, movieDTOs.size());

		MovieDTO movieDTO = movieDTOs.get(0);
		assertEquals("Test Movie", movieDTO.getTitle());
		assertEquals("Action", movieDTO.getCategory());
		assertEquals("Test Director", movieDTO.getDirector());
		// assertEquals(createDate(2022, Calendar.JANUARY, 1),
		// movieDTO.getReleaseDate());
		assertEquals("Test Cast", movieDTO.getCast());
		assertEquals("Test Songs", movieDTO.getSongs());
		assertEquals(4.5, movieDTO.getRating());
	}

	@Test
	public void testGetAllMovies_NoMovies() {
		when(movieRepository.findAll()).thenReturn(new ArrayList<>());

		assertThrows(NoMoviesAvaliableException.class, () -> movieService.getAllMovies());
	}

	@Test
	public void testGetMovieById() throws MovieNotFoundException {
		Movie movie = createTestMovie();

		when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

		MovieDTO movieDTO = movieService.getMovieById(1L);

		assertNotNull(movieDTO);
		assertEquals("Test Movie", movieDTO.getTitle());
		assertEquals("Action", movieDTO.getCategory());
		assertEquals("Test Director", movieDTO.getDirector());
		// assertEquals(createDate(2022, Calendar.JANUARY, 1),
		// movieDTO.getReleaseDate());
		assertEquals("Test Cast", movieDTO.getCast());
		assertEquals("Test Songs", movieDTO.getSongs());
		assertEquals(4.5, movieDTO.getRating());
	}

	@Test
	public void testGetMovieById_NotFound() {
		when(movieRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(MovieNotFoundException.class, () -> movieService.getMovieById(1L));
	}

	@Test
	public void testCreateMovie() throws MovieAlredyPresentException {
		MovieDTO movieDTO = createTestMovieDTO();

		when(movieRepository.findById(1L)).thenReturn(Optional.empty());
		when(movieRepository.save(any(Movie.class))).thenReturn(createTestMovie());

		String title = movieService.createMovie(movieDTO);

		assertNotNull(title);
		assertEquals("Test Movie", title);
	}

	@Test
	public void testCreateMovie_AlreadyPresent() {
		MovieDTO movieDTO = createTestMovieDTO();

		when(movieRepository.findById(1L)).thenReturn(Optional.of(createTestMovie()));

		assertThrows(MovieAlredyPresentException.class, () -> movieService.createMovie(movieDTO));
	}

	@Test
	public void testUpdateMovie() throws MovieNotFoundException {
		Movie movie = createTestMovie();

		when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

		String response = movieService.updateMovie(1L, 5.0);

		assertEquals(ConfigConstant.MOVIEINFO_UPDATED, response);
		assertEquals(5.0, movie.getRating());

	}

	@Test
	public void testUpdateMovie_NotFound() {
		when(movieRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(MovieNotFoundException.class, () -> movieService.updateMovie(1L, 5.0));
	}

	@Test
	public void testDeleteMovie() throws MovieNotFoundException {
		Movie movie = createTestMovie();

		when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

		String response = movieService.deleteMovie(1L);

		assertEquals(ConfigConstant.MOVIE_DELETED + 1L, response);
		verify(movieRepository, times(1)).delete(movie);
	}

	@Test
	public void testDeleteMovie_NotFound() {
		when(movieRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(MovieNotFoundException.class, () -> movieService.deleteMovie(1L));
	}

	@Test
	public void testGetMoviesByRating() throws NoMoviesAvaliableException {
		List<Movie> movies = new ArrayList<>();
		movies.add(createTestMovie());

		when(movieRepository.findByRatingBetween(4.0, 5.0)).thenReturn(movies);

		List<MovieDTO> movieDTOs = movieService.getMoviesByRating(4.0, 5.0);

		assertNotNull(movieDTOs);
		assertFalse(movieDTOs.isEmpty());
		assertEquals(1, movieDTOs.size());

		MovieDTO movieDTO = movieDTOs.get(0);
		assertEquals("Test Movie", movieDTO.getTitle());
		assertEquals("Action", movieDTO.getCategory());
		assertEquals("Test Director", movieDTO.getDirector());
		// assertEquals(createDate(2022, Calendar.JANUARY, 1),
		// movieDTO.getReleaseDate());
		assertEquals("Test Cast", movieDTO.getCast());
		assertEquals("Test Songs", movieDTO.getSongs());
		assertEquals(4.5, movieDTO.getRating());
	}

	@Test
	public void testGetMoviesByRating_NoMovies() {
		when(movieRepository.findByRatingBetween(4.0, 5.0)).thenReturn(new ArrayList<>());

		assertThrows(NoMoviesAvaliableException.class, () -> movieService.getMoviesByRating(4.0, 5.0));
	}

	@Test
	public void testGetMoviesByCategory() throws NoMoviesAvaliableException {
		List<Movie> movies = new ArrayList<>();
		movies.add(createTestMovie());

		when(movieRepository.findByCategoryContaining("Action")).thenReturn(movies);

		List<MovieDTO> movieDTOs = movieService.getMoviesByCategory("Action");

		assertNotNull(movieDTOs);
		assertFalse(movieDTOs.isEmpty());
		assertEquals(1, movieDTOs.size());

		MovieDTO movieDTO = movieDTOs.get(0);
		assertEquals("Test Movie", movieDTO.getTitle());
		assertEquals("Action", movieDTO.getCategory());
		assertEquals("Test Director", movieDTO.getDirector());
		// assertEquals(createDate(2022, Calendar.JANUARY, 1),
		// movieDTO.getReleaseDate());
		assertEquals("Test Cast", movieDTO.getCast());
		assertEquals("Test Songs", movieDTO.getSongs());
		assertEquals(4.5, movieDTO.getRating());
	}

	@Test
	public void testGetMoviesByCategory_NoMovies() {
		when(movieRepository.findByCategoryContaining("Action")).thenReturn(new ArrayList<>());

		assertThrows(NoMoviesAvaliableException.class, () -> movieService.getMoviesByCategory("Action"));
	}

	@Test
	public void testDeleteMovieByTitle() throws TitleNotFoundException {
		Movie movie = createTestMovie();

		when(movieRepository.findByTitle("Test Movie")).thenReturn(Optional.of(movie));

		String response = movieService.deleteMovieByTitle("Test Movie");

		assertEquals(ConfigConstant.MOVIE_DELETED_FOR_TITLE + "Test Movie", response);
		verify(movieRepository, times(1)).deleteByTitle("Test Movie");
	}

	@Test
	public void testDeleteMovieByTitle_NotFound() {
		when(movieRepository.findByTitle("Test Movie")).thenReturn(Optional.empty());

		assertThrows(TitleNotFoundException.class, () -> movieService.deleteMovieByTitle("Test Movie"));
	}
}