/**
 * 
 */
package com.example.marketplace.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

/**
 * @author srikanthgummula
 *
 */
@Provider
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException>{

	@Override
	public Response toResponse(UnrecognizedPropertyException exception) {
		return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).
				entity("Invalid request. The property " + exception.getPropertyName() + " is invalid.").build();
	}

}
