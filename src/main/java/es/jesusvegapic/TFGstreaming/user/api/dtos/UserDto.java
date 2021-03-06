package es.jesusvegapic.TFGstreaming.user.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String name;

    private String familyName;

    private String address;

    private String city;

    private String province;

    private String postalCode;

    private String bankAccount;

    private String passwd;

    private Role role;

    private Boolean active;

    private LocalDateTime registrationDate;

    public UserDto(User user) {
        BeanUtils.copyProperties(user, this);
        this.passwd = "secret";
    }

    public static UserDto ofUser(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .familyName(user.getFamilyName())
                .address(user.getAddress())
                .city(user.getCity())
                .province(user.getProvince())
                .postalCode(user.getPostalCode())
                .role(user.getRole())
                .active(user.getActive())
                .registrationDate(user.getRegistrationDate())
                .passwd(user.getPasswd())
                .bankAccount(user.getBankAccount()).build();
    }

    public void doDefault() {
        if (Objects.isNull(passwd)) {
            passwd = UUID.randomUUID().toString();
        }
        if (Objects.isNull(role)) {
            this.role = Role.CLIENT;
        }
        if (Objects.isNull(active)) {
            this.active = true;
        }
    }


    public User toUser() {
        this.doDefault();
        this.passwd = new BCryptPasswordEncoder().encode(this.passwd);
            User user = new User();
            BeanUtils.copyProperties(this, user);
            return user;
    }
}
