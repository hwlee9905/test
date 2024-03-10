package pet.hub.app.domain.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.hub.app.web.dto.pet.PetRequest;
import pet.hub.common.exception.EntityNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;

    @Transactional(rollbackFor = Exception.class)
    public Pet createPet(final PetRequest request) {
        Pet pet = Pet.builder()
                .name(request.getName())
                .petType(request.getPetType())
                .petBirth(request.getPetBirth())
                .build();

        return petRepository.save(pet);
    }

    @Transactional(readOnly = true)
    public Pet readPet(final Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("등록되지 않은 반려동물입니다."));
    }

    @Transactional(rollbackFor = Exception.class)
    public Pet updatePet(final Long petId, final PetRequest request) {
        Pet pet = readPet(petId);

        pet.setName(request.getName());
        pet.setPetType(request.getPetType());
        pet.setPetBirth(request.getPetBirth());
        pet.setPetAlbums(request.getPetAlbums());

        return pet;
    }

    @Transactional
    public void deletePet(final Long id) {
        Optional<Pet> pet = petRepository.findById(id);

        if (pet.isEmpty()) {
            throw new EntityNotFoundException("등록되지 않은 반려동물입니다.");
        } else {
            petRepository.delete(pet.get());
        }
    }
}
