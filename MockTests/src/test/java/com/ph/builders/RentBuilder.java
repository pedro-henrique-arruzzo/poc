package com.ph.builders;

import com.ph.entities.Movie;
import com.ph.entities.Rent;
import com.ph.entities.User;
import com.ph.utils.DataUtils;

import java.util.Arrays;
import java.lang.Double;
import java.util.Date;

public class RentBuilder {
	private Rent element;
	private RentBuilder(){}

	public static RentBuilder aRent() {
		RentBuilder builder = new RentBuilder();
		inicializarDadosPadroes(builder);
		return builder;
	}

	public static void inicializarDadosPadroes(RentBuilder builder) {
		builder.element = new Rent();
		Rent element = builder.element;

		
		element.setUser(UserBuilder.aUser().now());
		element.setMovies(Arrays.asList(MovieBuilder.aMovie().now()));
		element.setRentDate(new Date());
		element.setReturnDate(DataUtils.obtainDataInDifferenceInDays(1));
		element.setValue(4.0);
	}

	public RentBuilder withUser(User param) {
		element.setUser(param);
		return this;
	}

	public RentBuilder withMovieList(Movie... params) {
		element.setMovies(Arrays.asList(params));
		return this;
	}

	public RentBuilder withRentDate(Date param) {
		element.setRentDate(param);
		return this;
	}

	public RentBuilder withReturnDate(Date param) {
		element.setReturnDate(param);
		return this;
	}

	public RentBuilder withValue(Double param) {
		element.setValue(param);
		return this;
	}

	public Rent now() {
		return element;
	}
}
