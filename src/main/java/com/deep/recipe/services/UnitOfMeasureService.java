package com.deep.recipe.services;

import java.util.Set;

import com.deep.recipe.command.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	Set<UnitOfMeasureCommand> listAllUoms();

}
