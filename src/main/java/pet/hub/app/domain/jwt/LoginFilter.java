package pet.hub.app.domain.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pet.hub.app.domain.jwt.util.JWTUtil;
import pet.hub.app.domain.jwt.dto.CustomUserDetails;
import pet.hub.common.exception.EntityNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //클라이언트 요청에서 id, password 추출
        String id = obtainUsername(request);
        String password = obtainPassword(request);
        //스프링 시큐리티에서 id와 password를 검증하기 위해서 token에 담는다.
        // (token이 AuthenticationManager로 넘겨질 때 dto 역할을 한다.)
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password, null);

        return  authenticationManager.authenticate(authenticationToken);
    }
    //로그인 성공시 실행하는 메소드 (이곳에서 JWT를 발급합니다.)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String userId = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        log.info("로그인 성공 : userRole = " + role);
        log.info("로그인 성공 : userId = " + userId);
        String token = jwtUtil.createJwt(userId, role, 60*60*100L);
        log.info("로그인 성공 : 해당 토큰을 로그인시 Header.Authorization에 포함하세요. Bearer " + token);
        response.addHeader("Authorization", "Bearer " + token);
    }
    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("로그인 실패");
        //로그인 실패 응답
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("timestamp", formattedTime);
        responseData.put("error", "Authentication failed: " + failed.getMessage() +" ID와 PW를 확인해주세요.");
        responseData.put("errorCode", HttpStatus.UNAUTHORIZED.value());
                ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(responseData);
        response.getWriter().write(json);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("id");
    }
}
