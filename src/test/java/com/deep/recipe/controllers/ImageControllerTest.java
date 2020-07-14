package com.deep.recipe.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
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

		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	@Test
	void getImageForm() throws Exception {

		// given
		RecipeCommand command = new RecipeCommand();

		command.setId(1L);

		Mockito.when(recipeService.findCommandById(anyLong())).thenReturn(command);

		// when

		mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image")).andExpect(MockMvcResultMatchers.status().isOk())
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

	@Test
	public void renderImageFromDB() throws Exception {

		// given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);

		String s = "fake image text";
		Byte[] bytesBoxed = new Byte[s.getBytes().length];

		int i = 0;

		for (byte primByte : s.getBytes()) {
			bytesBoxed[i++] = primByte;
		}

		command.setImage(bytesBoxed);

		when(recipeService.findCommandById(anyLong())).thenReturn(command);

		// when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage")).andExpect(status().isOk())
				.andReturn().getResponse();

		byte[] reponseBytes = response.getContentAsByteArray();

		assertEquals(s.getBytes().length, reponseBytes.length);
	}
	
	@Test
    public void testGetImageNumberFormatException() throws Exception {

        mockMvc.perform(get("/recipe/asdf/recipeimage"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("400error"));
    }

}
