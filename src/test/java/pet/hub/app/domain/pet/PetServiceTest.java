package pet.hub.app.domain.pet;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PetServiceTest {

    @Autowired
    private PetService petService;

    @Test
    @DisplayName("1.[Create]")
    void create() {
        petService.create();
    }

    @Test
    @DisplayName("2.[Read]")
    void read() {
        log.info(petService.read().getName());
    }
}