package com.deep.recipe.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.deep.recipe.command.RecipeCommand;
import com.deep.recipe.services.ImageService;
import com.deep.recipe.services.RecipeService;

class ImageControllerTest {
	
	@Mock
	ImageService imageService;

	@Mock
	RecipeService recipeService;
	
	ImageController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		controller = new ImageController(imageService, recipeService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void getImageForm() throws Exception {
		
		//given
		RecipeCommand command = new RecipeCommand();
		
		command.setId(1L);
		
		Mockito.when(recipeService.findCommandById(anyLong())).thenReturn(command);
		
		//when
		
		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
		
		verify(recipeService, times(1)).findCommandById(anyLong());
		
		
	}
	
	@Test
    public void handleImagePost() throws Exception {
		
		MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain", 
				"Spring Framework Guru".getBytes());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/1/image").file(multipartFile))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.header().string("Location", "/recipe/1/show"));
		
		 verify(imageService, times(1)).saveImageFile(anyLong(), any());
	}

}
