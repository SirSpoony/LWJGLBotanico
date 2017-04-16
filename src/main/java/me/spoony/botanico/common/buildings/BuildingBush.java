package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 4/15/2017.
 */
public class BuildingBush extends Building {
  public BuildingBush(int id) {
    super(id);
    this.name = "bush";
    this.setTextureBounds(48, 0, 32, 32);
    this.hardness = Float.MAX_VALUE;
    this.alwaysBehindCharacter = false;
    this.collisionBounds = new DoubleRectangle(0,0,2,1.5);
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }
}

