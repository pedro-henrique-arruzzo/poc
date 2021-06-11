package com.ph.services;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ph.entities.User;
import com.ph.exceptions.RentException;
import com.ph.utils.DataUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import com.ph.entities.Movie;
import com.ph.entities.Rent;
import com.ph.exceptions.MovieOutOfStockException;

public class RentServiceTest {

	private RentService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup(){
		service = new RentService();
	}
	
	@Test
	public void rentTest() throws Exception {
		//given
		User user = new User("User 1");
		List<Movie> movies = Arrays.asList(new Movie("Movie 1", 1, 5.0));

		//when
		Rent rent = service.rentMovie(user, movies);

		//then
		error.checkThat(rent.getValue(), is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(rent.getRentDate(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(rent.getReturnDate(), DataUtils.obterDataComDiferencaDias(1)), is(true));
	}
	
	@Test(expected = MovieOutOfStockException.class)
	public void rentTest_movieOutOfStock() throws Exception{
		//given
		User user = new User("User 1");
		List<Movie> movies = Arrays.asList(new Movie("Movie 2", 0, 4.0));
		
		//when
		service.rentMovie(user, movies);
	}
	
	@Test
	public void rentTest_EmptyUser() throws MovieOutOfStockException {
		//given
		List<Movie> movies = Arrays.asList(new Movie("Movie 2", 1, 4.0));
		
		//when
		try {
			service.rentMovie(null, movies);
			Assert.fail();
		} catch (RentException e) {
			assertThat(e.getMessage(), is("Empty user"));
		}
	}

	@Test
	public void rentTest_EmptyMovie() throws MovieOutOfStockException, RentException {
		//given
		User user = new User("User 1");
		
		exception.expect(RentException.class);
		exception.expectMessage("Empty movie");
		
		//when
		service.rentMovie(user, null);
	}
}
