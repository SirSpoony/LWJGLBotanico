package me.spoony.botanico.common.buildings.buildingentity;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.Timer;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by Colten on 12/18/2016.
 */
public class BuildingEntityJar extends BuildingEntity implements Updatable, IEnergyContainer {

  public byte contentsType;
  public float energy;
  Timer updateTimer = new Timer(0f);

  public BuildingEntityJar(OmniPosition position, IPlane plane) {
    super(position, plane);

    contentsType = (byte) -1;
    energy = 0f;
  }

  @Override
  public void update(float delta) {
    if (energy >= getEnergyCapacity()) {
      return;
    }

    if (true) {
      int mysticFlowerCount = 0;

      for (int ox = -2; ox <= 2; ox++) {
        for (int oy = -2; oy <= 2; oy++) {
          Building obuilding = plane.getBuilding(
              new OmniPosition(PositionType.GAME, position.getX(PositionType.GAME) + ox,
                  position.getY(PositionType.GAME) + oy));
          if (obuilding == Buildings.MYSTIC_FLOWER) {
            mysticFlowerCount++;
          }
        }
      }

      energy += mysticFlowerCount * .01f;

      if (energy > getEnergyCapacity()) {
        energy = getEnergyCapacity();
      }
    }
  }

  @Override
  public float getEnergyStored() {
    return energy;
  }

  @Override
  public float getEnergyCapacity() {
    return 100;
  }

  @Override
  public float extractEnergy(float energy, float max) {
    float toTransfer = energy;
    if (toTransfer > getEnergyStored()) {
      toTransfer = getEnergyStored();
    }
    toTransfer = BMath.clamp(toTransfer, 0, max);
    this.energy -= toTransfer;
    return toTransfer;
  }

  @Override
  public float receiveEnergy(float energy, float max) {
    float toTransfer = energy;
    if (toTransfer + getEnergyStored() > getEnergyCapacity()) {
      toTransfer = getEnergyCapacity() - getEnergyStored();
    }
    toTransfer = BMath.clamp(toTransfer, 0, max);
    this.energy += toTransfer;
    return toTransfer;
  }
}
