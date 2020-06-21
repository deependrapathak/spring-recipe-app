package com.deep.recipe.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.deep.recipe.domain.Recipe;
import com.deep.recipe.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
	
	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I am in the Recipe Service Impl");
		
		Set<Recipe> recipeSet = new HashSet<Recipe>();
		
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		
		return recipeSet;
	}

}
