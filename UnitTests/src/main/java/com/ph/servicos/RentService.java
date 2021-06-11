package com.ph.servicos;

import java.util.Date;
import java.util.List;

import com.ph.entidades.Movie;
import com.ph.entidades.Rent;
import com.ph.entidades.User;
import com.ph.exceptions.FilmeSemEstoqueException;
import com.ph.exceptions.LocadoraException;
import com.ph.utils.DataUtils;

public class RentService {
	
	public Rent rentMovie(User user, List<Movie> movies)
			throws FilmeSemEstoqueException, LocadoraException {
		if(user == null) {
			throw new LocadoraException("Empty user");
		}
		
		if(movies == null || movies.isEmpty()) {
			throw new LocadoraException("Empty movie");
		}

		for (Movie movie : movies) {
			if (movie.getStock() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}
		
		Rent rent = new Rent();
		rent.setMovies(movies);
		rent.setUser(user);
		rent.setRentDate(new Date());
		Double totalValue = 0d;
		for (Movie movie : movies){
			totalValue += movie.getRentPrice();
		}
		rent.setValue(totalValue);
		
		//Next day return
		Date returnDate = new Date();
		returnDate = DataUtils.adicionarDias(returnDate, 1);
		rent.setReturnDate(returnDate);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return rent;
	}
}