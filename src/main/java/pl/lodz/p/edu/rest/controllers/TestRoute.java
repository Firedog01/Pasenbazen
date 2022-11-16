package pl.lodz.p.edu.rest.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/test")
public class TestRoute {

    protected TestRoute() {}

    @GET
    @Path("/hi")
    public String hello() {
        return "hello";
    }
}
