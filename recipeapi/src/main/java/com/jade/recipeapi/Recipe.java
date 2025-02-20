package com.jade.recipeapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private String id;

    private String name;
    private String type;
    private Integer cookTimeMins;
    private String instructions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference 
    private Set<Ingredient> ingredients = new HashSet<>();

    public Recipe() {} 

    public Recipe(String name, String type, Integer cookTimeMins, String instructions) {
        if (this.id == null || this.id.isEmpty()) {
            this.id = UUID.randomUUID().toString();
        }
        this.name = name;
        this.type = type;
        this.cookTimeMins = cookTimeMins;
        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCookTimeMins() {
        return cookTimeMins;
    }

    public void setCookTimeMins(Integer cookTimeMins) {
        this.cookTimeMins = cookTimeMins;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
}
