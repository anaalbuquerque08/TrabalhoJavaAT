package br.com.infnet.atjavafinal.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.infnet.atjavafinal.model.CharacterRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;




@RestController
@RequestMapping("/")
public class MyController {
    private final Logger logger = LoggerFactory.getLogger(MyController.class);

    private List<String> characters = new ArrayList<>(Arrays.asList(
            "Jake Peralta",
            "Amy Santiago",
            "Rosa Diaz",
            "Terry Jeffords",
            "Captain Raymond Holt",
            "Gina Linetti",
            "Adrian Pimento",
            "Charles Boyle"
    ));


    //GET
    @GetMapping("/characters")
    public ResponseEntity<List<String>> getCharacters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String role) {
        try {
            List<String> filteredCharacters = new ArrayList<>(characters);

            if (name != null) {
                filteredCharacters = filterByName(filteredCharacters, name);
            }
            if (role != null) {

            }

            return ResponseEntity.ok(filteredCharacters);
        } catch (Exception e) {
            logger.error("Erro ao obter informações dos personagens", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    //POST
    @PostMapping("/characters")
    public ResponseEntity<String> addCharacter(@RequestBody CharacterRequest characterRequest) {
        try {
            characters.add(characterRequest.getName());
            logger.info("Personagem adicionado: {}", characterRequest.getName());
            return ResponseEntity.ok("Personagem adicionado com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao adicionar personagem", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao adicionar personagem");
        }
    }
    private List<String> filterByName(List<String> characters, String name) {
        return characters.stream()
                .filter(character -> character.toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    //DELETE
    @DeleteMapping("/characters")
    public ResponseEntity<String> deleteCharacter(@RequestParam String name) {
        try {
            boolean removed = characters.removeIf(character -> character.equalsIgnoreCase(name));
            if (removed) {
                logger.info("Personagem removido: {}", name);
                return ResponseEntity.ok("Personagem removido com sucesso");
            } else {
                logger.warn("Personagem não encontrado para remoção: {}", name);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personagem não encontrado");
            }
        } catch (Exception e) {
            logger.error("Erro ao remover personagem", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover personagem");
        }
    }


    //PUT
    @PutMapping("/characters/{oldName}")
    public ResponseEntity<String> updateCharacter(
            @PathVariable String oldName,
            @RequestParam String newName) {
        try {
            boolean updated = characters.removeIf(character -> character.equalsIgnoreCase(oldName));
            if (updated) {
                characters.add(newName);
                logger.info("Personagem atualizado: {} para {}", oldName, newName);
                return ResponseEntity.ok("Personagem atualizado com sucesso");
            } else {
                logger.warn("Personagem não encontrado para atualização: {}", oldName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Personagem não encontrado");
            }
        } catch (Exception e) {
            logger.error("Erro ao atualizar personagem", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar personagem");
        }
    }
}

