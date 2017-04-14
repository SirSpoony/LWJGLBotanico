package me.spoony.botanico.common.crafting;

import com.google.common.collect.Range;
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
        GENERAL_RECIPES.setIngredientStackRange(Range.closed(2, 8));

        TOOL_STATION_RECIPES = new RecipeRegistry();
        TOOL_STATION_RECIPES.setIngredientStackRange(Range.singleton(3));

        GENERAL_RECIPES.register(new Recipe(
                new ItemStack[]{new ItemStack(Items.WOOD, 20)},
                new ItemStack[]{new ItemStack(Items.TOOL_STATION, 1)}, true));

        GENERAL_RECIPES.register(new Recipe(
                new ItemStack[]{new ItemStack(Items.WOOD, 20), new ItemStack(Items.ROCK, 10)},
                new ItemStack[]{new ItemStack(Items.KNAPPING_STATION, 1)}, true));

        GENERAL_RECIPES.register(new Recipe(
                new ItemStack[]{new ItemStack(Items.WHEAT, 1)},
                new ItemStack[]{new ItemStack(Items.WHEAT_SEEDS, 1)}, true));

        GENERAL_RECIPES.register(new Recipe(
                new ItemStack[]{new ItemStack(Items.HEMP_FIBER, 5)},
                new ItemStack[]{new ItemStack(Items.ROPE, 1)}, true));

        TOOL_STATION_RECIPES.register(new Recipe(
                new ItemStack[]{new ItemStack(Items.WOOD), new ItemStack(Items.ROCK), new ItemStack(Items.ROCK_SWORD_BLADE)},
                new ItemStack[]{new ItemStack(Items.ROCK_SWORD, 1)}, false));
        TOOL_STATION_RECIPES.register(new Recipe(
                new ItemStack[]{new ItemStack(Items.WOOD), new ItemStack(Items.WOOD), new ItemStack(Items.ROCK_HOE_HEAD)},
                new ItemStack[]{new ItemStack(Items.ROCK_HOE, 1)}, false));
        TOOL_STATION_RECIPES.register(new Recipe(
                new ItemStack[]{new ItemStack(Items.WOOD), new ItemStack(Items.HEMP_FIBER), new ItemStack(Items.ROCK_PICKAXE_HEAD)},
                new ItemStack[]{new ItemStack(Items.ROCK_PICKAXE, 1)}, false));
        TOOL_STATION_RECIPES.register(new Recipe(
                new ItemStack[]{new ItemStack(Items.WOOD), new ItemStack(Items.HEMP_FIBER), new ItemStack(Items.ROCK_AXE_HEAD)},
                new ItemStack[]{new ItemStack(Items.ROCK_AXE, 1)}, false));

        GENERAL_RECIPES.register(new Recipe(
                new ItemStack[]{new ItemStack(Items.ROCK, 20)},
                new ItemStack[]{new ItemStack(Items.FURNACE, 1)}, true));
    }
}