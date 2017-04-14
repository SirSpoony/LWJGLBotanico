package me.spoony.botanico.common.crafting;

import me.spoony.botanico.common.items.ItemStack;

import java.util.List;

/**
 * Created by Colten on 11/10/2016.
 */
public class CraftingQuery
{
    protected ItemStack[] ingredients;
    protected ItemStack tool;

    public CraftingQuery() {

    }

    public ItemStack[] getIngredients() {
        return ingredients;
    }

    public ItemStack getTool() {
        return tool;
    }

    public void setIngredients(ItemStack[] stacks) {
        this.ingredients = stacks;
    }

    public void setIngredients(List<ItemStack> stacks) {
        this.ingredients = stacks.toArray(new ItemStack[stacks.size()]);
    }

    public void setTool(ItemStack stack) {
        this.tool = stack;
    }

    public int ingredientsCount() {
        return ingredients.length;
    }

    public boolean isTool() {
        return tool != null;
    }
}
