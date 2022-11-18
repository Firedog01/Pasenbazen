package pl.lodz.p.edu.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pl.lodz.p.edu.rest.model.Address;
import pl.lodz.p.edu.rest.model.DTO.RentDTO;
import pl.lodz.p.edu.rest.model.DTO.users.AdminDTO;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.repository.DataFaker;

public class RentControllerTest {

    @BeforeAll
    static void beforeAll() {
        baseURI = "http://localhost:8080/rest/api";
    }

    ObjectMapper obj = new ObjectMapper();
    RentDTO validRent;
    RentDTO unvalidRentData;
    RentDTO unvalidRentLogic;
    String validRentStr;
    String unvalidRentDataStr;
    String unvalidRentLogicStr;
    Client validClient;
    Equipment validEquipment;


    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validClient = DataFaker.getClient();
        validEquipment = DataFaker.getEquipment();
        String validClientStr = obj.writeValueAsString(validClient);
        String clientId = given()
                .header("Content-Type", "application/json")
                .body(validClientStr)
                .when()
                .post("/clients")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        String validEquipmentStr = obj.writeValueAsString(validEquipment);
        String equipmentId = given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        validRent = new RentDTO(clientId, equipmentId,
                "2023-04-05T12:38:35.585", "2023-05-05T12:38:35.585");
        validRentStr = obj.writeValueAsString(validRent);
        unvalidRentData = new RentDTO("c7428cb8-ed14-43bb-81b8-7ad06dad9b9e", equipmentId,
                "2023-06-06T12:38:35.585", "2024-07-07T12:38:35.585");
        unvalidRentDataStr = obj.writeValueAsString(unvalidRentData);
        unvalidRentLogic = new RentDTO("c7428cb8-ed14-43bb-81b8-7ad06dad9b9e", equipmentId,
                "2024-06-06T12:38:35.585", "2023-07-07T12:38:35.585");
        unvalidRentLogicStr = obj.writeValueAsString(unvalidRentLogic);
    }


//     create
    @Test
    void createRent_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validRentStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(201);
    }

    @Test
    void createRent_uncorrect_BADREQUEST() {
        given()
                .header("Content-Type", "application/json")
                .body(unvalidRentDataStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(400);
    }

    @Test
    void createRent_uncorrect_CONFLICT() {
        given()
                .header("Content-Type", "application/json")
                .body(unvalidRentLogicStr)
                .when()
                .post("/rents")
                .then()
                .statusCode(409);
    }

    // get all
    @Test
    void getAllRents() {

    }
}
