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
    this.textureName = "building/bush.png";
    this.hardness = Float.MAX_VALUE;
    this.alwaysBehindCharacter = false;
    this.collisionBounds = new DoubleRectangle(0,0,2,1.5);
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d, boolean highlight) {
    rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
        new IntRectangle(0, 0, 32, 32), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y);
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }
}

