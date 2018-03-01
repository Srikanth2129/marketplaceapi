/**
 * 
 */
package com.example.marketplace.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;

/**
 * @author srikanthgummula
 *
 */
@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException>{

	private static final String message = "Invalid Json. Request cannot be parsed";
	
	@Override
	public Response toResponse(JsonParseException exception) {
		return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(message).build();
	}

}
