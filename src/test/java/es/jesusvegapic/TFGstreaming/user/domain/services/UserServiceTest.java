package es.jesusvegapic.TFGstreaming.user.domain.services;

import es.jesusvegapic.TFGstreaming.user.TestConfig;
import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.data.model.User;
import es.jesusvegapic.TFGstreaming.user.domain.exceptions.ConflictException;
import es.jesusvegapic.TFGstreaming.user.domain.exceptions.ForbiddenException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;


@TestConfig
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testCreateUserForbidden() {
        User user = User.builder().email("email@gmail.com").name("k").familyName("prueba")
                .address("Prueba").city("prueba").province("prueba").postalCode("prueba")
                .passwd("7").bankAccount("prueba").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                .active(true).build();
        assertThrows(ForbiddenException.class, () -> this.userService.createUser(user, Role.MOD));
    }

    @Test
    void testCreateUserEmailAlreadyExist() {
        User user = User.builder().email("adm@gmail.com").name("Aquiles").familyName("prueba")
                .address("Prueba").city("prueba").province("prueba").postalCode("prueba")
                .passwd("7").bankAccount("prueba").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                .active(true).build();
        assertThrows(ConflictException.class, () -> this.userService.createUser(user, Role.ADMIN));
    }

    @Test
    void testCreateUserSuccessfully() {
        User user = User.builder().email("prueba@gmail.com").name("Aquiles").familyName("prueba")
                .address("Prueba").city("prueba").province("prueba").postalCode("prueba")
                .passwd("7").bankAccount("prueba").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                .active(true).build();
    }

}
