package com.movies.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.movies.dto.MovieDTO;
import com.movies.service.MovieService;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MovieService movieService;

	@Test
	void testGetAllMovies() throws Exception {
		MovieDTO movie = new MovieDTO();
		movie.setId(1L);
		movie.setTitle("Test Movie");
		movie.setCategory("Action");
		movie.setDirector("Director Name");
		movie.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:00:00"));
		movie.setCast("Cast Name");
		movie.setSongs("Song Name");
		movie.setRating(4.5);

		List<MovieDTO> movies = Arrays.asList(movie);

		Mockito.when(movieService.getAllMovies()).thenReturn(movies);

		mockMvc.perform(get("/api/movies")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(movie.getId()))
				.andExpect(jsonPath("$[0].title").value(movie.getTitle()))
				.andExpect(jsonPath("$[0].category").value(movie.getCategory()))
				.andExpect(jsonPath("$[0].director").value(movie.getDirector()))
//				.andExpect(jsonPath("$[0].releaseDate")
//						.value(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movie.getReleaseDate())))
				.andExpect(jsonPath("$[0].cast").value(movie.getCast()))
				.andExpect(jsonPath("$[0].songs").value(movie.getSongs()))
				.andExpect(jsonPath("$[0].rating").value(movie.getRating()));
	}

	@Test
	void testGetMovieById() throws Exception {
		MovieDTO movie = new MovieDTO();
		movie.setId(1L);
		movie.setTitle("Test Movie");
		movie.setCategory("Action");
		movie.setDirector("Director Name");
		movie.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:00:00"));
		movie.setCast("Cast Name");
		movie.setSongs("Song Name");
		movie.setRating(4.5);

		Mockito.when(movieService.getMovieById(anyLong())).thenReturn(movie);

		mockMvc.perform(get("/api/movies/{id}", 1L)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(movie.getId())).andExpect(jsonPath("$.title").value(movie.getTitle()))
				.andExpect(jsonPath("$.category").value(movie.getCategory()))
				.andExpect(jsonPath("$.director").value(movie.getDirector()))
//				.andExpect(jsonPath("$.releaseDate")
//						.value(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movie.getReleaseDate())))
				.andExpect(jsonPath("$.cast").value(movie.getCast()))
				.andExpect(jsonPath("$.songs").value(movie.getSongs()))
				.andExpect(jsonPath("$.rating").value(movie.getRating()));
	}

	@Test
	void testGetMoviesByCategory() throws Exception {
		MovieDTO movie = new MovieDTO();
		movie.setId(1L);
		movie.setTitle("Test Movie");
		movie.setCategory("Action");
		movie.setDirector("Director Name");
		movie.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:00:00"));
		movie.setCast("Cast Name");
		movie.setSongs("Song Name");
		movie.setRating(4.5);

		List<MovieDTO> movies = Arrays.asList(movie);

		Mockito.when(movieService.getMoviesByCategory(anyString())).thenReturn(movies);

		mockMvc.perform(get("/api/movies/category/{category}", "Action")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(movie.getId()))
				.andExpect(jsonPath("$[0].title").value(movie.getTitle()))
				.andExpect(jsonPath("$[0].category").value(movie.getCategory()))
				.andExpect(jsonPath("$[0].director").value(movie.getDirector()))
//				.andExpect(jsonPath("$[0].releaseDate")
//						.value(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movie.getReleaseDate())))
				.andExpect(jsonPath("$[0].cast").value(movie.getCast()))
				.andExpect(jsonPath("$[0].songs").value(movie.getSongs()))
				.andExpect(jsonPath("$[0].rating").value(movie.getRating()));
	}

	@Test
	void testGetMoviesByRating() throws Exception {
		MovieDTO movie = new MovieDTO();
		movie.setId(1L);
		movie.setTitle("Test Movie");
		movie.setCategory("Action");
		movie.setDirector("Director Name");
		movie.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:00:00"));
		movie.setCast("Cast Name");
		movie.setSongs("Song Name");
		movie.setRating(4.5);

		List<MovieDTO> movies = Arrays.asList(movie);

		Mockito.when(movieService.getMoviesByRating(anyDouble(), anyDouble())).thenReturn(movies);

		mockMvc.perform(get("/api/movies/by-rating").param("minRating", "1.0").param("maxRating", "5.0"))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(movie.getId()))
				.andExpect(jsonPath("$[0].title").value(movie.getTitle()))
				.andExpect(jsonPath("$[0].category").value(movie.getCategory()))
				.andExpect(jsonPath("$[0].director").value(movie.getDirector()))
//				.andExpect(jsonPath("$[0].releaseDate")
//						.value(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movie.getReleaseDate())))
				.andExpect(jsonPath("$[0].cast").value(movie.getCast()))
				.andExpect(jsonPath("$[0].songs").value(movie.getSongs()))
				.andExpect(jsonPath("$[0].rating").value(movie.getRating()));
	}

	@Test
	void testCreateMovie() throws Exception {
		MovieDTO movie = new MovieDTO();
		movie.setTitle("New Movie");
		movie.setCategory("Action");
		movie.setDirector("Director Name");
		movie.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:00:00"));
		movie.setCast("Cast Name");
		movie.setSongs("Song Name");
		movie.setRating(4.5);

		Mockito.when(movieService.createMovie(any(MovieDTO.class))).thenReturn(movie.getTitle());

		mockMvc.perform(post("/api/movies").contentType(MediaType.APPLICATION_JSON).content(
				"{\"title\": \"New Movie\", \"category\": \"Action\", \"director\": \"Director Name\", \"releaseDate\": \"2022-01-01T12:00:00Z\", \"cast\": \"Cast Name\", \"songs\": \"Song Name\", \"rating\": 4.5}"))
				.andExpect(status().isCreated());
			//	.andExpect(MockMvcResultMatchers.content().string(movie.getTitle() + " movie added"));
	}

	@Test
	void testUpdateMovie() throws Exception {
		Mockito.when(movieService.updateMovie(anyLong(), anyDouble())).thenReturn("Movie updated");

		mockMvc.perform(put("/api/movies/{id}", 1L).param("rating", "4.5")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Movie updated"));
	}

	@Test
	void testDeleteMovie() throws Exception {
		Mockito.when(movieService.deleteMovie(anyLong())).thenReturn("Movie deleted");

		mockMvc.perform(delete("/api/movies/{id}", 1L)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Movie deleted"));
	}

	@Test
	void testDeleteMovieByTitle() throws Exception {
		Mockito.when(movieService.deleteMovieByTitle(anyString())).thenReturn("Movie deleted successfully");

		mockMvc.perform(delete("/api/movies/by-title").param("title", "Test Movie")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Movie deleted successfully"));
	}
}