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

	@Deployment
	public static WebArchive createDeployment() {
		return ArquillianHelper.getDefaultShrinkWrap()
				.addClasses(
						RestApplication.class,
						MovieResource.class,
						MovieResourceBean.class
				);
	}

	@Test
	@RunAsClient
	@InSequence(value = 1)
	public void testMovieCreation(@ArquillianResource URL url) {
		Movie m = new Movie();
		m.setCineplexKey(MoviesResourceTest.class.getName() + "_testMovieCreation");
		m.setDescription("A movie");
		m.setThumbnailImageUrl("http://example.org/");
		
		MovieResource client = ArquillianHelper.createResteasyClientProxy(url, MovieResource.class);
		Response r = client.putMovie(m);
		assertEquals(Status.CREATED, Status.fromStatusCode(r.getStatus()));
	}

}
