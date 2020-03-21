package com.cecum.movies.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.cecum.movies.model.Genre;
import com.cecum.movies.model.Movie;

public class MovieRepositoryJdbc implements MovieRepository {

	private JdbcTemplate jdbcTemplate;
	
	
	
	public MovieRepositoryJdbc(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Movie findById(long id) {
		Object[] args = {id};
		return jdbcTemplate.queryForObject("select * from movies where id = ?", args,movieMapper);
	}

	@Override
	public Collection<Movie> findAll() {
		// TODO Auto-generated method stub
		
		return jdbcTemplate.query("select * from movies", movieMapper);
	}

	@Override
	public void saveOrUpdate(Movie movie) {
		jdbcTemplate.update("insert into movies (name, minutes, genre) values (?, ?, ?)",
                movie.getName(), movie.getMinutes(), movie.getGenre().toString());

	}
	
	private static RowMapper<Movie> movieMapper = new RowMapper<Movie>() {

		@Override
		public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			return new Movie(rs.getInt("id"),
					rs.getString("name"),
					rs.getInt("minutes"),
					Genre.valueOf(rs.getString("genre")));
		}
		
	};

}
