package me.spoony.botanico.common.buildings.buildingentity;

import me.spoony.botanico.common.dialog.DialogFurnace;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.OmniPosition;

/**
 * Created by Colten on 12/30/2016.
 */
public class BuildingEntityFurnace extends BuildingEntity implements Updatable {
    public DialogFurnace dialog;

    public BuildingEntityFurnace(OmniPosition position, IPlane plane)
    {
        super(position, plane);

        DialogFurnace d = new DialogFurnace();
        this.dialog = d;
    }


    @Override
    public void update(float delta) {
        if (!canSmelt()) {
            dialog.progress = 0;
            return;
        }

        if (!ensureFuel()) {
            dialog.burnProgress = 0;
            return;
        }

        dialog.progress += .2f * delta;
        dialog.currentBurnTime -= .02f * delta;

        dialog.burnProgress = dialog.currentBurnTime /dialog.initialBurnTime;

        if (dialog.progress > 1) {
            dialog.progress = 0;
            dialog.inventory.getStack(DialogFurnace.INGREDIENT_SLOT_ID).increaseCount(-1);
            if (dialog.inventory.getStack(DialogFurnace.PRODUCT_SLOT_ID) == null) dialog.inventory.setStack(DialogFurnace.PRODUCT_SLOT_ID, new ItemStack(Items.CHARCOAL));
            else dialog.inventory.getStack(DialogFurnace.PRODUCT_SLOT_ID).increaseCount(1);
        }
    }

    public boolean canSmelt() {
        ItemStack ingstack = dialog.inventory.getStack(DialogFurnace.INGREDIENT_SLOT_ID);
        ItemStack productstack = dialog.inventory.getStack(DialogFurnace.PRODUCT_SLOT_ID);

        if (ingstack != null && ingstack.getItem() == Items.WOOD &&
                (productstack == null || productstack.getItem() == Items.CHARCOAL)) {
            return true;
        }

        return false;
    }

    public boolean ensureFuel() {
        if (dialog.currentBurnTime > 0) return true;
        ItemStack stack = dialog.inventory.getStack(DialogFurnace.FUEL_SLOT_ID);
        if (stack == null) return false;

        float burntime = stack.getItem().getBurnTime();
        if (burntime != 0) {
            stack.increaseCount(-1);
            dialog.initialBurnTime = burntime;
            dialog.currentBurnTime = burntime;
            return true;
        }
        return false;
    }
}
