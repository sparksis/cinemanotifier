package com.cineplexnotifier.rest;

import java.net.URL;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cineplexnotifier.ArquillianHelper;
import com.cineplexnotifier.model.Movie;

@RunWith(Arquillian.class)
public class MoviesResourceTest {

	@Deployment
	public static WebArchive createDeployment() {
		return ArquillianHelper.getDefaultShrinkWrap()
				.addClasses(
						MovieResource.class,
						MovieResourceBean.class
				);
	}

	@Test
	@RunAsClient
	public void testRest(@ArquillianResource URL url) {
		ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
		client.register(MOXyJsonProvider.class);
		ResteasyWebTarget target = (ResteasyWebTarget) client.target(url.toString());

		MovieResource simple = target.proxy(MovieResource.class);
		simple.putMovie(new Movie());
	}

}
