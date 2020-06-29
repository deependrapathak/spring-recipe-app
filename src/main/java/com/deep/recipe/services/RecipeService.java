package com.deep.recipe.services;

import java.util.Set;

import com.deep.recipe.command.RecipeCommand;
import com.deep.recipe.domain.Recipe;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
