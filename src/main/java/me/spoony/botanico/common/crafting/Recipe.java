package me.spoony.botanico.common.crafting;

import me.spoony.botanico.common.items.ItemStack;

/**
 * Created by Colten on 11/10/2016.
 */
public class Recipe {

  public CraftingIngredient[] ingredients;
  public ItemStack product;

  public Recipe(ItemStack product, CraftingIngredient... ingredients) {
    this.product = product;
    this.ingredients = ingredients;
  }

  public boolean test(CraftingQuery query) {
    for (CraftingIngredient ingredient : ingredients) {
      boolean found = false;
      for (ItemStack stack : query.ingredients) {
        if (stack == null) {
          continue;
        }
        if (stack.getItem() == ingredient.item && stack.getCount() >= ingredient.requiredAmount) {
          found = true;
          break;
        }
      }
      if (!found) {
        return false;
      }
    }
    return true;
  }
}

