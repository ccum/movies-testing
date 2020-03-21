package com.cecum.movies.service;

import java.util.Collection;
import java.util.stream.Collectors;

import com.cecum.movies.data.MovieRepository;
import com.cecum.movies.model.Genre;
import com.cecum.movies.model.Movie;

public class MovieService {

	private MovieRepository movieRepository;
	
	
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}


	public Collection<Movie> findMovieByGenre(Genre genre) {
		
		Collection<Movie> result =  movieRepository.findAll().stream().filter(movie -> movie.getGenre() == genre).collect(Collectors.toList());
		return result;
		
	}


	public Collection<Movie> findMovieByLength(int length) {
		// TODO Auto-generated method stub
		Collection<Movie> result =  movieRepository.findAll().stream().filter(movie -> movie.getMinutes() <= length).collect(Collectors.toList());
		return result;
	}

}
