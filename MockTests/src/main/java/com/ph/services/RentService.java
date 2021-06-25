package com.ph.services;

import java.util.Date;
import java.util.List;

import com.ph.daos.RentDAO;
import com.ph.entities.Movie;
import com.ph.entities.Rent;
import com.ph.entities.User;
import com.ph.exceptions.MovieOutOfStockException;
import com.ph.exceptions.RentException;
import com.ph.utils.DataUtils;

public class RentService {

	private RentDAO dao;
	private SPCService spcService;
	
	public Rent rentMovie(User user, List<Movie> movies)
			throws MovieOutOfStockException, RentException {
		if(user == null) {
			throw new RentException("Empty user");
		}

		if(movies == null || movies.isEmpty()) {
			throw new RentException("Empty movie");
		}

		for (Movie movie : movies) {
			if (movie.getStock() == 0) {
				throw new MovieOutOfStockException();
			}
		}

		if (spcService.isNegative(user)){
			throw new RentException("Negative User");
		}
		
		Rent rent = new Rent();
		rent.setMovies(movies);
		rent.setUser(user);
		rent.setRentDate(new Date());
		Double totalValue = 0d;
		for(int i = 0; i < movies.size(); i++) {
			Movie movie = movies.get(i);
			Double movieValue = movie.getRentPrice();
			switch (i) {
				case 2: movieValue = movieValue * 0.75; break;
				case 3: movieValue = movieValue * 0.5; break;
				case 4: movieValue = movieValue * 0.25; break;
				case 5: movieValue = 0d; break;
			}
			totalValue += movieValue;
		}
		rent.setValue(totalValue);
		
		//Next day return
		Date returnDate = new Date();
		returnDate = DataUtils.addDays(returnDate, 1);
		rent.setReturnDate(returnDate);

		dao.save(rent);
		
		return rent;
	}


	public void setRentDao(RentDAO dao) {
		this.dao = dao;
	}

	public void setSpcService(SPCService spcService){
		this.spcService = spcService;
	}
}