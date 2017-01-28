package com.cineplexnotifier.rest;

import javax.ws.rs.ext.Provider;
import javax.ws.rs.RedirectionException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Suppress logging of RedirectExceptions to console
 * 
 * @author colton
 *
 */
@Provider
public class RedirectionExceptionMapper implements ExceptionMapper<RedirectionException> {

  @Override
  public Response toResponse(RedirectionException exception) {
    return exception.getResponse();
  }
}
