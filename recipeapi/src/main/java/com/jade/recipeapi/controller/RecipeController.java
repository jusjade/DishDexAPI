package com.jade.recipeapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jade.recipeapi.Ingredient;
import com.jade.recipeapi.Recipe;
import com.jade.recipeapi.repo.RecipeRepository;
import com.jade.recipeapi.service.RecipeService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;

    /* ========================= GET MAPPINGS ============================== */
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<Recipe>> getRecipes(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(recipeService.getAllRecipes(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable String id) {
        return ResponseEntity.ok().body(recipeService.getRecipe(id));
    }

    @GetMapping("/sorted/cookTime")
    public List<Recipe> getSortedCookTime() {
        return recipeService.sortByCookTime(recipeRepository.findAll());
    }

     /* ========================= POST MAPPINGS ============================== */
     @PostMapping
     public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
         
         Recipe newRecipe = recipeService.createRecipe(recipe);
         return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
     }

     /* ========================= PUT MAPPINGS ============================== */
    @PutMapping("/{id}")
    public Recipe putMethodName(@PathVariable String id, @RequestBody Recipe updatedRecipe) {
        Optional<Recipe> checkExistence = recipeRepository.findById(id);
        if(checkExistence.isPresent()){
            Recipe existingRecipe = checkExistence.get();
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setCookTimeMins(updatedRecipe.getCookTimeMins());
            existingRecipe.setType(updatedRecipe.getType());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());

            existingRecipe.getIngredients().clear();
            for (Ingredient ingredient : updatedRecipe.getIngredients()) {
            ingredient.setRecipe(existingRecipe);
            existingRecipe.getIngredients().add(ingredient);
            }

            existingRecipe.getIngredients().addAll(updatedRecipe.getIngredients());
            existingRecipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(updatedRecipe));

            return recipeRepository.save(existingRecipe);
        }
        throw new RuntimeException("Recipe not found by ID: " + id);   
    }
  
    /* ========================= DELETE MAPPINGS ============================== */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String id){
        recipeService.deleteRecipe(id);

        return new ResponseEntity<>("Recipe deleted successfully.", HttpStatus.OK);
    }
}
