package pl.michalzadrozny.familyrecipes.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.michalzadrozny.familyrecipes.exception.InternalServerErrorException;
import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.RecipeNotFoundException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.dto.AddRecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.entity.Rating;
import pl.michalzadrozny.familyrecipes.model.entity.Recipe;
import pl.michalzadrozny.familyrecipes.model.mapper.RecipeMapper;
import pl.michalzadrozny.familyrecipes.repository.RecipeRepo;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepo recipeRepo;
    private final UserRepo userRepo;
    private final StorageService storageService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public RecipeServiceImpl(RecipeRepo recipeRepo, UserRepo userRepo, StorageService storageService) {
        this.recipeRepo = recipeRepo;
        this.userRepo = userRepo;
        this.storageService = storageService;
    }

    @Override
    public RecipeDTO addRecipe(AddRecipeDTO addRecipeDTO, MultipartFile image) throws RecipeAlreadyExistException, UserDoesNotExistException, InternalServerErrorException {
        Optional<Recipe> existingRecipe = recipeRepo.findByAuthorUsernameAndName(addRecipeDTO.getUsername(), addRecipeDTO.getName());

        existingRecipe.ifPresent(x -> {
            throw new RecipeAlreadyExistException("Nazwa \"" + addRecipeDTO.getName() + "\" już została przez Ciebie wybrana do nazwania innego przepisu");
        });

        AppUser user = userRepo
                .findByUsername(addRecipeDTO.getUsername())
                .orElseThrow(() -> new UserDoesNotExistException("Użytkownik o nazwie " + addRecipeDTO.getUsername() + " nie istnieje"));

        Recipe recipe = RecipeMapper.convertAddRecipeDTOtoRecipe(addRecipeDTO, user);
        recipe.setRating(new Rating());

        Recipe savedRecipe = recipeRepo.save(recipe);

        if (image != null) {
            try {
                String imageName = savedRecipe.getId() + ".jpg";
                storageService.uploadFile(image, imageName);
                savedRecipe.setImageName(imageName);
                savedRecipe = recipeRepo.save(savedRecipe);
            } catch (IOException e) {
                log.error("addRecipe: " + e.getMessage());
                throw new InternalServerErrorException();
            }
        }
        return modelMapper.map(savedRecipe, RecipeDTO.class);
    }

    @Override
    public void editRecipe(RecipeDTO recipeDTO) throws RecipeNotFoundException {
        Optional<Recipe> recipe = recipeRepo.findByAuthorUsernameAndName(recipeDTO.getUsername(), recipeDTO.getName());

        recipe.ifPresent(x -> {
            throw new RecipeNotFoundException("Nazwa \"" + recipeDTO.getName() + "\" już została przez Ciebie wybrana do nazwania innego przepisu");
        });

        AppUser user = userRepo
                .findByUsername(recipeDTO.getUsername())
                .orElseThrow(() -> new UserDoesNotExistException("Użytkownik o nazwie " + recipeDTO.getUsername() + " nie istnieje"));

        recipeRepo.save(RecipeMapper.recipeDtoToRecipeMapper(user).map(recipeDTO, Recipe.class));
    }
}