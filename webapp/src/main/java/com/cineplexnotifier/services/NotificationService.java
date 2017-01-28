package com.cineplexnotifier.services;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.cineplexnotifier.data.MovieRepository;
import com.cineplexnotifier.model.Movie;
import com.cineplexnotifier.model.User;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Stateless
@SuppressWarnings("serial")
public class NotificationService implements Serializable {

  @Inject
  private SendGrid sendGrid;

  @EJB
  private MovieRepository movieRepository;

  private static final String SUBJECT_MOVIE_NOW_AVAILABLE =
      "Tickets for %s are now available from cineplex.com!";
  private static final String BODY_MOVIE_NOW_AVAILABLE = SUBJECT_MOVIE_NOW_AVAILABLE;

  @Asynchronous
  public void notifySubscribers(long movieId) {
    Movie m = movieRepository.selectById(movieId);
    notifySubscribers(m);
  }

  /**
   * Sends an email to all users who have requested that they be emailed when a movie becomes
   * available
   * 
   * @param movieId
   */
  public void notifySubscribers(Movie m) {
    if (m.isAvailable()) {
      for (User u : m.getUsers()) {
        Email from = new Email("no-reply@cineplexnotifier.com");
        Email to = new Email(u.getEmail());

        String subject = String.format(SUBJECT_MOVIE_NOW_AVAILABLE, m.getName());
        Content content =
            new Content("text/html", String.format(BODY_MOVIE_NOW_AVAILABLE, m.getName()));

        Mail mail = new Mail(from, subject, to, content);
        mail.setTemplateId("db73998c-d70d-4301-9e10-f2e68b31ecdf");

        Personalization p = mail.getPersonalization().get(0);
        p.addSubstitution("-movieName-", m.getName());
        p.addSubstitution("-cineplexKey-", m.getCineplexKey());

        mail.sendAt();

        Request request = new Request();
        try {
          request.method = Method.POST;
          request.endpoint = "mail/send";
          request.body = mail.build();
          Response response = sendGrid.api(request);
          if (response.statusCode < 200 || response.statusCode >= 300) {
            throw new IOException(response.toString());
          }
        } catch (IOException e) {
          Logger.getLogger(this.getClass().getName()).error(e);
        }
      }
      m.getUsers().clear();
    }
  }

}
