package es.jesusvegapic.TFGstreaming.user.api.dtos;

import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.data.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {

    @Test
    void testOfUser() {
        UserDto x = UserDto.ofUser(User.builder().email("prueba@gmail.com").role(Role.CLIENT).passwd("1")
                .bankAccount("s").postalCode("3").city("dd").address("pa").province("gi").name("ju")
                .familyName("ja").active(true).registrationDate(LocalDateTime.now()).build());
        assertEquals("prueba@gmail.com", x.getEmail());
        assertEquals("1", x.getPasswd());
    }

    @Test
    void testToUser() {
        User user = UserDto.builder().email("prueba1@gmail.com").name("daemon").address("pi")
                        .familyName("p").city("sa").province("su").bankAccount("la").postalCode("la")
                        .registrationDate(LocalDateTime.now()).role(Role.CLIENT).passwd("pi")
                .active(true).build().toUser();
        assertNotNull(user.getRole());
        assertTrue(user.getActive());
    }




}
