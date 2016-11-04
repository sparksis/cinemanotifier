package com.cineplexnotifier.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.cache.Cache;

import com.cineplexnotifier.data.MovieRepository;

@Path("images")
@Produces("image/jpeg")
public class ImageResouce {

	public static final String MISSING_THUMBNAIL_URL = "http://mediafiles.cineplex.com/Cineplex2013/postermissing_230x341.jpg";

	@EJB
	private MovieRepository dao;

	@GET
	@Path("thumbnail/{cineplexKey}")
	@Cache
	public byte[] getThumbnail(@PathParam("cineplexKey") String key) throws IOException, URISyntaxException {
		String remoteUrl = dao.selectByCineplexKey(key).getThumbnailImageUrl();

		// Do not deliver the not found images from our server as they contain
		// the Cineplex logo
		if (MISSING_THUMBNAIL_URL.equals(remoteUrl)) {
			throw new RedirectionException(Status.MOVED_PERMANENTLY, new URI(remoteUrl));
		}

		URL remote = new URL(remoteUrl);
		InputStream stream = remote.openStream();
		byte[] r = IOUtils.toByteArray(stream);
		IOUtils.closeQuietly(stream);
		return r;
	}

}
