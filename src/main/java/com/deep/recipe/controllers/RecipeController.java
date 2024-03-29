package com.deep.recipe.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.deep.recipe.command.RecipeCommand;
import com.deep.recipe.domain.Recipe;
import com.deep.recipe.exceptions.NotFoundException;
import com.deep.recipe.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {
	
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
	
	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	@GetMapping("recipe/{id}/show")
	//@RequestMapping("recipe/{id}/show")
	private String showbyId(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		
		return "recipe/show";
		
	}
	
	@GetMapping("recipe/new")
	//@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		
		model.addAttribute("recipe", new RecipeCommand());
		
		return RECIPE_RECIPEFORM_URL;
	}
	
	@GetMapping("recipe/{id}/update")
	//@RequestMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		//model.addAttribute("recipe", new Recipe()); //test case failing because of null from find by id
		return RECIPE_RECIPEFORM_URL;
		
	}
	
	//@RequestMapping(name = "recipe", method = RequestMethod.POST)
	@PostMapping("recipe")
	//@RequestMapping("recipe")
	public String saveOrUpdate(@Validated @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			bindingResult.getAllErrors().forEach(objectError ->{
				log.debug(objectError.toString());
			});
			
			return RECIPE_RECIPEFORM_URL;
		}
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
		
		return "redirect:/recipe/" + savedCommand.getId()+ "/show" ;
		
	}
	
	@GetMapping("recipe/{id}/delete")
    //@RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		
		log.error("Handling not found exception");
		log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
		
	}
	
}
	


