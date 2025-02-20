package com.jade.recipeapi.service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jade.recipeapi.repo.RecipeRepository;
import com.jade.recipeapi.Recipe;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class RecipeService {    
    private final RecipeRepository recipeRepository;

    public Page<Recipe> getAllRecipes(int page, int size){
        return recipeRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    public Recipe getRecipe(String id){
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    public Recipe createRecipe(Recipe recipe){
        return  recipeRepository.save(recipe);
    }

    public void deleteRecipe(String id){
        recipeRepository.deleteById(id);
    }

    public List<Recipe> sortByCookTime(List<Recipe> recipes){
        if(recipes == null || recipes.size() <= 1){
            return recipes;
        }

        int mid = recipes.size()/2;
        List<Recipe> left = new ArrayList<Recipe>();
        List<Recipe> right = new ArrayList<Recipe>();

        for(int i = 0; i < recipes.size(); i++){
            if(i < mid){
                left.add(recipes.get(i));
            }else{
                right.add(recipes.get(i));
            }
        }
       

        return merge(sortByCookTime(left), sortByCookTime(right));
    }

    private static List<Recipe> merge(List<Recipe> left, List<Recipe> right){
        List<Recipe> orig = new ArrayList<>();
        int leftSize = left.size(), rightSize  = right.size();

        int l = 0, r = 0;

        while (l < leftSize && r < rightSize) {
            if(left.get(l).getCookTimeMins() < right.get(r).getCookTimeMins()){
                orig.add(left.get(l));
                l++;
            }else{
                orig.add(right.get(r));
                r++;
            }
        }

        while(l < leftSize){
            orig.add(left.get(l));
            l++;
        }
        while(r < rightSize){
            orig.add(right.get(r));
            r++;
        }

        return orig;
    }
}
