package com.cineplexnotifier.util;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {

  @PersistenceContext
  private EntityManager em;

  @Produces
  @Default
  public EntityManager getEntityManager() {
    return em;
  }

}
