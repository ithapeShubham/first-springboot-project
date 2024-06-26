package com.movies.dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MovieDTO {
	@NotNull(message = "id is required")
	private Long id;

	@NotEmpty(message = "Title is required")
	private String title;

	@NotEmpty(message = "Category is required")
	private String category;

	@NotEmpty(message = "Director is required")
	private String director;

	@NotNull(message = "Release date is required")
	private Date releaseDate;

	@NotEmpty(message = "Cast is required")
	private String cast;

	@NotEmpty(message = "Songs are required")
	private String songs;

	@NotNull(message = "Rating is required")
	private Double rating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getSongs() {
		return songs;
	}

	public void setSongs(String songs) {
		this.songs = songs;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "MovieDTO [id=" + id + ", title=" + title + ", category=" + category + ", director=" + director
				+ ", releaseDate=" + releaseDate + ", cast=" + cast + ", songs=" + songs + ", rating=" + rating + "]";
	}

	// Getters and Setters
}
