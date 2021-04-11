package pl.michalzadrozny.familyrecipes.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import pl.michalzadrozny.familyrecipes.PrepareTests;
import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.RecipeNotFoundException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.dto.AddRecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.entity.*;
import pl.michalzadrozny.familyrecipes.model.mapper.RecipeMapper;
import pl.michalzadrozny.familyrecipes.repository.RecipeRepo;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepo recipeRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private RecipeServiceImpl recipeService;

//    ADD RECIPE
    @Test
    void should_throwRecipeAlreadyExistException_when_recipeHasBeenFound() {
//        given
        Recipe recipe = PrepareTests.getValidRecipe();
        given(recipeRepo.findByAuthorUsernameAndName(recipe.getAuthor().getUsername(), recipe.getName())).willReturn(Optional.of(recipe));
        AddRecipeDTO addRecipeDTO = RecipeMapper.convertRecipeToAddRecipeDTO(recipe);

//        when
//        then
        assertThatExceptionOfType(RecipeAlreadyExistException.class).isThrownBy(() -> recipeService.addRecipe(addRecipeDTO, null));
    }

    @Test
    void should_throwUserDoesNotExistException_when_addingRecipeByUnknownUser() {
//        given
        Recipe recipe = PrepareTests.getValidRecipe();
        given(userRepo.findByUsername(recipe.getAuthor().getUsername())).willReturn(Optional.empty());
        AddRecipeDTO addRecipeDTO = RecipeMapper.convertRecipeToAddRecipeDTO(recipe);

//        when
//        then
        assertThatExceptionOfType(UserDoesNotExistException.class).isThrownBy(() -> recipeService.addRecipe(addRecipeDTO, null));
    }

    @Test
    void should_saveRecipe_when_addingRecipe() {
//        given
        Recipe recipe = PrepareTests.getValidRecipeWithoutId();
        given(recipeRepo.findByAuthorUsernameAndName(recipe.getAuthor().getUsername(), recipe.getName())).willReturn(Optional.empty());
        given(userRepo.findByUsername(recipe.getAuthor().getUsername())).willReturn(Optional.of(recipe.getAuthor()));
        given(recipeRepo.save(recipe)).willReturn(recipe);
        AddRecipeDTO addRecipeDTO = RecipeMapper.convertRecipeToAddRecipeDTO(recipe);

//        when
        recipeService.addRecipe(addRecipeDTO, null);
//        then
        verify(recipeRepo, times(1)).save(recipe);
    }

    @Test
    void should_uploadImage_when_addingRecipe() throws IOException {
//        given
        Recipe recipe = PrepareTests.getValidRecipeWithoutId();
        AddRecipeDTO addRecipeDTO = RecipeMapper.convertRecipeToAddRecipeDTO(recipe);
        MultipartFile multipartFile = PrepareTests.getMultipartFile();

        given(recipeRepo.findByAuthorUsernameAndName(addRecipeDTO.getUsername(), addRecipeDTO.getName())).willReturn(Optional.empty());
        given(userRepo.findByUsername(addRecipeDTO.getUsername())).willReturn(Optional.of(recipe.getAuthor()));
        given(recipeRepo.save(recipe)).willReturn(recipe);

//        when
        recipeService.addRecipe(addRecipeDTO, multipartFile);
//        then
        verify(storageService, times(1)).uploadFile(eq(multipartFile), anyString());
    }

//    EDIT RECIPE

//    @Test
//    void should_throwRecipeNotFoundException_when_recipeHasBeenFound() {
////        given
//        Recipe recipe = PrepareTests.getValidRecipe();
//        given(recipeRepo.findByAuthorUsernameAndName(recipe.getAuthor().getUsername(), recipe.getName())).willReturn(Optional.of(recipe));
//        RecipeDTO recipeDTO = RecipeMapper.recipeToRecipeDTOMapper().map(recipe, RecipeDTO.class);
//
////        when
////        then
//        assertThatExceptionOfType(RecipeNotFoundException.class).isThrownBy(() -> recipeService.editRecipe(recipeDTO));
//    }
//
//    @Test
//    void should_throwUserDoesNotExistException_when_editingRecipeByUnknownUser() {
////        given
//        Recipe recipe = PrepareTests.getValidRecipe();
//        given(userRepo.findByUsername(recipe.getAuthor().getUsername())).willReturn(Optional.empty());
//        AddRecipeDTO addRecipeDTO = RecipeMapper.convertRecipeToAddRecipeDTO(recipe);
//
////        when
////        then
//        assertThatExceptionOfType(UserDoesNotExistException.class).isThrownBy(() -> recipeService.addRecipe(addRecipeDTO));
//    }
//
//    @Test
//    void should_saveRecipe_when_editingRecipe() {
////        given
//        Recipe recipe = PrepareTests.getValidRecipe();
//        given(recipeRepo.findByAuthorUsernameAndName(recipe.getAuthor().getUsername(), recipe.getName())).willReturn(Optional.empty());
//        given(userRepo.findByUsername(recipe.getAuthor().getUsername())).willReturn(Optional.of(recipe.getAuthor()));
//        RecipeDTO recipeDTO = RecipeMapper.recipeToRecipeDTOMapper().map(recipe, RecipeDTO.class);
//
////        when
//        recipeService.editRecipe(recipeDTO);
//
////        then
//        verify(recipeRepo, times(1)).save(recipe);
//    }
}