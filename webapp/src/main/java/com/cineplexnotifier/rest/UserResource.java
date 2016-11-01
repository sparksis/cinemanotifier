package com.cineplexnotifier.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cineplexnotifier.data.MovieRepository;
import com.cineplexnotifier.data.UserRepository;
import com.cineplexnotifier.model.Movie;
import com.cineplexnotifier.model.User;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class UserResource {

	@EJB
	private UserRepository dao;
	@EJB
	private MovieRepository movieRepository;

	@EJB
	private UserResource ejb;
	
	@POST
	@Path("{email}/subscribe")
	public void subscribe(@PathParam("email") String email, String... cineplexKeys) {
		User user = dao.selectByEmailAddress(email);

		// get database attached user
		user = dao.selectByEmailAddress(email);
		// if user doesn't exist sign them up automatically
		if (user == null) {
			user = signupUser(email);
		}

		List<Movie> movies = user.getMovies();
		for (String key : cineplexKeys) {
			movies.add(movieRepository.selectByCineplexKey(key));
		}

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public User signupUser(String email){
		User user = new User(email);
		dao.insert(user);
		// TODO send welcome email or create UserManagement EJB
		return user;
	}
}
