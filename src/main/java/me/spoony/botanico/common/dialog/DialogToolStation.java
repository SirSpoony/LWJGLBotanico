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
}
