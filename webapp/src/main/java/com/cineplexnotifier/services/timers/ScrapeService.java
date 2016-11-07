package com.cineplexnotifier.services.timers;

import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;

import groovy.lang.GroovyShell;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.ejb.Schedule;

@Stateless
public class ScrapeService {

	@Schedule(hour = "*/6", minute = "12")
	public void run() {
		GroovyShell shell = new GroovyShell();
		Reader reader = new InputStreamReader(
				this.getClass().getResourceAsStream("/com/cineplexnotifier/scripts/ScrapeOrigin.groovy"),
				Charset.forName("UTF-8"));
		shell.evaluate(reader);
		IOUtils.closeQuietly(reader);
	}
}
