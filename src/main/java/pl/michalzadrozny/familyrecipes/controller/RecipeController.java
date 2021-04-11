package pl.michalzadrozny.familyrecipes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.michalzadrozny.familyrecipes.exception.InternalServerErrorException;
import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.RecipeNotFoundException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.dto.AddRecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO;
import pl.michalzadrozny.familyrecipes.model.view.RecipeView;
import pl.michalzadrozny.familyrecipes.repository.RecipeRepo;
import pl.michalzadrozny.familyrecipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeRepo recipeRepo;
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeRepo recipeRepo, RecipeService recipeService) {
        this.recipeRepo = recipeRepo;
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipePreviewDTO>> getRecipePreviews(Pageable pageable) {
        return ResponseEntity.ok(recipeRepo.findAllRecipePreviews(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeView> getRecipe(@PathVariable Long id) {
        return recipeRepo.findById(id, RecipeView.class)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addRecipe(@RequestPart(name = "image", required = false) MultipartFile image, @Valid @RequestPart(name = "recipe") AddRecipeDTO recipeDTO) {
        try {
            RecipeDTO outputRecipe = recipeService.addRecipe(recipeDTO, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(outputRecipe);
        } catch (UserDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RecipeAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(recipeDTO);
        } catch (InternalServerErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping
    public ResponseEntity<RecipeDTO> editRecipe(@RequestBody RecipeDTO recipe) {
        try {
            recipeService.editRecipe(recipe);
            return ResponseEntity.status(HttpStatus.OK).body(recipe);
        } catch (RecipeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
