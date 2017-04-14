package me.spoony.botanico.common.crafting.crafting2;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import me.spoony.botanico.common.items.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Colten on 3/2/2017.
 */
public class Recipe {

    private final String[] shape;
    private final Map<Character, ItemStack> itemStackMap;

    public Recipe(String[] shape, Map<Character, ItemStack> itemStackMap) {
        Preconditions.checkArgument(validRecipeShape(shape));
        Preconditions.checkArgument(validItemStackMap(shape, itemStackMap));

        this.shape = shape;
        this.itemStackMap = itemStackMap;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        return shape[0].length();
    }

    public boolean testCraftingQuery(CraftingQuery query) {
        for (int x = 0; x < query.width; x++) {
            for (int y = 0; y < query.width; y++) {
                ItemStack queryStack = query.stacks[x][y];
                ItemStack recipeStack = itemStackMap.get(shape[y].charAt(x));

            }
        }
        return true;
    }

    private boolean validRecipeShape(String[] shape) {
        int size = shape[0].length();
        for (String s : shape) {
            if (size != s.length()) {
                return false;
            }
        }
        return true;
    }

    private boolean validItemStackMap(String[] shape, Map<Character, ItemStack> itemStackMap) {
        Set<Character> characterSet = Sets.newHashSet();

        for (String s : shape) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c != ' ') {
                    characterSet.add(c);
                }
            }
        }

        for (Character c : characterSet) {
            if (itemStackMap.get(c) == null) {
                return false;
            }
        }

        return true;
    }
}
