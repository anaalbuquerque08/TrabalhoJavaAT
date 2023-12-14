package br.com.infnet.atjavafinal.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.infnet.atjavafinal.model.CharacterRequest;
import br.com.infnet.atjavafinal.Util.RickAndMortyCharacter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            "Rick Sanchez",
            "Morty Smith",
            "Summer Smith",
            "Beth Smith",
            "Jerry Smith",
            "Birdperson",
            "Mr. Meeseeks",
            "Tammy Guetermann",
            "Unity"
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

    // NOVO ENDPOINT GET
    @GetMapping("/rickandmorty/{characterId}")
    public ResponseEntity<RickAndMortyCharacter> getRickAndMortyCharacter(@PathVariable int characterId) {
        String apiUrl = "https://rickandmortyapi.com/api/character/" + characterId;

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(apiUrl))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("Status Code da chamada para a API do Rick and Morty: {}", response.statusCode());

            ObjectMapper objectMapper = new ObjectMapper();
            RickAndMortyCharacter character = objectMapper.readValue(response.body(), RickAndMortyCharacter.class);

            return ResponseEntity.ok(character);
        } catch (IOException | InterruptedException e) {
            logger.error("Erro ao chamar a API do Rick and Morty", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //POST
    @PostMapping("/characters")
    public ResponseEntity<String> addCharacter(@RequestBody CharacterRequest characterRequest) {
        try {
            if (characterRequest == null) {
                return ResponseEntity.badRequest().body("CharacterRequest não pode ser nulo");
            }

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
            if (oldName == null) {
                throw new IllegalArgumentException("O nome antigo não pode ser nulo");
            }

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