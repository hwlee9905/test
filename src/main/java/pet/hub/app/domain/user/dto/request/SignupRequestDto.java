package pet.hub.app.domain.user.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.hub.app.domain.user.util.Address;
import pet.hub.app.domain.user.util.Sex;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class SignupRequestDto {

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String username;
    private String nickname;
    private String city;
    private String district;
    private String roadAddress;
    private Sex sex;
    @Column(unique = true)
    @Size(min = 6, max = 20)
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 8~20자 영문, 숫자, 특수문자를 사용하세요.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email
    private String email;

}
