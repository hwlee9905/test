package pet.hub.app.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import pet.hub.app.domain.BaseEntity;
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
    private Long Id;
    private String userId;
    private String pw;
    private String name;
    private String nickname;
    @Embedded
    private Address address;

    @Lob
    private String introduction;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Enumerated(EnumType.STRING)
    private Role role;

}
