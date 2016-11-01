package com.cineplexnotifier.data;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.cineplexnotifier.model.User;
import com.cineplexnotifier.model.User_;

@Stateless
public class UserRepository extends BaseRepository<User> {

	@PersistenceContext
	private EntityManager em;
	
	public UserRepository() {
		super(User.class);
	}
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public User selectByEmailAddress(String emailAddress){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root).where(cb.equal(root.get(User_.email), emailAddress));
		try{
			return em.createQuery(cq).getSingleResult();
		}catch(javax.persistence.NoResultException e){
			return null;
		}

	}
	
}
