package me.spoony.botanico.common.dialog;

import me.spoony.botanico.common.crafting.CraftingQuery;
import me.spoony.botanico.common.crafting.CraftingResult;
import me.spoony.botanico.common.crafting.Recipes;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlotMode;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.ItemStackExchange;

/**
 * Created by Colten on 11/25/2016.
 */
public class DialogToolStation extends Dialog {
    public DialogToolStation() {
        super(Dialog.TOOL_STATION_ID);
        this.inventory = new Inventory(4, Dialog.TOOL_STATION_ID);
        this.inventory.getSlot(3).setMode(ItemSlotMode.TAKE_ONLY);
    }

    public CraftingResult queryCraft() {
        CraftingQuery craftingQuery = new CraftingQuery();
        craftingQuery.setIngredients(new ItemStack[]{
                inventory.getStack(0),
                inventory.getStack(1),
                inventory.getStack(2),
        });
        return Recipes.TOOL_STATION_RECIPES.craft(craftingQuery);
    }

    public boolean canCraft() {
        if (queryCraft().products != null) {
            ItemStackExchange exchange = new ItemStackExchange(inventory.getStack(3), queryCraft().products[0]);
            if (exchange.mergeIntoOneStack()) {
                return true;
            }
        }
        return false;
    }

    public void craft() {
        if (!canCraft()) return;
        CraftingResult craftingResult = queryCraft();

        inventory.setStack(0, craftingResult.ingredients[0]);
        inventory.setStack(1, craftingResult.ingredients[1]);
        inventory.setStack(2, craftingResult.ingredients[2]);

        ItemStackExchange ise = new ItemStackExchange(inventory.getStack(3), craftingResult.products[0]);
        ise.mergeIntoOneStack();

        inventory.setStack(3, ise.to);
    }

    @Override
    public void onButtonPressed(int button) {
        super.onButtonPressed(button);
        if (button == 0 && canCraft()) {
            this.craft();
            viewers.updateDialogAll();
        }
    }
}
