package com.cineplexnotifier.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.PathParam;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import com.cineplexnotifier.data.MovieRepository;

@Stateless
@Local(ImageResource.class)
public class ImageResourceBean implements ImageResource {

  @EJB
  private MovieRepository dao;

  /* (non-Javadoc)
   * @see com.cineplexnotifier.rest.IImageResource#getThumbnail(java.lang.String)
   */
  @Override
  public byte[] getThumbnail(String key)
      throws IOException, URISyntaxException {
    String remoteUrl = dao.selectByCineplexKey(key).getThumbnailImageUrl();

    // Do not deliver the not found images from our server as they contain
    // the Cineplex logo
    if (MISSING_THUMBNAIL_URL.equals(remoteUrl)) {
      throw new RedirectionException(Status.MOVED_PERMANENTLY,
          new URI(REPLACEMENT_MISSING_THUMBNAIL_URL));
    }

    URL remote = new URL(remoteUrl);
    InputStream stream = remote.openStream();
    byte[] r = IOUtils.toByteArray(stream);
    IOUtils.closeQuietly(stream);
    return r;
  }

}
