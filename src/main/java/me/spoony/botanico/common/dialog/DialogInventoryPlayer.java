package me.spoony.botanico.common.dialog;

import me.spoony.botanico.common.crafting.CraftingIngredient;
import me.spoony.botanico.common.crafting.CraftingQuery;
import me.spoony.botanico.common.crafting.Recipe;
import me.spoony.botanico.common.crafting.Recipes;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.items.ItemSlotMode;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by Colten on 11/25/2016.
 */
public class DialogInventoryPlayer extends Dialog {

  public DialogInventoryPlayer() {
    super(Dialog.PLAYER_INV_CRAFTING_ID);
    this.inventory = new Inventory(16, Dialog.PLAYER_INV_CRAFTING_ID);
    for (int i = 4; i < 16; i++) {
      this.inventory.getSlot(i).setMode(ItemSlotMode.TAKE_ONLY);
    }
  }

  public void updateCraftingGrid() {
    for (int i = 4; i < 16; i++) {
      this.inventory.setStack(i, null);
    }

    CraftingQuery query = new CraftingQuery(new ItemStack[]{
        this.inventory.getStack(0),
        this.inventory.getStack(1),
        this.inventory.getStack(2),
        this.inventory.getStack(3),
    });

    Recipe[] recipes = Recipes.GENERAL_RECIPES.query(query);

    for (int i = 0; i < recipes.length; i++) {
      this.inventory.setStack(i + 4, ItemStack.clone(recipes[i].product));
    }
  }

  public void removeIngredients(int slot) {
    if (!inventory.getSlot(slot).isEmpty()) return;

    CraftingQuery query = new CraftingQuery(new ItemStack[]{
        this.inventory.getStack(0),
        this.inventory.getStack(1),
        this.inventory.getStack(2),
        this.inventory.getStack(3),
    });

    Recipe[] recipes = Recipes.GENERAL_RECIPES.query(query);
    if (recipes.length <= slot-4) return;
    Recipe usedRecipe = recipes[slot - 4];

    for (CraftingIngredient ingredient : usedRecipe.ingredients) {
      for (ItemSlot ingredientSlot : new ItemSlot[]{
          inventory.getSlot(0),
          inventory.getSlot(1),
          inventory.getSlot(2),
          inventory.getSlot(3)}) {
        if (ingredientSlot.getStack() == null) continue;
        if (ingredientSlot.getStack().getItem() == ingredient.item
            && ingredientSlot.getStack().getCount() >= ingredient.requiredAmount) {
          ingredientSlot.getStack().increaseCount(-ingredient.requiredAmount);
          break;
        }
      }
    }
  }

  @Override
  public void onClose(RemoteEntityPlayer player) {
    super.onClose(player);

    for (int i = 0; i < 4; i++) {
      player.giveItemStack(inventory.getStack(i));
      inventory.setStack(i, null);
    }
  }

  @Override
  public void onItemSlotInteraction(int slot, byte type) {
    if (slot >= 0 && slot < 4) {
      updateCraftingGrid();
    } else if (slot >= 4 && slot < 16) {
      removeIngredients(slot);
      updateCraftingGrid();
    } else {
      System.out.println("this shouldnt happen :O");
    }
  }
}
