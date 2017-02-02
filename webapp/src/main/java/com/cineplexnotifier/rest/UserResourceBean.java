package com.cineplexnotifier.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.PathParam;
import com.cineplexnotifier.data.MovieRepository;
import com.cineplexnotifier.data.UserRepository;
import com.cineplexnotifier.model.Movie;
import com.cineplexnotifier.model.User;

@Stateless
@Local(UserResource.class)
public class UserResourceBean implements UserResource {

  @EJB
  private UserRepository dao;
  @EJB
  private MovieRepository movieRepository;

  @EJB
  private UserResourceBean ejb;

  /* (non-Javadoc)
   * @see com.cineplexnotifier.rest.IUserResource#subscribe(java.lang.String, java.lang.String)
   */
  @Override
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
  public User signupUser(String email) {
    User user = new User(email);
    dao.insert(user);
    // TODO send welcome email or create UserManagement EJB
    return user;
  }
}
