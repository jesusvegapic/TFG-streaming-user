package es.jesusvegapic.TFGstreaming.user.api.http_errors;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorMessage {

    private final String error;
    private final String message;
    private final Integer code;

    ErrorMessage(Exception e, Integer code) {
        this.error = e.getClass().getSimpleName();
        this.message = e.getMessage();
        this.code = code;
    }
}
