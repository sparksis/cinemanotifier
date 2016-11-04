#!/usr/bin/groovy

@Grapes([
    @Grab(group='net.sourceforge.htmlunit', module='htmlunit', version='2.13'),
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
])
import com.gargoylesoftware.htmlunit.WebClient;

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*


def init(){
  WebClient webClient = new WebClient();
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
    def listing = [:]

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
    return listing.getByXPath('//div[@class="single--body"]/h4[text()="Synopsis"]/following-sibling::p/text()').get(0).toString();
  }catch(RuntimeException re){
    return null;
  }
}

def findPosterImage(listing){
  //TODO
  return null;
}

def publish(movie){
  def http = new HTTPBuilder('http://localhost:8080')
  http.request( POST ) {
    uri.path = '/rest/movies/'
    requestContentType = JSON
    body =  movie

    response.success = { resp ->
      println "POST response status: ${resp.statusLine}"
      assert resp.statusLine.statusCode == 201
    }
  }
}

init();
for (int i = 1;i <= 20;i++) {
  println "Reading Page:         ${i}"
  def results = lookupMovies("http://www.cineplex.com/Movies/ComingSoon?cmpid=MainSubNavEN_coming-soon&page=${i}");

  results.each{
    println "Processing:           ${it.name} (${it.cineplexKey})";
    def listing = loadListing(it.cineplexKey);
    it.description = findDescription(listing);
    it.posterImage = findPosterImage(listing);

    publish(it);
  }
  Thread.sleep(3000);
}
