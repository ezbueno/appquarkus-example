package com.buenoezandro.controller;

import com.buenoezandro.entity.UserEntity;
import com.buenoezandro.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path(value = "/users")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    public Response findAll(@QueryParam(value = "page") @DefaultValue(value = "0") Integer page,
                            @QueryParam(value = "pageSize") @DefaultValue(value = "10") Integer pageSize) {
        var users = this.userService.findAll(page, pageSize);
        return Response.ok(users).build();
    }

    @GET
    @Path(value = "/{id}")
    public Response findById(@PathParam(value = "id") UUID userId) {
        return Response.ok(this.userService.findById(userId)).build();
    }

    @POST
    @Transactional
    public Response createUser(UserEntity userEntity) {
        return Response.ok(this.userService.createUser(userEntity)).build();
    }

    @PUT
    @Path(value = "/{id}")
    @Transactional
    public Response updateUser(@PathParam(value = "id") UUID userId, UserEntity userEntity) {
        return Response.ok(this.userService.updateUser(userId, userEntity)).build();
    }

    @DELETE
    @Path(value = "/{id}")
    @Transactional
    public Response deleteById(@PathParam(value = "id") UUID userId) {
        this.userService.deleteById(userId);
        return Response.noContent().build();
    }
}