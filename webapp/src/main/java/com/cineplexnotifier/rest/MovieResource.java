package com.cineplexnotifier.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.Cache;

import com.cineplexnotifier.model.Movie;

@Path("movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface MovieResource {

	@GET
	@Path("/")
	@Cache(maxAge = 600)
	List<Movie> getMovies(@DefaultValue(value = "false") @QueryParam("all") boolean available);

	@POST
	@Path("/")
	Response putMovie(Movie m);

	@GET
	@Path("{id}")
	Movie getMovieByCineplexKey(String cineplexKey);

}