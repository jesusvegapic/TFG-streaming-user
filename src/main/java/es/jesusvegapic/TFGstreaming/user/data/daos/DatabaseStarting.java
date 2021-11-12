package es.jesusvegapic.TFGstreaming.user.data.daos;

import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.data.model.User;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DatabaseStarting {

    private static final String SUPER_USER = "admin";
    private static final String PASSWORD = "7";

    private final UserRepository userRepository;

    @Autowired
    public DatabaseStarting(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void initialize() {
        LogManager.getLogger(this.getClass()).warn("------- Finding Admin -------");
        if(this.userRepository.findByRoleIn(List.of(Role.ADMIN)).isEmpty()) {
            User user = User.builder().email("chusck@gmail.com").name(SUPER_USER).lastName("Vega").address("C/ Los Andes n5")
                    .city("Gijon").province("Asturias").postalCode("33213")
                    .passwd(new BCryptPasswordEncoder().encode(PASSWORD)).bankAccount("xxxx-xxxx-xxxx-xxxx")
                    .role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true).build();
            this.userRepository.save(user);
            LogManager.getLogger(this.getClass()).warn("------- Created Admin -------");
        }
    }

}
