package com.cineplexnotifier.rest;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {

	@GET
	@Path("/")
	public List<String> getAllMovies() {

		return Arrays.asList("Doctor Strange");
	}

}
