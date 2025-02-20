package com.jade.recipeapi.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jade.recipeapi.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
    List<Ingredient> findByRecipeId(String recipeId);
}
