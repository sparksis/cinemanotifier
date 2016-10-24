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

	public Movie getMovieByCineplexKey(String key) {
		//TODO create typesafe query
		return (Movie) em.createQuery("select m from Movie m where m.cineplexKey = :cineplexKey")
				.setParameter("cineplexKey", key).getSingleResult();
	}

	public List<Movie> getAll() {
		CriteriaQuery<Movie> cq = em.getCriteriaBuilder().createQuery(Movie.class);
		cq.select(cq.from(Movie.class));
		return em.createQuery(cq).getResultList();
	}

	public long addMovie(Movie m) {
		// TODO this really should follow the standard repository design pattern
		// and use generics.
		em.persist(m);
		return m.getId();
	}

}
