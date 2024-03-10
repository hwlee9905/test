package pet.hub.app.web.controller.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.hub.app.domain.pet.Pet;
import pet.hub.app.domain.pet.PetService;
import pet.hub.app.web.dto.pet.PetRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pet")
public class PetController {
    private final PetService petService;

    @PostMapping("")
    public ResponseEntity<Pet> create(@RequestBody final PetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(petService.createPet(request));
    }

    @GetMapping("")
    public ResponseEntity<Pet> read(@RequestParam(name = "petId") final Long petId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(petService.readPet(petId));
    }

    @PutMapping("")
    public ResponseEntity<Pet> update(@RequestParam(name = "petId") final Long petId,
                                      @RequestBody final PetRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(petService.updatePet(petId, request));
    }

    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestParam(name = "petId") final Long petId) {
        petService.deletePet(petId);
        
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
