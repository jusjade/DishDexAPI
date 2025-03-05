package com.jade.recipeapi.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@JsonPropertyOrder({"id", "name", "measurement", "units"})
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;
    private Double measurement;
    private String units;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    @JsonBackReference 
    private Recipe recipe;

    public Ingredient() {} 

    @JsonCreator
    public Ingredient(
        @JsonProperty("name") String name, 
        @JsonProperty("measurement") Double measurement, 
        @JsonProperty("units") String units,
        Recipe recipe) {
        this.name = name != null? name : "Untitled Ingredient";
        this.measurement = measurement != null? measurement : 0.0;
        this.units = units != null? units : "Unspecified";
        this.recipe = recipe;
        
    }

    public Ingredient(String name){
        this.name = name; 
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null? name : "Unknown";
    }

    public Double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Double measurement) {
        this.measurement = measurement != null? measurement : 0.0;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units != null? units : "Unspecified";
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
