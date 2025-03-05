package com.jade.recipeapi.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.jade.recipeapi.model.Ingredient;
import com.jade.recipeapi.model.Recipe;
import com.jade.recipeapi.repo.RecipeRepository;

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

    public Recipe updateRecipe(String id, Recipe updatedRecipe){
         Optional<Recipe> checkExistence = recipeRepository.findById(id);
        if(checkExistence.isPresent()){
            Recipe existingRecipe = checkExistence.get();
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setCookTimeMins(updatedRecipe.getCookTimeMins());
            existingRecipe.setType(updatedRecipe.getType());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());

            if(!updatedRecipe.getIngredients().isEmpty()) {
                existingRecipe.getIngredients().clear();
                List<Ingredient> newIngredients = new ArrayList<>();
                for (Ingredient ingredient : updatedRecipe.getIngredients()) {
                    Ingredient managedIngredient = new Ingredient();
                    managedIngredient.setName(ingredient.getName());
                    managedIngredient.setMeasurement(ingredient.getMeasurement());
                    managedIngredient.setUnits(ingredient.getUnits());
                    managedIngredient.setRecipe(existingRecipe);  
                    newIngredients.add(managedIngredient);
                }
                existingRecipe.getIngredients().addAll(newIngredients);
            }


           return existingRecipe;
        }
        throw new RuntimeException("Recipe not found by ID: " + id);   
    }

    public void deleteRecipe(String id){
        recipeRepository.deleteById(id);
    }

      //====================//====================**COOKTIME SORT**====================//====================//====================//====================//====================
     public Page<Recipe> getRecipesSortedByCookTime(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Recipe> allRecipes = recipeRepository.findAll();

        List<Recipe> sortedRecipes = sortByCookTime(allRecipes);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sortedRecipes.size());
        List<Recipe> pagedRecipes = sortedRecipes.subList(start, end);

        return new PageImpl<>(pagedRecipes, pageable, sortedRecipes.size());
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
    //====================//====================**FILTER RECIPES**====================//====================//====================//====================//====================

    public Page<Recipe> filterByIngredients(List<String> list, int page, int size) {
        Map<Recipe, Integer> map = new HashMap<>();
        List<Recipe> sortedRecipes = new ArrayList<>();

        for (Recipe recipe : recipeRepository.findAll()) {
            int count = this.countIngOccurence(list, recipe);
            if (count > 0) {
                map.put(recipe, count); 
            }
        }
        List<Map.Entry<Recipe, Integer>> entryList = new ArrayList<>();
        for (Map.Entry<Recipe, Integer> entry : map.entrySet()) {
            entryList.add(entry);
        }

        entryList.sort(new IngredientPriorityComparator());
        
        for (Map.Entry<Recipe, Integer> entry : entryList) {
            sortedRecipes.add(entry.getKey());
        }
        
        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sortedRecipes.size());
        List<Recipe> pagedRecipes = sortedRecipes.subList(start, end);
    
        return new PageImpl<>(pagedRecipes, pageable, sortedRecipes.size());
    }
    

    private int countIngOccurence(List<String> list, Recipe recipe){
        int count = 0;
        if(recipe.getIngredients().isEmpty()){
            return count;
        }

        for(Ingredient ingredient : recipe.getIngredients()){
           for(String name : list){
            if(ingredient.getName() != null && ingredient
                .getName()
                .toLowerCase()
                .replace(" ", "")
                .contains(name.toLowerCase().replace(" ", ""))){
                count++;
            }
           }
        }
        return count;
    }
    
    
}
