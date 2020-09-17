package com.company.api.open.handler;

import com.company.REST;
import io.restassured.RestAssured;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;

import java.io.IOException;

public class HelpCustomHttpHandlerCommandTest {
    public static final String DEFAULT_RESPONSE = "use like /api/search?q=your+requestnull/api/search?q=your+requestnull";

    @Before
    public void setUp() throws IOException {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        REST.main(new String[0]);
    }

    @Test
    public void wrongRequestHandling() {
        RestAssured.get("/api/").then()
                .statusCode(200)
                .assertThat()
                .body("", hasItem(DEFAULT_RESPONSE));
    }

}
