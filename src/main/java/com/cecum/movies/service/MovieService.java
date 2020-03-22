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
	
	public Collection<Movie> findMoviesByTemplate(Movie template) {
		if(template.getMinutes()<0){
			throw new IllegalArgumentException("This value is not allowed");
		}
		
		Collection<Movie> result = movieRepository.findAll();
		if(template.getName()!=null) {
			result=result.stream().filter(movie -> movie.getName().toLowerCase().contains(template.getName().toLowerCase())).collect(Collectors.toList());
		}
		if(template.getMinutes()!=null) {
			result=result.stream().filter(movie -> movie.getMinutes() <= template.getMinutes()).collect(Collectors.toList());
		}
		if(template.getGenre()!=null) {
			result=result.stream().filter(movie -> movie.getGenre() == template.getGenre()).collect(Collectors.toList());
		}
		if(template.getDirector()!=null) {
			result=result.stream().filter(movie -> movie.getDirector().toLowerCase().contains(template.getDirector())).collect(Collectors.toList());
		}
		return result;
		
	}

}
