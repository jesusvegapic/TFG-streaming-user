package es.jesusvegapic.TFGstreaming.user.data.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "streamingUser")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String lastName;

    @NonNull
    private String address;

    @NonNull
    private String city;

    @NonNull
    private String province;

    @NonNull
    @Column(length = 5)
    private String postalCode;

    @NonNull
    private String passwd;

    @NonNull
    private String bankAccount;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NonNull
    private LocalDateTime registrationDate;

    @NonNull
    private boolean active;

}
