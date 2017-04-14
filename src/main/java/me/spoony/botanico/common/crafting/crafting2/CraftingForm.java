package me.spoony.botanico.common.crafting.crafting2;

import com.google.common.base.Preconditions;

/**
 * Created by Colten on 3/2/2017.
 */
public class CraftingForm {
    public final int width;
    public final int height;

    public CraftingForm(int width, int height) {
        Preconditions.checkArgument(width > 0);
        Preconditions.checkArgument(height > 0);

        this.width = width;
        this.height = height;
    }

    public boolean doesMatchForm(CraftingQuery query) {
        Preconditions.checkNotNull(query);
        Preconditions.checkArgument(query.stacks.length != 0);

        if (query.width > width) return false;
        if (query.height > height) return false;
        return true;
    }

    public boolean doesMatchForm(Recipe recipe) {
        if (recipe.getWidth() > width) return false;
        if (recipe.getHeight() > height) return false;
        return true;
    }
}
