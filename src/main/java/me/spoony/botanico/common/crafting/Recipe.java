package me.spoony.botanico.common.crafting;

import me.spoony.botanico.common.items.ItemStack;

/**
 * Created by Colten on 11/10/2016.
 */
public class Recipe {
    public ItemStack[] ingredients;
    public ItemStack[] products;
    public ItemStack tool;
    public boolean shapeless;

    public Recipe(ItemStack[] ingredients, ItemStack[] product, boolean shapeless) {
        this.ingredients = ingredients;
        this.tool = null;
        this.products = product;
        this.shapeless = shapeless;
    }

    protected ItemStack[] toProducts(ItemStack[] ingredients, ItemStack tool) {
        if (!ItemStack.match(this.tool, tool) && !(tool == null && tool == null)) return null;
        if (shapeless) {
            for (ItemStack mystack : this.ingredients) {
                boolean match = false;
                for (ItemStack querystack : ingredients) {
                    if (ItemStack.match(mystack, querystack) && querystack.getCount() >= mystack.getCount()) {
                        match = true;
                    }
                }
                if (match == false) return null;
            }
        } else {
            for (int i = 0; i < this.ingredients.length; i++) {
                if (ItemStack.match(ingredients[i], this.ingredients[i]) && ingredients[i].getCount() >= this.ingredients[i].getCount()) {

                } else {
                    return null;
                }
            }
        }
        return products;
    }

    protected ItemStack[] toIngredients(ItemStack[] ingredients) {
        ItemStack[] ret = new ItemStack[ingredients.length];
        for (int i = 0; i < ingredients.length; i++) {
            if (shapeless) {
                for (ItemStack ingredient : this.ingredients) {
                    if (ItemStack.match(ingredients[i], ingredient)) {
                        ItemStack retstack = ItemStack.clone(ingredients[i]);
                        retstack.increaseCount(-ingredient.getCount());
                        ret[i] = retstack;
                    }
                }
            } else {
                if (ItemStack.match(ingredients[i], this.ingredients[i]) && ingredients[i].getCount() > this.ingredients[i].getCount()) {
                    ItemStack retstack = ItemStack.clone(ingredients[i]);
                    retstack.increaseCount(-this.ingredients[i].getCount());
                    ret[i] = retstack;
                }
            }
        }
        return ret;
    }

    public CraftingResult craft(CraftingQuery craftingQuery) {

        if (toProducts(craftingQuery.ingredients, craftingQuery.tool) == null) return null;
        CraftingResult ret = new CraftingResult();
        ret.ingredients = toIngredients(craftingQuery.ingredients);
        ret.products = toProducts(craftingQuery.ingredients, craftingQuery.tool);
        ret.tool = craftingQuery.tool;
        return ret;
    }
}

