package com.cineplexnotifier.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cineplexnotifier.data.MovieRepository;
import com.cineplexnotifier.model.Movie;
import com.cineplexnotifier.services.NotificationService;

@Stateless
@Local(MovieResource.class)
public class MovieResourceBean implements MovieResource {
	@EJB
	private MovieRepository movieDao;
	@EJB
	private NotificationService notificationService;

	@Override
	public List<Movie> getMovies(boolean available) {
		return movieDao.selectByAvailability(false);
	}

	@Override
	public Response postMovie(Movie m) {
		URI r;
		try {
			r = new URI(m.getCineplexKey());
		} catch (URISyntaxException e) {
			throw new WebApplicationException("Bad cineplexKey", Status.INTERNAL_SERVER_ERROR);
		}
		Movie dbMovie = movieDao.selectByCineplexKey(m.getCineplexKey());
		if (dbMovie == null) {
			movieDao.insert(m);
			return Response.created(r).build();
		} else {
			// First check to see if the movie is newly available
			boolean notifyUsers = m.isAvailable() && !dbMovie.isAvailable();

			dbMovie.merge(m);
			movieDao.update(dbMovie);

			if (notifyUsers) {
				notificationService.notifySubscribers(dbMovie.getId());
			}
			return Response.ok(r).build();
		}
	}

	@Override
	public Movie getMovieByCineplexKey(String cineplexKey) {
		return movieDao.selectByCineplexKey(cineplexKey);
	}

}
