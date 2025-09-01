package java412.storemanagementapplication;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@EnableScheduling
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestContainerInitialization {

    public static final PostgreSQLContainer<?> postgresqlContainer;

    static {

        postgresqlContainer = new PostgreSQLContainer<>("postgres:13")
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa");

        postgresqlContainer.start();

    }

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:13:///integration-tests-db");
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);

    }

}
