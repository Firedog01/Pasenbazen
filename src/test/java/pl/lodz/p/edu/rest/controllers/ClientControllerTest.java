//package pl.lodz.p.edu.rest.controllers;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.restassured.internal.path.json.JSONAssertion;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import static io.restassured.RestAssured.*;
//import io.restassured.matcher.RestAssuredMatchers.*;
//import pl.lodz.p.edu.rest.model.users.Client;
//import pl.lodz.p.edu.rest.repository.DataFaker;
//
//import javax.xml.crypto.Data;
//
//class ClientControllerTest {
//
//    @BeforeAll
//    static void beforeAll() {
//        baseURI = "http://localhost:8080/rest/api";
//    }
//
//    ObjectMapper obj = new ObjectMapper();
//    Client validClient;
//    String validClientStr;
//
//    @BeforeEach
//    void beforeEach() throws JsonProcessingException {
//        validClient = DataFaker.getClient();
//        validClientStr = obj.writeValueAsString(validClient);
//        System.out.println(validClientStr);
//    }
//
//    @Test
//    void readWriteClient_correct() {
//        given()
//                .header("Content-Type", "application/json")
//                .body(validClientStr)
//        .when()
//                .post("/clients")
//        .then()
//                .statusCode(201)
//                .body("login", equalTo(validClient.getLogin()))
//                .body("entityId", not(validClient.getEntityId()));
//    }
//}