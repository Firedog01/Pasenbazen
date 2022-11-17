package pl.lodz.p.edu.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.rest.model.DTO.EquipmentDTO;
import pl.lodz.p.edu.rest.repository.DataFaker;

import java.util.UUID;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class EquipmentControllerTest {
    @BeforeAll
    static void beforeAll() {
        baseURI = "http://localhost:8080/rest/api";
    }

    ObjectMapper obj = new ObjectMapper();
    EquipmentDTO validEquipment;
    String validEquipmentStr;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        validEquipment = new EquipmentDTO(DataFaker.getEquipment());
        validEquipmentStr = obj.writeValueAsString(validEquipment);
    }

    // create

    @Test
    void createEquipment_correct() {
        given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .statusCode(201);
    }

    @Test
    void createEquipment_noBody() {
        given()
                .header("Content-Type", "application/json")
                .body("")
                .when()
                .post("/equipment")
                .then()
                .statusCode(400);
    }

    @Test
    void createEquipment_illegalValue() throws JsonProcessingException {
        validEquipment.setFirstDayCost(-1000);
        String notValidEquipmentStr = obj.writeValueAsString(validEquipment);
        System.out.println(notValidEquipmentStr);
        given()
                .header("Content-Type", "application/json")
                .body(notValidEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .statusCode(400);
    }

    @Test
    void createEquipment_conflict() {
        given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .statusCode(201);
        given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .statusCode(409);
    }

    // read
    @Test
    void getAllEquipments_correct()  {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/equipment")
                .then()
                .statusCode(200);
    }

    @Test
    void getOneEquipment_byUUID_correct()  {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .extract().path("entityId");
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/equipment/" + uuid)
                .then()
                .statusCode(200)
                .body("entityId", equalTo(uuid));
    }

    @Test
    void getOneEquipment_byUUID_noSuchUUID()  {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/equipment/" + uuid)
                .then()
                .statusCode(404);
    }

    // update
    @Test
    void updateOneEquipment_correct() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .statusCode(201)
                .extract().path("entityId");

        String newName = "___other_name___";
        validEquipment.setName(newName);
        String updatedEquipmentStr = obj.writeValueAsString(validEquipment);
        given()
                .header("Content-Type", "application/json")
                .body(updatedEquipmentStr)
                .when()
                .put("/equipment/" + uuid)
                .then()
                .statusCode(200);
        String name = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/equipment/" + uuid)
                .then()
                .statusCode(200)
                .extract().path("name");
        assertEquals(newName, name);
    }

    @Test
    void updateOneEquipment_noEquipment() throws JsonProcessingException {
        String uuid = UUID.randomUUID().toString();
        given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .put("/equipment/" + uuid)
                .then()
                .statusCode(404);
    }

    @Test
    void updateOneEquipment_illegalValues() throws JsonProcessingException {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .statusCode(201)
                .extract().path("entityId");
        validEquipment.setName(null);
        String updatedLogin = obj.writeValueAsString(validEquipment);
        given()
                .header("Content-Type", "application/json")
                .body(updatedLogin)
                .when()
                .put("/equipment/" + uuid)
                .then()
                .statusCode(400);
    }
    
    @Test
    void deleteOneEquipment_correct() {
        String uuid = given()
                .header("Content-Type", "application/json")
                .body(validEquipmentStr)
                .when()
                .post("/equipment")
                .then()
                .statusCode(201)
                .extract().path("entityId");
        // delete
        given()
                .header("Content-Type", "application/json")
                .when()
                .delete("/equipment/" + uuid)
                .then()
                .statusCode(204);
        // get
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("/equipment/" + uuid)
                .then()
                .statusCode(404);

    }
}