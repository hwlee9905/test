package pet.hub.app.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pet.hub.app.domain.user.dto.request.SignupRequestDto;
import pet.hub.app.domain.user.service.UserService;


@RequiredArgsConstructor
@RestController
public class LoginController {
    private final UserService userWriteService;
    @PostMapping("/signup")
    public String signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        userWriteService.signup(signupRequestDto);

        return "OK";
    }

}
