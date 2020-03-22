package com.cecum.movies.data;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.cecum.movies.model.Genre;
import com.cecum.movies.model.Movie;
import static org.hamcrest.CoreMatchers.is;

public class MovieRepositoryJdbcIntegrationTest {

	private MovieRepositoryJdbc movieRepositoryJdbc;
	private DataSource dataSource;
	
	@Before
	public void setUp() throws ScriptException, SQLException {
		dataSource = new DriverManagerDataSource("jdbc:h2:mem:test;MODE=MYSQL", "sa", "sa");
		
		ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql-scripts/test-data.sql"));
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		movieRepositoryJdbc = new MovieRepositoryJdbc(jdbcTemplate);
	}
	
	@Test
	public void load_all_movies() throws ScriptException, SQLException {
		
		Collection<Movie> movies = movieRepositoryJdbc.findAll();
		assertThat(movies,is(Arrays.asList(
				new Movie(1, "Dark Knight", 152, Genre.ACTION,"Christopher Nolan"),
                new Movie(2, "Memento", 113, Genre.THRILLER,"Christopher Nolan"),
                new Movie(3, "Matrix", 136, Genre.ACTION,"Lana Wachowski"),
                new Movie(4, "Super 8", 136, Genre.ACTION,"J.J. Abrams"),
                new Movie(5, "Superman", 146, Genre.ACTION,"Zack Snyder")
				)));
	}
	@Test
	public void load_movie_by_id() {
		Movie result = movieRepositoryJdbc.findById(2);
		assertThat(result, is(new Movie(2,"Memento",113,Genre.THRILLER,"Christopher Nolan")));
	}
	
	@Test
	public void insert_a_movie() {
		 Movie movie = new Movie("Super 10", 112, Genre.THRILLER,"No Director");
		 movieRepositoryJdbc.saveOrUpdate(movie);
         Movie movieFromDb = movieRepositoryJdbc.findById(6);
         assertThat( movieFromDb, is(new Movie(6, "Super 10", 112, Genre.THRILLER,"No Director")) );
	}
	
	@Test
	public void find_by_name() {
		Collection<Movie> movies = movieRepositoryJdbc.findByName("Super");
		assertThat(movies,is(Arrays.asList(
					new Movie(4, "Super 8", 136, Genre.ACTION,"J.J. Abrams"),
	                new Movie(5, "Superman", 146, Genre.ACTION,"Zack Snyder")
				)));
	}
	
	@Test
	public void find_by_director(){
		Collection<Movie> movies = movieRepositoryJdbc.findByDirector("Christopher Nolan");
		assertThat(movies,is(Arrays.asList(
				new Movie(1, "Dark Knight", 152, Genre.ACTION,"Christopher Nolan"),
                new Movie(2, "Memento", 113, Genre.THRILLER,"Christopher Nolan")
			)));
	}
	
	@After
	public void tearDown() throws Exception {
		// Remove H2 files -- https://stackoverflow.com/a/51809831/1121497
        final Statement s = dataSource.getConnection().createStatement();
        s.execute("drop all objects delete files"); // "shutdown" is also enough for mem db
	}
	
}
