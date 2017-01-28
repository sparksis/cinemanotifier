package com.cineplexnotifier.rest;

import java.net.URL;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cineplexnotifier.ArquillianHelper;
import com.cineplexnotifier.model.Movie;
import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class MoviesResourceTest {

  public static Movie buildTestMovie(Class<?> clazz, String methodName, int i) {
    final String MOVIE_NAME = MoviesResourceTest.class.getName() + "_" + methodName + i;
    Movie m = new Movie();
    m.setAvailable(false);
    m.setCineplexKey(MOVIE_NAME + "_KEY");
    m.setName(MOVIE_NAME + "_NAME");
    m.setDescription(MOVIE_NAME + "_DESCRIPTION");
    m.setThumbnailImageUrl("http://example.org/" + MOVIE_NAME);
    return m;
  }

  @Deployment
  public static WebArchive createDeployment() {
    return ArquillianHelper.getDefaultShrinkWrap().addClasses(RestApplication.class,
        MovieResource.class, MovieResourceBean.class);
  }

  @Test
  @RunAsClient
  @InSequence(value = 1)
  public void testPostMovieCreate(@ArquillianResource URL url) {
    Movie m = buildTestMovie(MoviesResourceTest.class, "testPostMovieCreate", 1);

    MovieResource client = ArquillianHelper.createResteasyClientProxy(url, MovieResource.class);
    Response r = client.postMovie(m);
    assertEquals(Status.CREATED, Status.fromStatusCode(r.getStatus()));
    r.close();

    Movie fromServer = client.getMovieByCineplexKey(m.getCineplexKey());
    assertEquals(m.isAvailable(), fromServer.isAvailable());
    assertEquals(m.getName(), fromServer.getName());
    assertEquals(m.getDescription(), fromServer.getDescription());
    assertEquals(m.getThumbnailImageUrl(), fromServer.getThumbnailImageUrl());
  }

  @Test
  @RunAsClient
  @InSequence(value = 2)
  public void testPostMovieUpdate(@ArquillianResource URL url) throws Exception {
    Movie m = buildTestMovie(MoviesResourceTest.class, "testPostMovieUpdate", 1);

    MovieResource client = ArquillianHelper.createResteasyClientProxy(url, MovieResource.class);

    // Put original movie
    Response r = client.postMovie(m);
    assertEquals(Status.CREATED, Status.fromStatusCode(r.getStatus()));
    r.close();

    m.setAvailable(true);
    m.setName(m.getName() + "UPDATED");
    m.setDescription(m.getDescription() + "UPDATED");
    m.setThumbnailImageUrl(m.getThumbnailImageUrl() + "UPDATED");

    // Put updated movie
    r = client.postMovie(m);
    assertEquals(Status.OK, Status.fromStatusCode(r.getStatus()));
    r.close();

    // Wait a second for async jobs to finish
    Thread.sleep(1000);

    Movie fromServer = client.getMovieByCineplexKey(m.getCineplexKey());
    assertEquals(m.isAvailable(), fromServer.isAvailable());
    assertEquals(m.getName(), fromServer.getName());
    assertEquals(m.getDescription(), fromServer.getDescription());
    assertEquals(m.getThumbnailImageUrl(), fromServer.getThumbnailImageUrl());
  }

}
