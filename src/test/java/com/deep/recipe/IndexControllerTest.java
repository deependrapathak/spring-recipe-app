package com.deep.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.deep.recipe.controllers.IndexController;
import com.deep.recipe.domain.Recipe;
import com.deep.recipe.services.RecipeService;

class IndexControllerTest {
	
	IndexController indexController;
	
	@Mock
	RecipeService recipeServiceImpl;

	@Mock
	Model model;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeServiceImpl);
	
	}

	@Test
	void test() {
		//fail("Not yet implemented");
	}
	
	
	@Test
	void testMockMVC() throws Exception {
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andExpect(MockMvcResultMatchers.view().name("index"));
		
		
	}
	
	@Test
	void testIndexPageMethod() {
		
		//given
		Set<Recipe> recipes = new HashSet<Recipe>();
		recipes.add(new Recipe());
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		recipes.add(recipe);
		
		Mockito.when(recipeServiceImpl.getRecipes()).thenReturn(recipes);
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		//when
		String viewName = indexController.getIndexPage(model);

		//then
		assertEquals(viewName, "index");
		Mockito.verify(recipeServiceImpl, Mockito.times(1)).getRecipes();
		Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());
		
		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
		
		
		
	}

}
