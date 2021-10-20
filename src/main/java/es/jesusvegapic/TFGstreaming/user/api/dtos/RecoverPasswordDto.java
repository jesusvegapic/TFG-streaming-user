package es.jesusvegapic.TFGstreaming.user.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoverPasswordDto {
    private String email;
    private String passwd;
}
