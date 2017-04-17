package me.spoony.botanico.common.buildings.buildingentity;

import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 12/28/2016.
 */
public class BuildingEntityEnergyPipe extends BuildingEntity implements Updatable, IEnergyContainer {
    public byte contentsType;
    public float energy;

    public BuildingEntityEnergyPipe(OmniPosition position, IPlane plane) {
        super(position, plane);

        contentsType = (byte) -1;
        energy = 0f;
    }

    @Override
    public void update(float delta) {
        for (int ox = -1; ox <= 1; ox++) {
            for (int oy = -1; oy <= 1; oy++) {
                if (ox == 1 && oy == 1) continue;
                if (ox == -1 && oy == -1) continue;
                if (ox == 1 && oy == -1) continue;
                if (ox == -1 && oy == 1) continue;
                BuildingEntity obe = ((ServerPlane)plane).getBuildingEntity(new OmniPosition(
                    PositionType.GAME, position.getX(PositionType.GAME) + ox, position.getY(PositionType.GAME) + oy));
                if (obe == null) continue;
                if (obe instanceof IEnergyContainer) {
                    IEnergyContainer energyContainer = (IEnergyContainer) obe;

                    if (energyContainer.getEnergyStored() > getEnergyStored()) {
                        float transferred = energyContainer.extractEnergy(1, getEnergyCapacity()-getEnergyStored());
                        energy+=transferred;
                    }
                }
            }
        }
    }

    @Override
    public float getEnergyStored() {
        return energy;
    }

    @Override
    public float getEnergyCapacity() {
        return 5;
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