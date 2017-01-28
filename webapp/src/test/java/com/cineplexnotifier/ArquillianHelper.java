package com.cineplexnotifier;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import com.cineplexnotifier.data.BaseRepository;
import com.cineplexnotifier.model.BaseModel;
import com.cineplexnotifier.services.NotificationService;

public class ArquillianHelper {

	public static Package[] getServicePackages() {
		return new Package[] { 
				BaseModel.class.getPackage(),
				BaseRepository.class.getPackage(),
				NotificationService.class.getPackage(),
		};
	}
	
	public static File[] getMavenLibraries(){
		return  Maven.resolver()
				.loadPomFromFile("pom.xml")
				.importRuntimeDependencies()
				.resolve().withTransitivity()
				.asFile();
	}

	public static WebArchive getDefaultShrinkWrap(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsLibraries(ArquillianHelper.getMavenLibraries())
				.addPackages(false, ArquillianHelper.getServicePackages())
				.addAsResource("META-INF/persistence.xml");
	}
	
}
