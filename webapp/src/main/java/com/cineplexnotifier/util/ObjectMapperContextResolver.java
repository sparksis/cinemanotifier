package com.cineplexnotifier.util;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Class to compensate for the Jackson's default of throwing an exception
 * whenever a new property is added to the client which may not be needed/used
 * by the server.  This class globally configures Jackson to behave more like JAXB/Moxy
 * 
 * @author colton
 *
 */
@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

	private final ObjectMapper mapper;

	public ObjectMapperContextResolver() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

}
