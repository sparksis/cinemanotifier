package com.cineplexnotifier.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.cineplexnotifier.model.BaseModel;
import com.cineplexnotifier.model.BaseModel_;

public abstract class BaseRepository<T extends BaseModel> {

  private Class<T> clazz;

  public BaseRepository(Class<T> clazz) {
    this.clazz = clazz;
  }


  public List<T> selectAll() {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(clazz);
    cq.select(cq.from(clazz));
    return getEntityManager().createQuery(cq).getResultList();
  }

  public T selectById(long id) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(clazz);
    Root<T> root = cq.from(clazz);
    cq.select(root).where(cb.equal(root.get(BaseModel_.id), id));
    return getEntityManager().createQuery(cq).getSingleResult();
  }

  protected abstract EntityManager getEntityManager();

  public long insert(T model) {
    getEntityManager().persist(model);
    return model.getId();
  }

  public void update(T model) {
    getEntityManager().merge(model);
  }

  public void delete(T model) {
    getEntityManager().remove(model);
  }

}
