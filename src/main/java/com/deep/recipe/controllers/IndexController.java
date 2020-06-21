package com.deep.recipe.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deep.recipe.domain.Category;
import com.deep.recipe.domain.UnitOfMeasure;
import com.deep.recipe.repositories.CategoryRepository;
import com.deep.recipe.repositories.UnitOfMeasureRepository;
import com.deep.recipe.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Autowired
	private RecipeService recipeServiceImpl;
	
	@RequestMapping({"", "/", "/index"})
	public String getIndexPage(Model model) {
	
		log.debug("I am inside Index controller and getting index page!!!!!!!!!!!");
		Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
		Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
		
		System.out.println(categoryOptional.get().getId() +"  " + categoryOptional.get().getDescription());
		
		System.out.println(unitOfMeasureOptional.get().getId() + "  " + unitOfMeasureOptional.get().getDescription());
		
		model.addAttribute("recipes", recipeServiceImpl.getRecipes());
		return "index";
		
	}
}
