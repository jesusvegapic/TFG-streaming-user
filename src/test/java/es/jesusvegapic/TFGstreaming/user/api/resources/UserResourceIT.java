package es.jesusvegapic.TFGstreaming.user.api.resources;

import es.jesusvegapic.TFGstreaming.user.api.dtos.UserDto;
import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static es.jesusvegapic.TFGstreaming.user.api.resources.UserResource.CLIENTS;
import static es.jesusvegapic.TFGstreaming.user.api.resources.UserResource.USERS;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ApiTestConfig
public class UserResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testLogin() {
        this.restClientTestService.loginAdmin(this.webTestClient);
        assertTrue(this.restClientTestService.getToken().length() > 10);
    }

    @Test
    void testCreateUser() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(UserDto.builder().email("email@gmail.com").name("k").familyName("prueba")
                        .address("Prueba").city("prueba").province("prueba").postalCode("33213")
                        .passwd("7").bankAccount("prueba").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                        .active(true).build()), UserDto.class)
                .exchange().expectStatus().isOk();
    }

    @Test
    void testCreateUserUnauthorized() {
        this.webTestClient
                .post().uri(USERS)
                .body(Mono.just(UserDto.builder().email("email@gmail.com").name("k").familyName("prueba")
                        .address("Prueba").city("prueba").province("prueba").postalCode("prueba")
                        .passwd("7").bankAccount("prueba").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                        .active(true).build()), UserDto.class)
                .exchange().expectStatus().isUnauthorized();
    }

    @Test
    void testCreateUserForbidden() {
        this.restClientTestService.loginMod(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(UserDto.builder().email("email@gmail.com").name("k").familyName("prueba")
                        .address("Prueba").city("prueba").province("prueba").postalCode("prueba")
                        .passwd("7").bankAccount("prueba").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                        .active(true).build()), UserDto.class)
                .exchange().expectStatus().isForbidden();
    }

    @Test
    void createUserConflict() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(UserDto.builder().email("adm@gmail.com").name("k").familyName("prueba")
                        .address("Prueba").city("prueba").province("prueba").postalCode("33213")
                        .passwd("7").bankAccount("prueba").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                        .active(true).build()), UserDto.class)
                .exchange().expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testCreateUserWithoutEmail() {
        this.restClientTestService.loginAdmin(this.webTestClient)
                .post().uri(USERS)
                .body(Mono.just(UserDto.builder().name("k").familyName("prueba")
                        .address("Prueba").city("prueba").province("prueba").postalCode("33213")
                        .passwd("7").bankAccount("prueba").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                        .active(true).build()), UserDto.class)
                .exchange().expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void testCreateClient() {
        this.webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(USERS + CLIENTS)
                        .build())
                .body(Mono.just(UserDto.builder().email("prueba@gmail.com").name("k").familyName("prueba")
                        .address("Prueba").city("prueba").province("prueba").postalCode("33213")
                        .passwd("7").bankAccount("prueba").role(Role.CLIENT).registrationDate(LocalDateTime.now())
                        .active(true).build()), UserDto.class)
                .exchange().expectStatus().isOk();
    }

}
