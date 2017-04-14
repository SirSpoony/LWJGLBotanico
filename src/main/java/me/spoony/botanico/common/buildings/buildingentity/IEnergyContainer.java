package me.spoony.botanico.common.buildings.buildingentity;

/**
 * Created by Colten on 12/28/2016.
 */
public interface IEnergyContainer {
    float getEnergyStored();
    float getEnergyCapacity();

    float extractEnergy(float energy, float max);
    float receiveEnergy(float energy, float max);
}
