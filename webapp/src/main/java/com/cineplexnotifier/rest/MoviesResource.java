package com.cineplexnotifier.rest;

import java.net.URI;
import java.net.URISyntaxException;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
		return movieDao.selectAll();
	}

	@POST
	@Path("/")
	public Response putMovie(Movie m) throws URISyntaxException {
		URI r = new URI(m.getCineplexKey());
		if (m.getId() == 0l && movieDao.insert(m) != 0l) {
			return Response.created(r).build();
		}
		throw new WebApplicationException(Status.BAD_REQUEST);
	}

	@GET
	@Path("{id}")
	public Movie getMovieByCineplexKey(@PathParam("id") String cineplexKey) {
		return movieDao.getMovieByCineplexKey(cineplexKey);
	}

}
