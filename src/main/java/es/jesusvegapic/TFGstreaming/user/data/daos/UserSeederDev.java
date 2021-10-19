package es.jesusvegapic.TFGstreaming.user.data.daos;

import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.data.model.User;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Repository @Profile("dev")
public class UserSeederDev {

    private DatabaseStarting databaseStarting;
    private UserRepository userRepository;

    @Autowired
    public UserSeederDev(UserRepository userRepository, DatabaseStarting databaseStarting) {
        this.userRepository = userRepository;
        this.databaseStarting = databaseStarting;
        this.deleteAllAndInitializeAndSeedDataBase();
    }

    public void deleteAllAndInitializeAndSeedDataBase() {
        this.deleteAllAndInitialize();

    }

    private void deleteAllAndInitialize() {
        this.userRepository.deleteAll();
        LogManager.getLogger(this.getClass()).warn("------- Deleted All -------");
        this.databaseStarting.initialize();
    }


    public void seedDataBase() {
        LogManager.getLogger(this.getClass()).warn("------- Initial Load from JAVA -------");
        String passwd = new BCryptPasswordEncoder().encode("7");
        User[] users = {
                User.builder().email("adm@gmail.com").name("adm").lastName("Vega").address("C/Streaming, 0")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("mod@gmail.com").name("mod").lastName("Picon").address("C/Streaming, 1")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.MOD).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("adm@gmail.com").name("c1").lastName("Torrelo").address("C/Streaming, 2")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.CLIENT).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("adm@gmail.com").name("c2").lastName("Fernandez").address("C/Streaming, 3")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.CLIENT).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("adm@gmail.com").name("c3").lastName("Sanchez").address("C/Streaming, 4")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.CLIENT).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("adm@gmail.com").name("client").lastName("Pomelo").address("C/Streaming, 5")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.CLIENT).registrationDate(LocalDateTime.now())
                        .active(true).build(),
        };

        this.userRepository.saveAll(Arrays.asList(users));
        LogManager.getLogger(this.getClass()).warn("       ------- users");

    }

}
