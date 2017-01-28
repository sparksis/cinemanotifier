package com.cineplexnotifier.services;

import java.io.Serializable;

import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;

import com.sendgrid.SendGrid;

/**
 * A generic CDI/EJB class which will allow us to change how we grab the SENDGRID_API_KEY at a later
 * date
 * 
 * This class expect an Environment Variable name <code>SENDGRID_API_KEY</code> to exist in order
 * for it to be able invoke the SendGrid api
 * 
 * @author colton
 *
 */
@Singleton
@SuppressWarnings("serial")
public class SendGridFactory implements Serializable {

  @Produces
  public SendGrid get() {
    return new SendGrid(System.getenv("SENDGRID_API_KEY"));
  }

}
