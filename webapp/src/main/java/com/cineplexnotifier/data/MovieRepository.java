package com.cineplexnotifier.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.cineplexnotifier.model.Movie;
import com.cineplexnotifier.model.Movie_;

@Stateless
public class MovieRepository extends BaseRepository<Movie> {

  @PersistenceContext
  private EntityManager em;

  public MovieRepository() {
    super(Movie.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public List<Movie> selectByAvailability(boolean available) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
    Root<Movie> root = cq.from(Movie.class);
    cq.select(root).where(cb.equal(root.get(Movie_.available), available));
    return em.createQuery(cq).getResultList();
  }

  public Movie selectByCineplexKey(String key) {
    try {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
      Root<Movie> root = cq.from(Movie.class);
      cq.select(root).where(cb.equal(root.get(Movie_.cineplexKey), key)).distinct(true);
      return em.createQuery(cq).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

}
