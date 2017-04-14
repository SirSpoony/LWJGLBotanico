package me.spoony.botanico.common.dialog;

import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlotMode;
import me.spoony.botanico.common.net.IPacketInterpreter;
import me.spoony.botanico.common.net.PacketDecoder;
import me.spoony.botanico.common.net.PacketEncoder;

/**
 * Created by Colten on 1/1/2017.
 */
public class DialogBoiler extends Dialog implements IPacketInterpreter {
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
    public void encodeToPacket(PacketEncoder encoder) {
        encoder.writeFloat(storedEnergy);
        encoder.writeFloat(energyProduction);
        encoder.writeFloat(burnProgress);
        encoder.writeFloat(waterProgress);
    }

    @Override
    public void decodeFromPacket(PacketDecoder decoder) {
        storedEnergy = decoder.readFloat();
        energyProduction = decoder.readFloat();
        burnProgress = decoder.readFloat();
        waterProgress = decoder.readFloat();
    }
}
