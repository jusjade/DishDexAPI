package com.jade.recipeapi.service;

import java.util.Comparator;
import java.util.Map;

import com.jade.recipeapi.model.Recipe;

public class IngredientPriorityComparator implements Comparator<Map.Entry<Recipe, Integer>>{

    @Override
    public int compare(Map.Entry<Recipe, Integer> entry1, Map.Entry<Recipe, Integer> entry2) {
        return Integer.compare(entry2.getValue(), entry1.getValue());
    }
    
}
