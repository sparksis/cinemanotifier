package com.cineplexnotifier.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cineplexnotifier.data.MovieRepository;
import com.cineplexnotifier.model.Movie;

@Path("movies")
public class MoviesResource implements IMoviesResource {
	@EJB
	private MovieRepository movieDao;

	@Override
	public List<Movie> getMovies(@DefaultValue(value = "false") @QueryParam("all") boolean available) {
		return movieDao.selectByAvailability(false);
	}

	@Override
	public Response putMovie(Movie m) {
		URI r;
		try{
			 r = new URI(m.getCineplexKey());
		}catch(URISyntaxException e){
			throw new WebApplicationException("Bad cineplexKey", Status.INTERNAL_SERVER_ERROR);
		}
		Movie old = movieDao.selectByCineplexKey(m.getCineplexKey());
		if (old != null) {
			old.merge(m);
			movieDao.update(old);

			// TODO formalize REST conventions used by this app
			return Response.created(r).build();
		} else if (m.getId() == 0l && movieDao.insert(m) != 0l) {
			return Response.created(r).build();
		}
		throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
	}

	@Override
	public Movie getMovieByCineplexKey(@PathParam("id") String cineplexKey) {
		return movieDao.selectByCineplexKey(cineplexKey);
	}

}
