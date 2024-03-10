package pet.hub.app.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pet.hub.app.domain.BaseEntity;
import pet.hub.app.domain.user.util.Address;
import pet.hub.app.domain.user.util.ProfileImage;
import pet.hub.app.domain.user.util.Role;
import pet.hub.app.domain.user.util.Sex;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String nickname;
    @Embedded
    private Address address;
    @Embedded
    private ProfileImage profileImage;
    @Lob
    private String introduction;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne
    @JoinColumn(name = "id")
    private Authentication authentication;
}
