package com.jade.recipeapi.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jade.recipeapi.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, String>{
    Page<Recipe> findAll(Pageable pageable);
    Optional<Recipe> findByName(String name);
}
