package com.example.student.exception;

import com.example.student.model.ApiResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class StudentNotFoundMapper implements ExceptionMapper<StudentNotFoundException> {

    @Override
    public Response toResponse(StudentNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(ApiResponse.error(ex.getMessage()))
                .build();
    }
}
