package com.example.student.resource;

import com.example.student.model.ApiResponse;
import com.example.student.model.Student;
import com.example.student.service.StudentService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Student REST resource.
 *
 * GET    /api/students                   — list all (optional ?major=X filter)
 * GET    /api/students/{id}              — get one
 * GET    /api/students/search?q=...      — search by name/email
 * POST   /api/students                   — create
 * PUT    /api/students/{id}              — full update
 * PATCH  /api/students/{id}              — partial update
 * DELETE /api/students/{id}              — delete
 */
@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    private final StudentService service = StudentService.getInstance();

    // ── GET /api/students  (optional ?major=Computer+Science) ────────────────
    @GET
    public Response getAll(@QueryParam("major") String major) {
        List<Student> list = service.findAll(major);
        String msg = major == null
                ? "Retrieved " + list.size() + " students"
                : "Retrieved " + list.size() + " students in major: " + major;
        return Response.ok(ApiResponse.ok(msg, list)).build();
    }

    // ── GET /api/students/search?q=alice ─────────────────────────────────────
    @GET
    @Path("/search")
    public Response search(@QueryParam("q") String query) {
        if (query == null || query.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("Query parameter 'q' is required"))
                    .build();
        }
        List<Student> results = service.search(query);
        return Response.ok(ApiResponse.ok("Found " + results.size() + " result(s)", results)).build();
    }

    // ── GET /api/students/{id} ───────────────────────────────────────────────
    @GET
    @Path("/{id}")
    public Response getOne(@PathParam("id") int id) {
        Student student = service.findById(id); // throws StudentNotFoundException if missing
        return Response.ok(ApiResponse.ok("Student found", student)).build();
    }

    // ── POST /api/students ───────────────────────────────────────────────────
    @POST
    public Response create(Student student) {
        if (student == null || student.getFirstName() == null || student.getLastName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("firstName and lastName are required"))
                    .build();
        }
        Student created = service.create(student);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.ok("Student created successfully", created))
                .build();
    }

    // ── PUT /api/students/{id} ───────────────────────────────────────────────
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Student student) {
        if (student == null || student.getFirstName() == null || student.getLastName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("firstName and lastName are required"))
                    .build();
        }
        Student updated = service.update(id, student);
        return Response.ok(ApiResponse.ok("Student updated successfully", updated)).build();
    }

    // ── PATCH /api/students/{id} ─────────────────────────────────────────────
    @PATCH
    @Path("/{id}")
    public Response patch(@PathParam("id") int id, Student partial) {
        Student patched = service.patch(id, partial);
        return Response.ok(ApiResponse.ok("Student partially updated", patched)).build();
    }

    // ── DELETE /api/students/{id} ────────────────────────────────────────────
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        service.delete(id);
        return Response.ok(ApiResponse.ok("Student deleted successfully", null)).build();
    }
}
