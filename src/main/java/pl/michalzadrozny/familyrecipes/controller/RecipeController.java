package pl.michalzadrozny.familyrecipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.RecipeNotFoundException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO;
import pl.michalzadrozny.familyrecipes.model.view.RecipeView;
import pl.michalzadrozny.familyrecipes.repository.RecipeRepo;
import pl.michalzadrozny.familyrecipes.service.RecipeService;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<RecipeDTO> addRecipe(@RequestBody RecipeDTO recipe) {
        try {
            recipeService.addRecipe(recipe);
            return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
        } catch (UserDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RecipeAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(recipe);
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
