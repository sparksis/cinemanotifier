package com.cineplexnotifier.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.Cache;

import com.cineplexnotifier.data.MovieRepository;
import com.cineplexnotifier.model.Movie;

@Path("movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {
	@EJB
	private MovieRepository movieDao;

	@GET
	@Path("/")
	@Cache(maxAge = 600)
	public List<Movie> getAllMovies() {
		return movieDao.getAll();
	}

}
