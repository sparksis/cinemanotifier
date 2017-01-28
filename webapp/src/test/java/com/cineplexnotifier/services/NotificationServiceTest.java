package com.cineplexnotifier.services;

import java.io.File;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cineplexnotifier.data.MovieRepository;
import com.cineplexnotifier.model.Movie;
import com.cineplexnotifier.model.User;
import static org.junit.Assert.*;

/**
 * 
 * @author colton
 *
 */
@RunWith(Arquillian.class)
public class NotificationServiceTest {

	@Deployment
	public static WebArchive createDeployment() {
		File[] files = Maven.resolver()
				.loadPomFromFile("pom.xml")
				.importRuntimeDependencies()
				.resolve().withTransitivity()
				.asFile();

		return ShrinkWrap.create(WebArchive.class)
				.addPackages(false, 
						NotificationService.class.getPackage(),
						MovieRepository.class.getPackage(),
						Movie.class.getPackage()
				).addAsResource("META-INF/persistence.xml")
				.addAsLibraries(files);
	}

	@EJB
	private NotificationService instance;

	@Test
	public void testNotifySubscribers() throws Exception {
		User user = new User("colton@cineplexnotifier.com");
		Movie m = new Movie();
		m.setName("Test Movie");
		m.setCineplexKey("test-movie");
		m.setAvailable(true);
		m.getUsers().add(user);
		
		instance.notifySubscribers(m);
		
		assertTrue(m.getUsers().isEmpty());
	}

}
