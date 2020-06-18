package com.deep.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import com.deep.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{

	
}
