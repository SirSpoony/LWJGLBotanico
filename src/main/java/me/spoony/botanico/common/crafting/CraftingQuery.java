package me.spoony.botanico.common.crafting;

import me.spoony.botanico.common.items.ItemStack;

import java.util.List;

/**
 * Created by Colten on 11/10/2016.
 */
public class CraftingQuery {

  protected ItemStack[] ingredients;

  public CraftingQuery(ItemStack[] stacks) {
    this.ingredients = stacks;
  }

  public ItemStack[] getIngredients() {
    return ingredients;
  }

  public int ingredientsCount() {
    return ingredients.length;
  }
}
