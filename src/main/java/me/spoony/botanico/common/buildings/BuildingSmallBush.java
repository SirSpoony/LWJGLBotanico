package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.util.DoubleRectangle;

/**
 * Created by Colten on 4/16/2017.
 */
public class BuildingSmallBush extends Building {
  public BuildingSmallBush(int id) {
    super(id);
    this.name = "small_bush";
    this.setTextureBounds(112, 160, 16, 32);
    this.hardness = 2.5f;
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }
}

