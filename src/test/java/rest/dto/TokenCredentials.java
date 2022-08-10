package rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenCredentials {
    private String domain;
    private String email;
    private String password;
}
