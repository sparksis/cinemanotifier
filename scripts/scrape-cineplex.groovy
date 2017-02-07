#!/usr/bin/groovy
package com.cineplexnotifier.scripts;
@Grapes([
  @Grab(group='net.sourceforge.htmlunit', module='htmlunit', version='2.13'),
  
  // Needed for rest-easy json serialization
  @Grab(group='org.eclipse.persistence', module='org.eclipse.persistence.moxy', version='2.6.4'),
  //needed for the rest components in the ejb-client
  @Grab(group='org.jboss.resteasy', module='resteasy-client', version='3.0.19.Final'),
  @Grab(group='com.cineplexnotifier', module='webapp', version='1.0-SNAPSHOT', classifier='ejb-client'),
])
import com.gargoylesoftware.htmlunit.WebClient;

import org.jboss.resteasy.client.jaxrs.*;

import com.cineplexnotifier.model.*;
import com.cineplexnotifier.rest.*;


public class ScrapeOrigin{
  WebClient webClient;

  public ScrapeOrigin(){
    webClient = new WebClient();
    webClient.getOptions().setJavaScriptEnabled(false);
    webClient.getOptions().setThrowExceptionOnScriptError(false);
    java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
    this.webClient = webClient;
  }


  def lookupMovies(pageUrl){
    def listingsPage = webClient.getPage(pageUrl);
    def listings = listingsPage.querySelectorAll('.showtime-card');
    def r = [];
    listings.each{
      def listing = new Movie();

      listing.available = it.getTextContent().contains("Buy Tickets");

      def titleNode = it.querySelector('.showtime-card--title > a');
      def uri = titleNode.getAttributes().getNamedItem("href").getValue();
      listing.cineplexKey = uri.substring(uri.lastIndexOf("/")+1,uri.length());
      listing.name = titleNode.getTextContent();

      listing.thumbnailImageUrl = it.querySelector('img.dbsmallposter').getAttributes().getNamedItem("src").getValue();

      r.push(listing);
    }

    return r;
  }

  def loadListing(id){
    return webClient.getPage("https://cineplex.com/Movie/${id}");
  }

  def findDescription(listing){
    try{
      def nodes = listing.getByXPath('//div[@class="single--body"]/h4[text()="Synopsis"]/following-sibling::p/text()');
      return nodes.get(0).toString();
    } catch(RuntimeException re) {
        return null;
    }
  }
  
  def findReleaseDate(listing){
  	final def searchPattern = ~'(January|February|March|April|May|June|July|August|September|October|November|December) \\d{1,2}, 20\\d\\d'
  	final def simpleCalendarPattern = java.time.format.DateTimeFormatter.ofPattern("MMMM d',' yyyy");
  	
    def infoNode = listing.querySelector('p.movie-info');
    
    
    def date = (infoNode.textContent=~ searchPattern)[0][0]
    
    return java.time.LocalDate.parse((java.lang.CharSequence)date, (java.time.format.DateTimeFormatter)simpleCalendarPattern);
  }

  def publish(baseUrl, movie){
	ResteasyClient client = new ResteasyClientBuilder().build();
    client.register(org.eclipse.persistence.jaxb.rs.MOXyJsonProvider.class);
	ResteasyWebTarget target = client.target(baseUrl+"/rest");
	
	MovieResource movieResource = target.proxy(MovieResource.class);
	
	movieResource.postMovie(movie);
  }

  public static void main(String... args){
    def targetUrl = 'http://localhost:8080';
    if(args.length>0) {
      targetUrl = args[0];
    }
    def THIS = new ScrapeOrigin();
    for (int i = 1;i <= 20;i++) {
      println "Reading Page:         ${i}"
      def results = THIS.lookupMovies("http://www.cineplex.com/Movies/ComingSoon?cmpid=MainSubNavEN_coming-soon&page=${i}");

      results.each{
        println "Processing:           ${it.name} (${it.cineplexKey})";
        def listing = THIS.loadListing(it.cineplexKey);
        it.setReleaseDate(THIS.findReleaseDate(listing));
        it.description = THIS.findDescription(listing);
          THIS.publish(targetUrl, it);
      }
      Thread.sleep(3000);
    }
  }
}
