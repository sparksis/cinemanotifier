package com.cineplexnotifier.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.Cache;

import com.cineplexnotifier.data.MovieRepository;
import com.cineplexnotifier.model.Movie;

@Path("movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MoviesResource {
	@EJB
	private MovieRepository movieDao;

	@GET
	@Path("/")
	@Cache(maxAge = 600)
	public List<Movie> getAllMovies() {
		return movieDao.getAll();
	}

	@POST
	@Path("/")
	public void putMovie(Movie m) {
		// TODO Validation
		if (m.getId() != 0l) {
			throw new WebApplicationException(400);
		}
		if (movieDao.addMovie(m) != 0l) {
			throw new WebApplicationException(201);
		}
	}

	@GET
	@Path("{id}")
	public Movie getMovieByCineplexKey(@PathParam("id") String cineplexKey) {
		return movieDao.getMovieByCineplexKey(cineplexKey);
	}

}
