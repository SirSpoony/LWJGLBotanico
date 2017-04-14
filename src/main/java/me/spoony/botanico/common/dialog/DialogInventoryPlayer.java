package me.spoony.botanico.common.dialog;

import me.spoony.botanico.common.crafting.CraftingQuery;
import me.spoony.botanico.common.crafting.CraftingResult;
import me.spoony.botanico.common.crafting.Recipes;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlotMode;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.ItemStackExchange;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by Colten on 11/25/2016.
 */
public class DialogInventoryPlayer extends Dialog implements DialogCrafting {
    public DialogInventoryPlayer() {
        super(Dialog.PLAYER_INV_CRAFTING_ID);
        this.inventory = new Inventory(3, Dialog.PLAYER_INV_CRAFTING_ID);
        this.inventory.getSlot(2).setMode(ItemSlotMode.TAKE_ONLY);
    }

    public void setCraftingInventory(Inventory inv) {
        this.inventory = inv;
    }

    public boolean canCraft() {
        if (queryCraft().products != null) {
            ItemStackExchange exchange = new ItemStackExchange(inventory.getStack(2), queryCraft().products[0]);
            if (exchange.mergeIntoOneStack()) {
                return true;
            }
        }
        return false;
    }

    public CraftingResult queryCraft() {
        CraftingQuery craftingQuery = new CraftingQuery();
        craftingQuery.setIngredients(new ItemStack[]{
                inventory.getStack(0),
                inventory.getStack(1),
        });
        return Recipes.GENERAL_RECIPES.craft(craftingQuery);
    }

    public void craft() {
        if (!canCraft()) return;
        CraftingResult craftingResult = queryCraft();

        inventory.setStack(0, craftingResult.ingredients[0]);
        inventory.setStack(1, craftingResult.ingredients[1]);

        ItemStackExchange ise = new ItemStackExchange(inventory.getStack(2), craftingResult.products[0]);
        ise.mergeIntoOneStack();

        inventory.setStack(2, ise.to);
    }

    @Override
    public void onButtonPressed(int button) {
        super.onButtonPressed(button);
        if (button == 0 && canCraft()) {
            craft();
            viewers.updateDialogAll();
        }
    }

    @Override
    public void onClose(EntityPlayer player) {
        super.onClose(player);

        if (player instanceof RemoteEntityPlayer) {
            RemoteEntityPlayer remoteEntityPlayer = (RemoteEntityPlayer) player;
            remoteEntityPlayer.giveItemStack(inventory.getStack(0));
            inventory.setStack(0, null);
            remoteEntityPlayer.giveItemStack(inventory.getStack(1));
            inventory.setStack(1, null);
            remoteEntityPlayer.giveItemStack(inventory.getStack(2));
            inventory.setStack(2, null);
        }
    }
}
