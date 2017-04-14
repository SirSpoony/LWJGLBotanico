package me.spoony.botanico.common.buildings.buildingentity;

import com.google.common.math.DoubleMath;
import me.spoony.botanico.common.dialog.DialogBoiler;
import me.spoony.botanico.common.dialog.DialogFurnace;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 1/1/2017.
 */
public class BuildingEntityBoiler extends BuildingEntity implements Updatable {
    public DialogBoiler dialog;

    public float constantgentime;

    public BuildingEntityBoiler(TilePosition position, IPlane plane) {
        super(position, plane);

        DialogBoiler d = new DialogBoiler();
        this.dialog = d;
    }

    @Override
    public void update(float delta) {
        if (ensureFuel() && ensureWater()) {
            constantgentime += delta;

            dialog.energyProduction = (float) (1 - Math.pow(Math.E, -constantgentime)); // 1-e^(1-x) rounded to 100
            dialog.currentBurnTime -= .02f * delta;
            dialog.waterProgress -= .02f * delta;

        } else {
            constantgentime = 0;
            dialog.energyProduction -= .5 * delta;
            if (dialog.energyProduction < 0) {
                dialog.energyProduction = 0;
            }
        }

        dialog.burnProgress = dialog.currentBurnTime / dialog.initialBurnTime;

        dialog.viewers.updateDialogAll();
    }

    public boolean ensureFuel() {
        if (dialog.currentBurnTime > 0) return true;
        dialog.burnProgress = 0;
        dialog.currentBurnTime = 0;
        dialog.initialBurnTime = 0;

        ItemStack stack = dialog.inventory.getStack(DialogBoiler.FUEL_SLOT_ID);
        if (stack == null) return false;

        float burntime = stack.getItem().getBurnTime();
        if (burntime != 0) {
            stack.increaseCount(-1);
            dialog.initialBurnTime = burntime;
            dialog.currentBurnTime = burntime;
            dialog.burnProgress = 1;
            return true;
        }
        return false;
    }

    public boolean ensureWater() {
        if (dialog.waterProgress < 0) dialog.waterProgress = 0;
        return dialog.waterProgress > 0;
    }
}
