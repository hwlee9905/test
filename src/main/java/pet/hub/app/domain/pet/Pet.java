package pet.hub.app.domain.pet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pet.hub.app.domain.pet.album.PetAlbum;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PetType petType;

    @Embedded
    private PetBirth petBirth;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.REMOVE)
    private List<PetAlbum> petAlbums = new ArrayList<>();

    protected void setName(final String name) {
        if (name != null) {
            this.name = name;
        }
    }

    protected void setPetType(final PetType petType) {
        if (petType != null) {
            this.petType = petType;
        }
    }

    protected void setPetBirth(final PetBirth petBirth) {
        this.petBirth.setPetBirth(petBirth);
    }

    public void setPetAlbums(final List<PetAlbum> petAlbums) {
        if (this.petAlbums != null) {
            this.petAlbums.addAll(petAlbums);
        } else {
            this.petAlbums = petAlbums;
        }
    }
}
