package com.ph.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ph.builders.MovieBuilder;
import com.ph.builders.UserBuilder;
import com.ph.daos.RentDAO;
import com.ph.entities.User;
import com.ph.exceptions.RentException;
import com.ph.matchers.SelfMatcher;
import com.ph.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import com.ph.entities.Movie;
import com.ph.entities.Rent;
import com.ph.exceptions.MovieOutOfStockException;
import org.mockito.Mockito;

public class RentServiceTest {

	private RentService service;
	private SPCService spcService;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup(){
		service = new RentService();
		RentDAO dao = Mockito.mock(RentDAO.class);
		service.setRentDao(dao);
		spcService = Mockito.mock(SPCService.class);
		service.setSpcService(spcService);
	}
	
	@Test
	public void rentTest() throws Exception {
		Assume.assumeFalse(DataUtils.checkWeekDay(new Date(), Calendar.SATURDAY));

		//given
		User user = UserBuilder.aUser().now();
		List<Movie> movies = Arrays.asList(MovieBuilder.aMovie().withValue(5.0).now());

		//when
		Rent rent = service.rentMovie(user, movies);

		//then
		error.checkThat(rent.getValue(), is(equalTo(5.0)));
		error.checkThat(DataUtils.isSameDate(rent.getRentDate(), new Date()), is(true));
		error.checkThat(DataUtils.isSameDate(rent.getReturnDate(), DataUtils.obtainDataInDifferenceInDays(1)), is(true));
	}
	
	@Test(expected = MovieOutOfStockException.class)
	public void rentTest_movieOutOfStock() throws Exception{
		//given
		User user = UserBuilder.aUser().now();
		List<Movie> movies = Arrays.asList(MovieBuilder.noStockMovie().now());
		
		//when
		service.rentMovie(user, movies);
	}
	
	@Test
	public void rentTest_EmptyUser() throws MovieOutOfStockException {
		//given
		List<Movie> movies = Arrays.asList(MovieBuilder.aMovie().now());

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
		User user = UserBuilder.aUser().now();
		
		exception.expect(RentException.class);
		exception.expectMessage("Empty movie");
		
		//when
		service.rentMovie(user, null);
	}

	@Test
	public void mustReturnInMondayIfRentedOnSaturday() throws MovieOutOfStockException, RentException{
		Assume.assumeTrue(DataUtils.checkWeekDay(new Date(), Calendar.SATURDAY));

		//given
		User user = UserBuilder.aUser().now();
		List<Movie> movies = Arrays.asList(MovieBuilder.aMovie().now());

		//when
		Rent giveBack = service.rentMovie(user,movies);

		//then
		assertThat(giveBack.getReturnDate(), SelfMatcher.fallsInAMonday());

	}

	@Test
	public void dontRentMovieIfUserIsNegative()
			throws MovieOutOfStockException, RentException{
		//given
		User user = UserBuilder.aUser().now();
		List<Movie> movies = Arrays.asList(MovieBuilder.aMovie().now());

		Mockito.when(spcService.isNegative(user)).thenReturn(true);

		exception.expect(RentException.class);
		exception.expectMessage("Negative User");

		//then
		service.rentMovie(user,movies);
	}
}
