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

@Repository 
@Profile("dev")
public class UserSeederDev {

    private final DatabaseStarting databaseStarting;
    private final UserRepository userRepository;

    @Autowired
    public UserSeederDev(UserRepository userRepository, DatabaseStarting databaseStarting) {
        this.userRepository = userRepository;
        this.databaseStarting = databaseStarting;
        this.deleteAllAndInitializeAndSeedDataBase();
    }

    public void deleteAllAndInitializeAndSeedDataBase() {
        this.deleteAllAndInitialize();
        this.seedDataBase();

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
                User.builder().email("adm@gmail.com").name("adm").familyName("Vega").address("C/Streaming, 0")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.ADMIN).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("mod@gmail.com").name("mod").familyName("Picon").address("C/Streaming, 1")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.MOD).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("c1@gmail.com").name("c1").familyName("Torrelo").address("C/Streaming, 2")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.CLIENT).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("c2@gmail.com").name("c2").familyName("Fernandez").address("C/Streaming, 3")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.CLIENT).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("c3@gmail.com").name("c3").familyName("Sanchez").address("C/Streaming, 4")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.CLIENT).registrationDate(LocalDateTime.now())
                        .active(true).build(),

                User.builder().email("c4@gmail.com").name("client").familyName("Pomelo").address("C/Streaming, 5")
                        .city("Barcelona").province("Barcelona").postalCode("08007").passwd(passwd)
                        .bankAccount("xxxx-xxxx-xxxx-xxxx").role(Role.CLIENT).registrationDate(LocalDateTime.now())
                        .active(true).build(),
        };

        this.userRepository.saveAll(Arrays.asList(users));
        LogManager.getLogger(this.getClass()).warn("       ------- users");

    }

}
