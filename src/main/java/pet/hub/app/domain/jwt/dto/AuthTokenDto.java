package pet.hub.app.domain.jwt.dto;

import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthTokenDto {
    String username;
    String role;
    String password;

}
