package com.deep.recipe;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.deep.recipe.domain.Recipe;
import com.deep.recipe.repositories.RecipeRepository;
import com.deep.recipe.services.RecipeServiceImpl;

class RecipeServiceImplTest {
	
	RecipeServiceImpl recipeService;
	
	@Mock
	RecipeRepository recipeRepository;

	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		recipeService = new RecipeServiceImpl(recipeRepository);
		
	}

	@Test
	void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	void getrecipe() {
		
		Recipe recipe = new Recipe();
		
		Set<Recipe> recipesData = new HashSet<Recipe>();
		
		recipesData.add(recipe);
		
		Mockito.when(recipeRepository.findAll()).thenReturn(recipesData);
			
		Set<Recipe> recipes = recipeService.getRecipes();
		
		assertSame(recipes.size(), 1);
		
		Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
		
	}

}
