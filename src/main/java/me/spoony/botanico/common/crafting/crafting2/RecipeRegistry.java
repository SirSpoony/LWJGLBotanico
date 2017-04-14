package me.spoony.botanico.common.crafting.crafting2;

import me.spoony.botanico.common.crafting.crafting2.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Colten on 3/2/2017.
 */
public class RecipeRegistry {
    protected final Set<Recipe> recipes;
    protected final CraftingForm form;

    public RecipeRegistry(CraftingForm form) {
        this.recipes = new HashSet<>();
        this.form = form;
    }

    public boolean registerRecipe(Recipe r) {
        if (!form.doesMatchForm(r)) return false;

        this.recipes.add(r);
        return true;
    }
}
