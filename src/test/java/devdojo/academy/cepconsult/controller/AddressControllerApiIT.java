package devdojo.academy.cepconsult.controller;

import devdojo.academy.cepconsult.commons.FileUtils;
import devdojo.academy.cepconsult.config.IntegrationTestContainers;
import devdojo.academy.cepconsult.config.RestAssuredConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestAssuredConfig.class)
@AutoConfigureWireMock(port = 0, files = "classpath:/wiremock/brasil-api", stubs = "classpath:/wiremock/brasil-api/mappings")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerApiIT extends IntegrationTestContainers {
    private static final String URL = "/v1/address";

    @Autowired
    private FileUtils fileUtils;


    @Autowired
    @Qualifier(value = "restAssuredConfiguration")
    private RequestSpecification restAssuredConfiguration;

    @BeforeEach
    void setUrl() {
        RestAssured.requestSpecification = restAssuredConfiguration;
    }


    @Order(1)
    @Test
    @DisplayName("findCep returns AddressGetResponse when successful")
    void findCep_ReturnsAddressGetResponse_WhenSuccessful() throws Exception {
        var cep = "00000";
        var expectedResponse = fileUtils.readResourceFile("brasil-api/expected-get-cep-response-200.json");

        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .get(URL + "/search/{cep}", cep)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(Matchers.equalTo(expectedResponse));
    }


    @Order(2)
    @Test
    @DisplayName("findCep returns CepErrorResponse when unsuccessful")
    void findCep_ReturnsCepErrorResponse_WhenUnsuccessful() throws Exception {
        var cep = "40400";
        var expectedResponse = fileUtils.readResourceFile("brasil-api/expected-get-cep-response-404.json");

        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .get(URL + "/{cep}", cep)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body(Matchers.equalTo(expectedResponse));
    }


}