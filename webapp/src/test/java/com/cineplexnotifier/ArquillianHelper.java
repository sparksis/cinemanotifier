package com.cineplexnotifier;

import java.io.File;
import java.net.URL;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import com.cineplexnotifier.data.BaseRepository;
import com.cineplexnotifier.model.BaseModel;
import com.cineplexnotifier.services.NotificationService;

public class ArquillianHelper {

  public static Package[] getServicePackages() {
    return new Package[] {BaseModel.class.getPackage(), BaseRepository.class.getPackage(),
        NotificationService.class.getPackage(),};
  }

  public static File[] getMavenLibraries() {
    return Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
        .withTransitivity().asFile();
  }

  public static WebArchive getDefaultShrinkWrap() {
    return ShrinkWrap.create(WebArchive.class).addAsLibraries(ArquillianHelper.getMavenLibraries())
        .addPackages(false, ArquillianHelper.getServicePackages())
        .addAsResource("META-INF/persistence.xml")
        .addAsWebInfResource("arquillian-web.xml", "web.xml")
        .addAsWebInfResource(new File("src/main/webapp/WEB-INF/jboss-deployment-structure.xml"));
  }

  public static <T> T createResteasyClientProxy(URL url, Class<T> clazz) {
    // Append "rest" to the context root of the webapp
    final String restUrl = url + "rest";

    ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
    client.register(MOXyJsonProvider.class);
    ResteasyWebTarget target = (ResteasyWebTarget) client.target(restUrl);

    return target.proxy(clazz);
  }
}
