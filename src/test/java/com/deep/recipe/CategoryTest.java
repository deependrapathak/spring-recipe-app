package com.deep.recipe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deep.recipe.domain.Category;

class CategoryTest {

	Category category;
	
	@BeforeEach
	void setUp() throws Exception {
		
		category = new Category();
	}

	@Test
	void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void getId() throws Exception{
		Long idValue = 4L;
		
		category.setId(idValue);
		
		assertEquals(idValue, category.getId());
		
	}

}
