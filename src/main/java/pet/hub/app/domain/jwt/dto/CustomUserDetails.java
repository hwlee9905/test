package pet.hub.app.domain.jwt.dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pet.hub.app.domain.jwt.dto.AuthTokenDto;
import pet.hub.app.domain.user.entity.User;

import java.util.ArrayList;
import java.util.Collection;
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetails implements UserDetails {
    private final AuthTokenDto authTokenDto;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                //권한 정보 반환
                return authTokenDto.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return authTokenDto.getPassword();
    }

    @Override
    public String getUsername() {
        return authTokenDto.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
