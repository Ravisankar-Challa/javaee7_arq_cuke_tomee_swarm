package com.example.exception;

/**
 * @author Ravisankar C
 *
 */
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException>
{
    @Override
    public Response toResponse(ApplicationException exception)
    {
    	Map<String, Object> error = new HashMap<>();
    	error.put("code", exception.getCode());
    	error.put("message", exception.getErrorMessage());
    	return Response.status(exception.getCode()).entity(error).type("application/error+json").build();
    }
}