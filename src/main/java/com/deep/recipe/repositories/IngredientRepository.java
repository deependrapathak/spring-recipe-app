package com.deep.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import com.deep.recipe.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

}
