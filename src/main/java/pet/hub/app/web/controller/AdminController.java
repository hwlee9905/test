package pet.hub.app.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pet.hub.app.domain.user.dto.request.SignupRequestDto;

import java.util.Collection;
import java.util.Iterator;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AdminController {
    //.requestMatchers("/admin").hasAnyAuthority("ADMIN") ADMIN 계정 로그인 필요
    @GetMapping("/admin")
    public String adminHome(){
        return "관리자로 로그인하셨습니다. 관리자 컨트롤러입니다.";
    }
    //현재 세션 정보 확인
    @GetMapping("/auth")
    public String mainP() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();
        return "Authorized Id : " + name + " role : " + role;
    }
}
