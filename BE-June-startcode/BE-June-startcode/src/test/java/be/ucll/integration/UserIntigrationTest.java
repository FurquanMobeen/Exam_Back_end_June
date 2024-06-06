package be.ucll.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import be.ucll.repository.DbInitializer;
import be.ucll.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Sql("classpath:schema.sql")
public class UserIntigrationTest {
    
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DbInitializer dbInitializer;

    @BeforeEach
    public void setup(){
        dbInitializer.initialize();
    }

    @Test
    public void givenUsers_whenGetAllUsers_thenAllUsersAreReturned() {
        webTestClient.get().uri("/users")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody().json("[\n" + //
                                "  {\n" + //
                                "    \"name\": \"John Doe\",\n" + //
                                "    \"age\": 25,\n" + //
                                "    \"email\": \"john.doe@ucll.be\"\n" + //
                                "  },\n" + //
                                "  {\n" + //
                                "    \"name\": \"Jane Toe\",\n" + //
                                "    \"age\": 30,\n" + //
                                "    \"email\": \"jane.toe@ucll.be\"\n" + //
                                "  },\n" + //
                                "  {\n" + //
                                "    \"name\": \"Jane Toe\",\n" + //
                                "    \"age\": 30,\n" + //
                                "    \"email\": \"jane.koe@ucll.be\"\n" + //
                                "  }\n" + //
                                "]");
    }

    @Test
    public void givenUsers_whenAddingUser_thenUserIsAddedToSystem() {
        webTestClient.post().uri("/users")
            .header("Content-Type", "application/json")
            .bodyValue("{\n" + //
                                "  \"name\": \"Furquan\",\n" + //
                                "  \"age\": 20,\n" + //
                                "  \"email\": \"furquan.mobeen@ucll.be\"\n" + //
                                "}")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody()
            .json("{\r\n" + //
                                "  \"name\": \"Furquan\",\r\n" + //
                                "  \"age\": 20,\r\n" + //
                                "  \"email\": \"furquan.mobeen@ucll.be\"\r\n" + //
                                "}");
    }
}
