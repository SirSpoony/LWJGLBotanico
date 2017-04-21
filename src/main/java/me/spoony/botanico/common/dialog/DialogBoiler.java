package me.spoony.botanico.common.dialog;

import com.google.gson.Gson;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.net.IPacketJsonInterpreter;

/**
 * Created by Colten on 1/1/2017.
 */
public class DialogBoiler extends Dialog implements IPacketJsonInterpreter {
    public static final int FUEL_SLOT_ID = 0;

    public float storedEnergy;
    public float energyProduction;

    public float burnProgress;
    public float waterProgress;

    public float initialBurnTime;
    public float currentBurnTime;

    public DialogBoiler() {
        super(BOILER_ID);
        this.inventory = new Inventory(1, BOILER_ID);
    }

    @Override
    public String toJson(Gson gson) {
        return gson.toJson(this);
    }

    @Override
    public void fromJson(Gson gson, String json) {
    }
}
