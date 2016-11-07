package com.cineplexnotifier.services.timers;

import org.junit.Test;

public class TestScrapeService {

	@Test
	public void testRun() {
		ScrapeService scrapeService = new ScrapeService();
		scrapeService.run();
	}
}
