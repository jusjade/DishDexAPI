package com.jade.recipeapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jade.recipeapi.Ingredient;
import com.jade.recipeapi.Recipe;
import com.jade.recipeapi.repo.IngredientRepository;
import com.jade.recipeapi.repo.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    public List<Ingredient> getIngredientsByRecipe(String recipeId) {
        return ingredientRepository.findByRecipeId(recipeId);
    }


    public Ingredient addIngredient(String recipeId, Ingredient ingredient) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (recipeOpt.isPresent()) {
            Recipe recipe = recipeOpt.get();
            ingredient.setRecipe(recipe);
            return ingredientRepository.save(ingredient);
        } else {
            throw new RuntimeException("Recipe not found with ID: " + recipeId);
        }
    }

    public Ingredient updateIngredient(Long ingredientId, Ingredient updatedIngredient) {
        Optional<Ingredient> ingredientOpt = ingredientRepository.findById(ingredientId);
        if (ingredientOpt.isPresent()) {
            Ingredient ingredient = ingredientOpt.get();
            ingredient.setName(updatedIngredient.getName());
            ingredient.setMeasurement(updatedIngredient.getMeasurement());
            ingredient.setUnits(updatedIngredient.getUnits());
            return ingredientRepository.save(ingredient);
        } else {
            throw new RuntimeException("Ingredient not found with ID: " + ingredientId);
        }
    }

    public void deleteIngredient(Long ingredientId) {
        if (ingredientRepository.existsById(ingredientId)) {
            ingredientRepository.deleteById(ingredientId);
        } else {
            throw new RuntimeException("Ingredient not found with ID: " + ingredientId);
        }
    }
}
