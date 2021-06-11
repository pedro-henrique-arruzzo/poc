package com.ph.servicos;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.ph.entidades.User;
import com.ph.exceptions.LocadoraException;
import com.ph.utils.DataUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import com.ph.entidades.Movie;
import com.ph.entidades.Rent;
import com.ph.exceptions.FilmeSemEstoqueException;

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
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception{
		//cenario
		User user = new User("Usuario 1");
		List<Movie> movies = Arrays.asList(new Movie("Filme 2", 0, 4.0));
		
		//acao
		service.rentMovie(user, movies);
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException{
		//cenario
		List<Movie> movies = Arrays.asList(new Movie("Filme 2", 1, 4.0));
		
		//acao
		try {
			service.rentMovie(null, movies);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Empty user"));
		}
	}

	@Test
	public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException{
		//cenario
		User user = new User("Usuario 1");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Empty movie");
		
		//acao
		service.rentMovie(user, null);
	}
}
