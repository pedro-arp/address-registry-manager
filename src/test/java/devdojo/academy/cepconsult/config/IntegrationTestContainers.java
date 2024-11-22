package devdojo.academy.cepconsult.config;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.MySQLContainer;

@Log4j2
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTestContainers {
    protected static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0.33");

    static {
        log.info("Starting MySQL Container...");
        MY_SQL_CONTAINER.start();
    }

}