package pet.hub.app.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.hub.app.domain.jwt.dto.AuthTokenDto;
import pet.hub.app.domain.jwt.dto.CustomUserDetails;
import pet.hub.app.domain.user.dto.request.SignupRequestDto;
import pet.hub.app.domain.user.entity.Authentication;
import pet.hub.app.domain.user.entity.User;
import pet.hub.app.domain.user.repository.AuthenticationRepository;
import pet.hub.app.domain.user.repository.UserRepository;
import pet.hub.app.domain.user.util.Address;
import pet.hub.app.domain.user.util.Role;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //회원가입
    @Transactional(rollbackFor = {Exception.class})
    public User signup(SignupRequestDto signupRequestDto) {
        Authentication authentication = Authentication.builder()
                .userId(signupRequestDto.getUserId())
                .email(signupRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()))
                .build();
        authenticationRepository.save(authentication);
        Address address = Address.builder()
                .city(signupRequestDto.getCity())
                .district(signupRequestDto.getDistrict())
                .roadAddress(signupRequestDto.getRoadAddress())
                .build();
        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .role(Role.USER)
                .nickname(signupRequestDto.getNickname())
                .sex(signupRequestDto.getSex())
                .address(address)
                .build();
        user.setAuthentication(authentication);
        userRepository.save(user);
        return user;

    }
    @Transactional(readOnly = true)
    public User Read() {
        return userRepository.findById(1L).get();
    }
    //로그인
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //DB에서 조회
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            AuthTokenDto authTokenDto = AuthTokenDto.builder()
                    .username(user.getAuthentication().getUserId())
                    .password(user.getAuthentication().getPassword())
                    .role(user.getRole().toString())
                    .build();
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(authTokenDto);
        }

        return null;
    }
}
