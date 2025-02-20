package com.jade.recipeapi.controller;

import com.jade.recipeapi.Ingredient;
import com.jade.recipeapi.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/recipe/{recipeId}")
    public List<Ingredient> getIngredientsByRecipe(@PathVariable String recipeId) {
        return ingredientService.getIngredientsByRecipe(recipeId);
    }

    @PostMapping("/{recipeId}")
    public Ingredient addIngredient(@PathVariable String recipeId, @RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(recipeId, ingredient);
    }

    @PutMapping("/{ingredientId}")
    public Ingredient updateIngredient(@PathVariable Long ingredientId, @RequestBody Ingredient updatedIngredient) {
        return ingredientService.updateIngredient(ingredientId, updatedIngredient);
    }

    @DeleteMapping("/{ingredientId}")
    public String deleteIngredient(@PathVariable Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return "Ingredient deleted successfully.";
    }
}
