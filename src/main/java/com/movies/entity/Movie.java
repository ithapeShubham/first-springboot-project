package com.movies.entity;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@NamedQuery(name = "Movie.deleteByTitle", query = "DELETE FROM Movie m WHERE m.title = :title")
@Table(name = "movies")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String category;
	private String director;
	private Date releaseDate;
	private String cast;
	private String songs;
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
	public int hashCode() {
		return Objects.hash(cast, category, director, id, rating, releaseDate, songs, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		return Objects.equals(cast, other.cast) && Objects.equals(category, other.category)
				&& Objects.equals(director, other.director) && Objects.equals(id, other.id)
				&& Objects.equals(rating, other.rating) && Objects.equals(releaseDate, other.releaseDate)
				&& Objects.equals(songs, other.songs) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", category=" + category + ", director=" + director
				+ ", releaseDate=" + releaseDate + ", cast=" + cast + ", songs=" + songs + ", rating=" + rating + "]";
	}

	// Getters and Setters
}
