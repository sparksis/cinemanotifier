package com.cineplexnotifier.services;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cineplexnotifier.model.BaseModel;
import com.cineplexnotifier.model.Movie;
import com.cineplexnotifier.model.User;
import com.sendgrid.SendGrid;

/**
 * 
 * @author colton
 *
 */
@RunWith(Arquillian.class)
public class NotificationServiceTest {

	@Deployment
	public static WebArchive createDeployment() {
		File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().asFile();
		
		Logger.getAnonymousLogger().info(Arrays.toString(files));

		return ShrinkWrap.create(WebArchive.class).addPackages(false, NotificationService.class.getPackage(),
				BaseModel.class.getPackage(), SendGrid.class.getPackage()).addAsLibraries(files);
	}

	@EJB
	private NotificationService instance;

	@Test
	public void testNotiySubscribers() throws Exception {
		User user = new User("colton@cineplexnotifier.com");
		Movie m = new Movie();
		m.setName("Test Movie");
		m.setCineplexKey("test-movie");
		m.setAvailable(true);
		m.getUsers().add(user);

		instance.notifySubscribers(m);

		Thread.sleep(2000);
	}

}
