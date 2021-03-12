package pl.michalzadrozny.familyrecipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.RecipeNotFoundException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.entity.Recipe;
import pl.michalzadrozny.familyrecipes.model.mapper.RecipeMapper;
import pl.michalzadrozny.familyrecipes.repository.RecipeRepo;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;

import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepo recipeRepo;
    private final UserRepo userRepo;

    @Autowired
    public RecipeServiceImpl(RecipeRepo recipeRepo, UserRepo userRepo) {
        this.recipeRepo = recipeRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void addRecipe(RecipeDTO recipeDTO) throws RecipeAlreadyExistException, UserDoesNotExistException {
        Optional<Recipe> recipe = recipeRepo.findRecipeByAuthor_UsernameAndName(recipeDTO.getUsername(), recipeDTO.getName());

        recipe.ifPresent(x -> {
            throw new RecipeAlreadyExistException("Nazwa \"" + recipeDTO.getName() + "\" już została przez Ciebie wybrana do nazwania innego przepisu");
        });

        AppUser user = userRepo
                .findByUsername(recipeDTO.getUsername())
                .orElseThrow(() -> new UserDoesNotExistException("Użytkownik o nazwie " + recipeDTO.getUsername() + " nie istnieje"));

        recipeRepo.save(RecipeMapper.recipeDtoToRecipeMapper(user).map(recipeDTO, Recipe.class));
    }

    @Override
    public void editRecipe(RecipeDTO recipeDTO) throws RecipeNotFoundException {
        Optional<Recipe> recipe = recipeRepo.findRecipeByAuthor_UsernameAndName(recipeDTO.getUsername(), recipeDTO.getName());

        recipe.ifPresent(x -> {
            throw new RecipeNotFoundException("Nazwa \"" + recipeDTO.getName() + "\" już została przez Ciebie wybrana do nazwania innego przepisu");
        });

        AppUser user = userRepo
                .findByUsername(recipeDTO.getUsername())
                .orElseThrow(() -> new UserDoesNotExistException("Użytkownik o nazwie " + recipeDTO.getUsername() + " nie istnieje"));

        recipeRepo.save(RecipeMapper.recipeDtoToRecipeMapper(user).map(recipeDTO, Recipe.class));
    }
}