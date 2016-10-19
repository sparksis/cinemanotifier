package com.cineplexnotifier.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import com.cineplexnotifier.model.Movie;

@Stateless
public class MovieRepository {
	
	@PersistenceContext
	private EntityManager em;

	public List<Movie> getAll() {
		CriteriaQuery<Movie> cq = em.getCriteriaBuilder().createQuery(Movie.class);
		cq.select(cq.from(Movie.class));
		return em.createQuery(cq).getResultList();
	}

}
