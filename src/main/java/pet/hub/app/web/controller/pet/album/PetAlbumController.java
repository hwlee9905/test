package pet.hub.app.web.controller.pet.album;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.hub.app.domain.pet.album.PetAlbum;
import pet.hub.app.domain.pet.album.PetAlbumService;
import pet.hub.app.web.dto.pet.album.PetAlbumRequest;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pet-album")
public class PetAlbumController {
    private final PetAlbumService petAlbumService;

    @PostMapping("/pet/{petId}")
    public ResponseEntity<PetAlbum> create(@RequestBody final PetAlbumRequest request,
                                           @PathVariable("petId") final Long petId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(petAlbumService.create(request, petId));
    }

    @GetMapping("/{petAlbumId}")
    public ResponseEntity<PetAlbum> read(@PathVariable("petAlbumId") final Long petAlbumId) {
        return ResponseEntity.ok(petAlbumService.read(petAlbumId));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<PetAlbum>> readAllByPetId(@PathVariable("petId") final Long petId) {
        return ResponseEntity.ok(petAlbumService.readAllByPetId(petId));
    }

    @PutMapping("/{petAlbumId}")
    public ResponseEntity<PetAlbum> update(@RequestBody final PetAlbumRequest request,
                                           @PathVariable("petAlbumId") final Long petAlbumId)
    {
        return ResponseEntity.ok(petAlbumService.update(petAlbumId, request));
    }

    @DeleteMapping("/{petAlbumId}")
    public void delete(@PathVariable("petAlbumId") final Long petAlbumId) {
        petAlbumService.delete(petAlbumId);
    }
}
