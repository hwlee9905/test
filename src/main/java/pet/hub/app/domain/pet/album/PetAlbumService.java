package pet.hub.app.domain.pet.album;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.hub.app.domain.pet.Pet;
import pet.hub.app.domain.pet.PetService;
import pet.hub.app.web.dto.pet.album.PetAlbumRequest;
import pet.hub.common.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PetAlbumService {
    private final PetAlbumRepository petAlbumRepository;

    private final PetService petService;

    @Transactional(rollbackFor = Exception.class)
    public PetAlbum create(final PetAlbumRequest petAlbumRequest, final Long petId) {
        Pet pet = petService.readPet(petId);

        PetAlbum petAlbum = PetAlbum.builder()
                .title(petAlbumRequest.title())
                .content(petAlbumRequest.content())
                .pet(pet)
                .petAlbumImgs(new ArrayList<>())
                .build();

        petAlbum.setPetAlbumImgs(petAlbumRequest.petAlbumImgs());

        return petAlbumRepository.save(petAlbum);
    }

    @Transactional(readOnly = true)
    public PetAlbum read(final Long petAlbumId) {
        return petAlbumRepository.findById(petAlbumId)
                .orElseThrow(() -> new EntityNotFoundException("저장된 앨범이 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<PetAlbum> readAllByPetId(final Long petId) {
        Pet pet = petService.readPet(petId);

        if (pet.getPetAlbums() == null) {
            throw new EntityNotFoundException("저장된 앨범이 없습니다.");
        } else {
             return pet.getPetAlbums();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public PetAlbum update(final Long petAlbumId, final PetAlbumRequest request) {
        PetAlbum petAlbum = read(petAlbumId);

        petAlbum.setTitle(request.title());
        petAlbum.setContent(request.content());
        petAlbum.setPetAlbumImgs(request.petAlbumImgs());

        return petAlbum;
    }

    @Transactional
    public void delete(final Long petAlbumId) {
        Optional<PetAlbum> petAlbum = petAlbumRepository.findById(petAlbumId);

        if (petAlbum.isEmpty()) {
            throw new EntityNotFoundException("존재하지 않는 앨범입니다.");
        } else {
            petAlbumRepository.delete(petAlbum.get());
        }
    }
}
