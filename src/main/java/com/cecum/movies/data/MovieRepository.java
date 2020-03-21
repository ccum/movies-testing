package com.cecum.movies.data;

import java.util.Collection;

import com.cecum.movies.model.Movie;

public interface MovieRepository {

	Movie findById(long id);
	Collection<Movie> findAll();
	void saveOrUpdate(Movie movie);
}
