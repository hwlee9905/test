package pet.hub.app.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import pet.hub.app.domain.BaseEntity;
import pet.hub.app.domain.user.util.InfoSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authentication")
public class Authentication extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password;
    private String email;
    private Integer failCount;
    @Enumerated(EnumType.STRING)
    private InfoSet infoSet;
}
