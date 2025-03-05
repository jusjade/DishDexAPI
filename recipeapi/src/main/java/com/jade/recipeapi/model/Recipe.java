package com.jade.recipeapi.model; 
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "recipes")
@JsonPropertyOrder({"id", "name", "type", "cookTimeMins", "instructions", "ingredients"})
public class Recipe {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String name;
    private String type;
    private Integer cookTimeMins;
    private String instructions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference 
    private List<Ingredient> ingredients;

    public Recipe(){}

    @JsonCreator
    public Recipe(
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("cookTimeMins") Integer cookTimeMins,
        @JsonProperty("instructions") String instructions,
        @JsonProperty("ingredients") List<Ingredient> ingredients
    ) 
    {   
        this.name = (name != null)? name : "Untitled Recipe";
        this.type = (type != null)? type : "Unknown";
        this.cookTimeMins = (cookTimeMins != null)? cookTimeMins : 0;
        this.instructions = (instructions != null)? instructions: "None";
        this.ingredients = (ingredients != null)? ingredients : new ArrayList<Ingredient>();
    }

    //FIX ALL ACCESSOR AND MUTATOR TO HAVE A DEFAULT VALUE WHEN ITS NULL
    public String getId() {
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null? name : "Untitled Recipe";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type != null? type : "Unknown";
    }

    public Integer getCookTimeMins() {
        return cookTimeMins;
    }

    public void setCookTimeMins(Integer cookTimeMins) {
        this.cookTimeMins = cookTimeMins != null? cookTimeMins : 0;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients != null? ingredients : new ArrayList<Ingredient>();
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions != null? instructions : "None";
    }
    
}
