package com.deep.recipe.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.deep.recipe.command.IngredientCommand;
import com.deep.recipe.command.RecipeCommand;
import com.deep.recipe.services.IngredientService;
import com.deep.recipe.services.RecipeService;
import com.deep.recipe.services.UnitOfMeasureService;

class IngredientControllerTest {

	@Mock
	IngredientService ingredientService;

	@Mock
	UnitOfMeasureService unitOfMeasureService;

	@Mock
	RecipeService recipeService;

	IngredientController controller;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new IngredientController(ingredientService, recipeService, unitOfMeasureService);

		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	@Test
	void testListIngredient() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(recipeCommand);

		// when

		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

		// then
		Mockito.verify(recipeService, Mockito.times(1)).findCommandById(Mockito.anyLong());

	}

	@Test
	public void testShowIngredient() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();

		// when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

		// then
		mockMvc.perform(get("/recipe/1/ingredient/2/show")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show")).andExpect(model().attributeExists("ingredient"));
	}

	@Test
	public void testUpdateIngredientForm() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();

		// when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

		// then
		mockMvc.perform(get("/recipe/1/ingredient/2/update")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient")).andExpect(model().attributeExists("uomList"));
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);

		// when
		when(ingredientService.saveIngredientCommand(Mockito.any())).thenReturn(command);

		// then
		mockMvc.perform(MockMvcRequestBuilders.post("/recipe/2/ingredient").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
				.param("description", "some string")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

	}

}
