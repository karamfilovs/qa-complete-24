package rest.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;
    private long expires;
    private String expires_string;
}
