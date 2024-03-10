package pet.hub.app.web.dto.pet;

import lombok.*;
import pet.hub.app.domain.pet.PetBirth;
import pet.hub.app.domain.pet.PetType;
import pet.hub.app.domain.pet.album.PetAlbum;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetRequest {
    private String name;
    private PetType petType;
    private PetBirth petBirth;
    private List<PetAlbum> petAlbums = new ArrayList<>();
}
