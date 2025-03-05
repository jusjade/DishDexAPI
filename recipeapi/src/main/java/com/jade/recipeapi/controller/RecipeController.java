package com.jade.recipeapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jade.recipeapi.model.Recipe;
import com.jade.recipeapi.repo.RecipeRepository;
import com.jade.recipeapi.service.RecipeService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
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
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;

    /* ========================= GET MAPPINGS ============================== */
    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<Recipe>> getRecipes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        return ResponseEntity.ok().body(recipeService.getAllRecipes(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable String id) {
        return ResponseEntity.ok().body(recipeService.getRecipe(id));
    }

    @GetMapping("/sorted/cookTime")
    public ResponseEntity<Page<Recipe>> getSortedCookTime(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(recipeService.getRecipesSortedByCookTime(page, size));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Recipe>> filterByIngredients(
            @RequestParam List<String> ingredients,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(recipeService.filterByIngredients(ingredients, page, size));
    }

     /* ========================= POST MAPPINGS ============================== */
     @PostMapping
     public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
         
         Recipe newRecipe = recipeService.createRecipe(recipe);
         return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
     }

     /* ========================= PUT MAPPINGS ============================== */
    @PutMapping("/{id}")
    public Recipe putRecipe(@PathVariable String id, @RequestBody Recipe updatedRecipe) {
       Recipe latestRecipe = recipeService.updateRecipe(id, updatedRecipe);

       return recipeRepository.save(latestRecipe);
    }
  
    /* ========================= DELETE MAPPINGS ============================== */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String id){
        recipeService.deleteRecipe(id);

        return new ResponseEntity<>("Recipe deleted successfully.", HttpStatus.OK);
    }
}
