package pet.hub.app.domain.pet.album;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pet.hub.app.domain.pet.*;
import pet.hub.app.domain.pet.album.img.PetAlbumImg;
import pet.hub.app.web.dto.pet.album.PetAlbumRequest;
import pet.hub.common.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetAlbumServiceTest {

    @Mock
    private PetAlbumRepository petAlbumRepository;

    @InjectMocks
    private PetAlbumService petAlbumService;

    @Mock
    private PetService petService;

    @BeforeEach
    public void setUp() {
        PetAlbum petAlbum = PetAlbum.builder()
                .id(1L)
                .title("Test Album")
                .content("Test Content")
                .pet(petFactory())
                .build();

        Mockito.lenient().when(petAlbumRepository.findById(1L)).thenReturn(Optional.of(petAlbum));
    }

    @Test
    public void Create_PetAlbum() {
        // given
        Pet pet = petFactory();

        PetAlbumRequest request = requestFactory(
                "Test Title",
                "Test Content"
        );

        Mockito.lenient().when(petService.readPet(1L)).thenReturn(pet);
        when(petAlbumRepository.save(any(PetAlbum.class))).then(returnsFirstArg());

        // when
        PetAlbum result = petAlbumService.create(request, 1L);

        // then
        Assertions.assertEquals(result.getTitle(), request.title());
        Assertions.assertEquals(result.getPet().getName(), petAlbumRepository.findById(1L).get().getPet().getName());
    }

    @Test
    public void Read_PetAlbum() {
        // when
        PetAlbum result = petAlbumService.read(1L);

        // then
        Assertions.assertEquals(petAlbumRepository.findById(1L).get().getTitle(), result.getTitle());
        Assertions.assertEquals(petAlbumRepository.findById(1L).get().getPet().getName(),
                                result.getPet().getName());
    }

    @Test
    public void Read_PetAlbum_When_Throw_EntityNotFoundException() {
        // given
        when(petAlbumRepository.findById(2L)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> petAlbumService.read(2L));
    }

//    @Test
//    public void ReadAllByPetId_PetAlbum() {
//        // given
//        Pet pet = petFactory();
//        petAlbumSetter(pet, 2);
//
//        when(petAlbumService.readAllByPetId(1L)).thenReturn(pet.getPetAlbums());
//
//        // when
//        List<PetAlbum> result = petAlbumService.readAllByPetId(1L);
//
//        // then
//        Assertions.assertEquals(2, result.size());
//        Assertions.assertEquals(pet.getPetAlbums().get(0).getTitle(), result.get(0).getTitle());
//    }

    public Pet petFactory() {
        return Pet.builder()
                .id(1L)
                .name("Test")
                .petType(PetType.DOG_POMERANIAN)
                .petBirth(petBirthFactory("2021", "07", "23"))
                .build();
    }

    public PetBirth petBirthFactory(String year, String month, String day) {
        return PetBirth.builder()
                .year(year)
                .month(month)
                .day(day)
                .build();
    }

    public PetAlbumRequest requestFactory(String title, String content) {
        return PetAlbumRequest.builder()
                .title(title)
                .content(content)
                .build();
    }
    public void petAlbumImgsSetter(PetAlbum petAlbum, int size) {
        List<PetAlbumImg> petAlbumImgs = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            PetAlbumImg petAlbumImg = PetAlbumImg.builder()
                    .id((long) (i + 1))
                    .uploadFileName("Test UFN" + (i + 1))
                    .uploadFilePath("Test UFP" + (i + 1))
                    .uploadFileUrl("Test URL" + (i + 1))
                    .petAlbum(petAlbum)
                    .build();

            petAlbumImgs.add(petAlbumImg);
        }

        petAlbum.setPetAlbumImgs(petAlbumImgs);
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