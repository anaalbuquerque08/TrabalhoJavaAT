package br.com.infnet.atjavafinal;

import br.com.infnet.atjavafinal.controllers.MyController;
import br.com.infnet.atjavafinal.model.CharacterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MyControllerTest {

    private final Logger logger = LoggerFactory.getLogger(MyControllerTest.class);

    @InjectMocks
    private MyController myController;


    @Test
    void testGetCharacters() {
        ResponseEntity<List<String>> response = myController.getCharacters(null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        logger.info("Teste para getCharacters passou com sucesso.");
    }

    @Test
    void testAddCharacter() {
        CharacterRequest characterRequest = new CharacterRequest("New Character", 25, null);
        ResponseEntity<String> response = myController.addCharacter(characterRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Personagem adicionado com sucesso", response.getBody());
        logger.info("Teste para addCharacter passou com sucesso.");
    }

    @Test
    void testDeleteCharacter() {
        ResponseEntity<String> response = myController.deleteCharacter("Rick Sanchez");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Personagem removido com sucesso", response.getBody());
        logger.info("Teste para deleteCharacter passou com sucesso.");
    }

    @Test
    void testDeleteCharacterNotFound() {
        ResponseEntity<String> response = myController.deleteCharacter("Non-existent Character");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Personagem não encontrado", response.getBody());
        logger.info("Teste para deleteCharacter (personagem não encontrado) passou com sucesso.");
    }

    @Test
    void testUpdateCharacter() {
        ResponseEntity<String> response = myController.updateCharacter("Rick Sanchez", "New Rick");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Personagem atualizado com sucesso", response.getBody());
        logger.info("Teste para updateCharacter passou com sucesso.");
    }

    @Test
    void testUpdateCharacterNotFound() {
        ResponseEntity<String> response = myController.updateCharacter("Non-existent Character", "New Name");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Personagem não encontrado", response.getBody());
        logger.info("Teste para updateCharacter (personagem não encontrado) passou com sucesso.");
    }
}
