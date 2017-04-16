package me.spoony.botanico.common.crafting;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import me.spoony.botanico.common.items.ItemStack;

/**
 * Created by Colten on 11/12/2016.
 */
public class RecipeRegistry {

  protected List<Recipe> recipes;

  public void setMaxIngredientCount(int maxIngredientCount) {
    this.maxIngredientCount = maxIngredientCount;
  }

  protected int maxIngredientCount;

  public RecipeRegistry() {
    recipes = Lists.newArrayList();
    maxIngredientCount = 4;
  }

  public void register(Recipe recipe) {
    Preconditions.checkNotNull(recipe);

    recipes.add(recipe);
  }

  public Recipe[] query(CraftingQuery query) {
    List<Recipe> ret = Lists.newArrayList();

    for (Recipe r : recipes) {
      if (r.test(query)) {
        ret.add(r);
      }
    }

    return ret.toArray(new Recipe[ret.size()]);
  }
}
