package com.jade.recipeapi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jade.recipeapi.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String>{
    Optional<Recipe> findByName(String name);
}
