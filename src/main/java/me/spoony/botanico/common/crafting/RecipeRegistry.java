package me.spoony.botanico.common.crafting;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colten on 11/12/2016.
 */
public class RecipeRegistry {
    protected List<Recipe> recipes;

    public void setIngredientStackRange(Range ingredientStackRange) {
        this.ingredientStackRange = ingredientStackRange;
    }

    protected Range ingredientStackRange;

    public RecipeRegistry() {
        recipes = Lists.newArrayList();
        ingredientStackRange = Range.closed(0, 2);
    }

    public CraftingResult craft(CraftingQuery craftingQuery) {
        Preconditions.checkArgument(validQuery(craftingQuery));

        for (Recipe r : recipes) {
            if (r.craft(craftingQuery) != null) {
                return r.craft(craftingQuery);
            }
        }

        CraftingResult cr = new CraftingResult();
        cr.ingredients = craftingQuery.getIngredients();
        cr.products = null;
        cr.tool = null;
        return cr;
    }

    public boolean validQuery(CraftingQuery craftingQuery) {
        Preconditions.checkNotNull(craftingQuery);
        if (!ingredientStackRange.contains(craftingQuery.ingredientsCount())) return false;
        return true;
    }

    public void register(Recipe recipe) {
        Preconditions.checkNotNull(recipe);
        recipes.add(recipe);
    }
}
