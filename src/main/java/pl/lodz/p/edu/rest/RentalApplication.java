package pl.lodz.p.edu.rest;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class RentalApplication extends Application {
//    EntityManagerFactory factory = Persistence.createEntityManagerFactory("RENT");
}