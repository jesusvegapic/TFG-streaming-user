package es.jesusvegapic.TFGstreaming.user.data.daos;

import es.jesusvegapic.TFGstreaming.user.TestConfig;
import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static es.jesusvegapic.TFGstreaming.user.data.model.Role.ADMIN;
import static es.jesusvegapic.TFGstreaming.user.data.model.Role.MOD;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class UserRepositoryIT {


    @Autowired
    private UserRepository userRepository;

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
