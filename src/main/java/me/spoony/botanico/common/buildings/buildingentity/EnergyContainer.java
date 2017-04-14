package me.spoony.botanico.common.buildings.buildingentity;

import me.spoony.botanico.common.util.BMath;

/**
 * Created by Colten on 12/28/2016.
 */
public class EnergyContainer implements IEnergyContainer {
    protected float energy;
    protected float capacity;

    @Override
    public float getEnergyStored() {
        return energy;
    }

    @Override
    public float getEnergyCapacity() {
        return capacity;
    }

    @Override
    public float extractEnergy(float energy, float max) {
        float toTransfer = energy;
        if (toTransfer > getEnergyStored()) toTransfer = getEnergyStored();
        toTransfer = BMath.clamp(toTransfer, 0, max);
        this.energy -= toTransfer;
        return toTransfer;
    }

    @Override
    public float receiveEnergy(float energy, float max) {
        float toTransfer = energy;
        if (toTransfer+getEnergyStored() > getEnergyCapacity()) toTransfer = getEnergyCapacity()-getEnergyStored();
        toTransfer = BMath.clamp(toTransfer, 0, max);
        this.energy += toTransfer;
        return toTransfer;
    }
}
