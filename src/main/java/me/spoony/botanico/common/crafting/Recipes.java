package me.spoony.botanico.common.crafting;

import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;

/**
 * Created by Colten on 11/10/2016.
 */
public class Recipes {

  public static RecipeRegistry GENERAL_RECIPES;

  public static void init() {
    GENERAL_RECIPES = new RecipeRegistry();
    GENERAL_RECIPES.setMaxIngredientCount(4);

    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.WALL),
        new CraftingIngredient(Items.WOOD, 4),
        new CraftingIngredient(Items.ROCK, 1)));

    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.FURNACE),
        new CraftingIngredient(Items.ROCK, 8)));

    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.ROCK_HOE),
        new CraftingIngredient(Items.WOOD, 2),
        new CraftingIngredient(Items.ROCK_HOE_HEAD, 1)));
    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.ROCK_AXE),
        new CraftingIngredient(Items.WOOD, 2),
        new CraftingIngredient(Items.ROCK_AXE_HEAD, 1)));
    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.ROCK_SWORD),
        new CraftingIngredient(Items.WOOD, 2),
        new CraftingIngredient(Items.ROCK_SWORD_BLADE, 1)));
    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.ROCK_PICKAXE),
        new CraftingIngredient(Items.WOOD, 2),
        new CraftingIngredient(Items.ROCK_PICKAXE_HEAD, 1)));

    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.ROPE),
        new CraftingIngredient(Items.HEMP_FIBER, 5)));

    GENERAL_RECIPES.register(new Recipe(new ItemStack(Items.WHEAT_SEEDS),
        new CraftingIngredient(Items.WHEAT, 4)));
  }
}