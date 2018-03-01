/**
 * 
 */
package com.example.marketplace.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author srikanthgummula
 *
 */
@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException>{

	private static final String message = "Invalid request. Field formats are invalid.";
	
	@Override
	public Response toResponse(JsonMappingException exception) {
		return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).
				entity(message).build();
	}

}
