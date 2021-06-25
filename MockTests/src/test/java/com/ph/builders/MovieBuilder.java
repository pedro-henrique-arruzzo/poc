package com.ph.builders;

import com.ph.entities.Movie;

public class MovieBuilder {

	private Movie movie;
	
	private MovieBuilder(){}
	
	public static MovieBuilder aMovie(){
		MovieBuilder builder = new MovieBuilder();
		builder.movie = new Movie();
		builder.movie.setStock(2);
		builder.movie.setName("Movie 1");
		builder.movie.setRentPrice(4.0);
		return builder;
	}
	
	public static MovieBuilder noStockMovie(){
		MovieBuilder builder = new MovieBuilder();
		builder.movie = new Movie();
		builder.movie.setStock(0);
		builder.movie.setName("Movie 1");
		builder.movie.setRentPrice(4.0);
		return builder;
	}
	
	public MovieBuilder noStock(){
		movie.setStock(0);
		return this;
	}
	
	public MovieBuilder withValue(Double valor) {
		movie.setRentPrice(valor);
		return this;
	}
	
	public Movie now(){
		return movie;
	}

}