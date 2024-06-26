package com.movies.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.movies.entity.Movie;

import jakarta.transaction.Transactional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

	List<Movie> findByRatingBetween(Double minRating, Double maxRating);

	Optional<Movie> findByTitle(String title);

	@Query("SELECT m FROM Movie m WHERE m.category LIKE %:category%")
	List<Movie> findByCategoryContaining(@Param("category") String category);

	@Transactional
	@Modifying
	@Query("DELETE FROM Movie m WHERE m.title = :title")
	void deleteByTitle(@Param("title") String title);
}