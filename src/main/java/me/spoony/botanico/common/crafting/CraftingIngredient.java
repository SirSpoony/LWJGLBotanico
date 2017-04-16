package me.spoony.botanico.common.crafting;

import me.spoony.botanico.common.items.Item;

/**
 * Created by Colten on 4/15/2017.
 */
public class CraftingIngredient {
  public final Item item;
  public final int requiredAmount;

  public CraftingIngredient(Item item, int requiredAmount) {
    this.item = item;
    this.requiredAmount = requiredAmount;
  }
}
