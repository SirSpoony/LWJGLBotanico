package me.spoony.botanico.common.crafting;

import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;

/**
 * Created by Colten on 11/10/2016.
 */
public class Recipes {

  public static RecipeRegistry GENERAL_RECIPES;
  public static RecipeRegistry TOOL_STATION_RECIPES;

  public static void init() {
    GENERAL_RECIPES = new RecipeRegistry();
    GENERAL_RECIPES.setMaxIngredientCount(4);

    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.ENERGY_PIPE),
        new CraftingIngredient(Items.ROCK, 2)));
    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.BUCKET),
        new CraftingIngredient(Items.ROCK, 2)));
  }
}