package es.jesusvegapic.TFGstreaming.user.daos;

import es.jesusvegapic.TFGstreaming.user.TestConfig;
import es.jesusvegapic.TFGstreaming.user.data.daos.DatabaseStarting;
import es.jesusvegapic.TFGstreaming.user.data.daos.UserRepository;
import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static es.jesusvegapic.TFGstreaming.user.data.model.Role.ADMIN;
import static es.jesusvegapic.TFGstreaming.user.data.model.Role.MOD;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class UserRepositoryIT {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatabaseStarting databaseStarting;

    @BeforeEach
    void init() {
        this.userRepository.deleteAll();
        this.databaseStarting.initialize();
        String passwd = new BCryptPasswordEncoder().encode("7");
        User user = User.builder().email("adm@gmail.com").name("adm").lastName("Vega").address("C/Streaming, 0")
                .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                .bankAccount("xxxx-xxxx-xxxx-xxxx").role(ADMIN).registrationDate(LocalDateTime.now())
                .active(true).build();
        this.userRepository.save(user);
    }

    @Test
    void testFindByEmail() {
        assertTrue(this.userRepository.findByEmail("adm@gmail.com").isPresent());
    }

    @Test
    void testFindByRoleIn() {
        List<Role> roles = List.of(ADMIN, MOD);
        assertTrue(this.userRepository.findByRoleIn(roles).stream().allMatch(user -> roles.contains(user.getRole())));
    }


}
