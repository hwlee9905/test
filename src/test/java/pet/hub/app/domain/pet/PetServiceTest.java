package pet.hub.app.domain.pet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pet.hub.app.domain.pet.album.PetAlbum;
import pet.hub.app.web.dto.pet.PetRequest;
import pet.hub.common.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @BeforeEach
    public void setUp() { // given
        Pet pet = Pet.builder()
                .id(1L)
                .name("Test")
                .petType(PetType.DOG_POMERANIAN)
                .petBirth(petBirthFactory("2021", "07", "23"))
                .build();

        Mockito.lenient().when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
    }

    @DisplayName("Create_Pet")
    @Test
    public void Create_Pet() {
        // given
        PetRequest request = requestFactory(
                "Test",
                PetType.DOG_POMERANIAN,
                petBirthFactory("2021", "07", "23")
        );

        when(petRepository.save(any(Pet.class))).then(returnsFirstArg());

        // when
        Pet result = petService.createPet(request);

        // then
        Assertions.assertEquals(request.getName(), result.getName());
        Assertions.assertEquals(request.getPetType(), result.getPetType());
    }

    @DisplayName("Read_Pet")
    @Test
    public void Read_Pet() {
        // when
        Pet pet = petService.readPet(1L);

        // then
        Assertions.assertEquals(petRepository.findById(1L).get().getName(), pet.getName());
        Assertions.assertEquals(petRepository.findById(1L).get().getPetType(), pet.getPetType());
    }

    @DisplayName("Read_Pet_When_Throw_EntityNotFoundException")
    @Test
    public void Read_Pet_When_Throw_EntityNotFoundException() {
        // given
        when(petRepository.findById(2L)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            petService.readPet(2L);
        });
    }

    @DisplayName("Read_PetAlbums_From_Pet")
    @Test
    public void Read_PetAlbums_From_Pet() {
        // given
        Pet pet = petRepository.findById(1L).get();

        // when
        petAlbumSetter(pet, 2);

        List<PetAlbum> petAlbums = pet.getPetAlbums();

        // then
        Assertions.assertEquals(petAlbums.get(0).getContent(), "Test Content1");
        Assertions.assertEquals(petAlbums.get(1).getContent(), "Test Content2");
    }

    @DisplayName("Read_PetAlbums_From_Pet_When_Null_PetAlbums")
    @Test
    public void Read_PetAlbums_From_Pet_When_Null_PetAlbums() {
        // given
        Pet pet = petRepository.findById(1L).get();

        // when, then
        Assertions.assertNull(pet.getPetAlbums());
    }

    @DisplayName("Update_Pet")
    @Test
    public void Update_Pet() {
        // given
        PetRequest request = requestFactory("Update", PetType.CAT_PERSIAN,
                petBirthFactory("2021", "07", "23"));
        // when
        Pet result = petService.updatePet(1L, request);
        // then
        Assertions.assertEquals(request.getName(), result.getName());
        Assertions.assertEquals(request.getPetType(), result.getPetType());
        Assertions.assertEquals(request.getPetBirth().getYear(), result.getPetBirth().getYear());
    }

    @DisplayName("Update_Pet_When_Throw_EntityNotFoundException")
    @Test
    public void Update_Pet_When_Throw_EntityNotFoundException() {
        // given
        PetRequest request = requestFactory(
                "Update",
                PetType.CAT_PERSIAN,
                petBirthFactory("2021", "07", "23")
        );
        when(petRepository.findById(2L)).thenReturn(Optional.empty());
        // when, then
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            petService.updatePet(2L, request);
        });
    }

    @DisplayName("Delete_Pet")
    @Test
    public void Delete_Pet() {
        // given
        Pet pet = petRepository.findById(1L).get();

        // when
        petService.deletePet(1L);

        // then
        Mockito.verify(petRepository, Mockito.times(1)).delete(pet);
    }

    @DisplayName("Delete_Pet_When_Throw_EntityNotFoundException")
    @Test
    public void Delete_Pet_When_Throw_EntityNotFoundException() {
        // given
        when(petRepository.findById(2L)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            petService.deletePet(2L);
        });
    }

   public PetBirth petBirthFactory(String year, String month, String day) {
        return PetBirth.builder()
                .year(year)
                .month(month)
                .day(day)
                .build();
    }

    public PetRequest requestFactory(String name, PetType petType, PetBirth petBirth) {
        return PetRequest.builder()
                .name(name)
                .petType(petType)
                .petBirth(petBirth)
                .build();
    }

    public void petAlbumSetter(Pet pet, int size) {
        List<PetAlbum> petAlbums = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            PetAlbum petAlbum = PetAlbum.builder()
                    .id((long) (i + 1))
                    .title("Test Title" + (i + 1))
                    .content("Test Content" + (i + 1))
                    .pet(pet)
                    .petAlbumImgs(new ArrayList<>())
                    .build();

            petAlbums.add(petAlbum);
        }

        pet.setPetAlbums(petAlbums);
    }
}