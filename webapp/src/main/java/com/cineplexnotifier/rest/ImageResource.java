package com.cineplexnotifier.rest;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.cache.Cache;

@Path("images")
@Produces("image/jpeg")
public interface ImageResource {

  String MISSING_THUMBNAIL_URL =
      "http://mediafiles.cineplex.com/Cineplex2013/postermissing_230x341.jpg";
  String REPLACEMENT_MISSING_THUMBNAIL_URL = "../resources/images/imagenotavailable.png";
  @GET
  @Path("thumbnail/{cineplexKey}.jpg")
  @Cache
  byte[] getThumbnail(String key) throws IOException, URISyntaxException;

}
