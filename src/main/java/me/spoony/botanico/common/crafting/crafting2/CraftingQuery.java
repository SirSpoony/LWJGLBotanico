package me.spoony.botanico.common.crafting.crafting2;

import me.spoony.botanico.common.items.ItemStack;

/**
 * Created by Colten on 3/2/2017.
 */
public class CraftingQuery {
    public final ItemStack[][] stacks;
    public final int width;
    public final int height;

    public CraftingQuery(ItemStack[][] stacks, int width, int height) {
        this.stacks = removePadding(stacks, null);
        this.width = width;
        this.height = height;
    }

    public ItemStack[][] removePadding(ItemStack[][] stacks, ItemStack padding) {
        //first row
        boolean allnull = true;
        for (int ii = 0; ii < stacks[0].length; ii++) {
            if (stacks[0][ii] != null) {
                allnull = false;
            }
        }
        if (allnull) {
            
        }

        return removePadding(stacks, padding);
    }
}
