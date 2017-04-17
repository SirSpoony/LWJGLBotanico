package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.util.DoubleRectangle;

/**
 * Created by Colten on 4/15/2017.
 */
public class BuildingBush extends Building {
  public BuildingBush(int id) {
    super(id);
    this.name = "bush";
    this.setTextureBounds(48, 0, 32, 32);
    this.hardness = 5;
    this.alwaysBehindCharacter = false;
    this.collisionBounds = new DoubleRectangle(0,0,2,1.5);
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }
}

