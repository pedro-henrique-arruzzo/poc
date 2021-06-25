package com.ph.services;

import static com.ph.builders.MovieBuilder.aMovie;
import static com.ph.builders.UserBuilder.aUser;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ph.daos.RentDAO;
import com.ph.entities.Movie;
import com.ph.entities.Rent;
import com.ph.entities.User;
import com.ph.exceptions.MovieOutOfStockException;
import com.ph.exceptions.RentException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

@RunWith(Parameterized.class)
public class CalculeRentValueTest {

	private RentService service;
	
	@Parameter
	public List<Movie> movies;
	
	@Parameter(value=1)
	public Double rentValue;
	
	@Parameter(value=2)
	public String scenery;
	
	@Before
	public void setup(){
		service = new RentService();
		RentDAO dao = Mockito.mock(RentDAO.class);
		service.setRentDao(dao);
		SPCService spcService = Mockito.mock(SPCService.class);
		service.setSpcService(spcService);
	}
	
	private static Movie movie1 = aMovie().now();
	private static Movie movie2 = aMovie().now();
	private static Movie movie3 = aMovie().now();
	private static Movie movie4 = aMovie().now();
	private static Movie movie5 = aMovie().now();
	private static Movie movie6 = aMovie().now();
	private static Movie movie7 = aMovie().now();
	
	@Parameters(name="{2}")
	public static Collection<Object[]> getParameters(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(movie1, movie2), 8.0, "2 Movies: No Discount"},
			{Arrays.asList(movie1, movie2, movie3), 11.0, "3 Movies: 25%"},
			{Arrays.asList(movie1, movie2, movie3, movie4), 13.0, "4 Movies: 50%"},
			{Arrays.asList(movie1, movie2, movie3, movie4, movie5), 14.0, "5 Movies: 75%"},
			{Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6), 14.0, "6 Movies: 100%"},
			{Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7), 18.0, "7 Movies: No Discount"}
		});
	}
	
	@Test
	public void mustCalculeRentValueConsideringDiscounts() throws MovieOutOfStockException, RentException {
		//given
		User user = aUser().now();
		
		//when
		Rent result = service.rentMovie(user, movies);
		
		//then
		assertThat(result.getValue(), is(rentValue));
	}
}
