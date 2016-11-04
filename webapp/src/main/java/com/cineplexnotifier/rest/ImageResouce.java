package com.cineplexnotifier.rest;

import java.io.IOException;
import java.net.URL;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.cache.Cache;

import com.cineplexnotifier.data.MovieRepository;

@Path("images")
@Produces("image/jpeg")
public class ImageResouce {
	
	@EJB
	private MovieRepository dao;
	
	@GET
	@Path("thumbnail/{cineplexKey}")
	@Cache
	public byte[] getThumbnail(@PathParam("cineplexKey") String key) throws IOException {
		URL remoteUrl = new URL(dao.selectByCineplexKey(key).getThumbnailImageUrl());
		return IOUtils.toByteArray(remoteUrl.openStream());
	}

}
