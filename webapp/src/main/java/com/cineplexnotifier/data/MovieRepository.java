package com.cineplexnotifier.data;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
	public EntityManager getEntityManager() {
		return em;
	}

	public Movie getMovieByCineplexKey(String key) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
		Root<Movie> root = cq.from(Movie.class);
		cq.select(root).where(cb.equal(root.get(Movie_.cineplexKey),key)).distinct(true);
		return em.createQuery(cq).getSingleResult();
	}

}
