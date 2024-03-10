package pet.hub.app.web.dto.pet.album;

import lombok.Builder;
import pet.hub.app.domain.pet.Pet;
import pet.hub.app.domain.pet.album.img.PetAlbumImg;

import java.util.List;

@Builder
public record PetAlbumRequest(
        String title,
        String content,
        List<PetAlbumImg> petAlbumImgs
) {
}
