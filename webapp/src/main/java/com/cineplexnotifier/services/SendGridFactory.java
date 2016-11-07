package com.cineplexnotifier.services;

import java.io.Serializable;

import javax.ejb.Singleton;
import com.sendgrid.SendGrid;

/**
 * 
 * @author colton
 *
 */
@Singleton
@SuppressWarnings("serial")
public class SendGridFactory implements Serializable {

	public SendGridFactory() {
	}

	public SendGrid get() {
		return new SendGrid(System.getenv("SENDGRID_API_KEY"));
	}

}
