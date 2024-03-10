package pet.hub.app.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {
    String id;
    String password;
}
