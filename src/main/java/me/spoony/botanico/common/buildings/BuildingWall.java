package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TileDirection;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 4/16/2017.
 */
public class BuildingWall extends Building {

  public BuildingWall(int id) {
    super(id);

    this.name = "wall";
    this.hardness = 10;
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, TilePosition position, byte extra,
      Color color) {
    boolean e = level.getBuilding(position.getNeighbor(TileDirection.EAST)) == Buildings.WALL;
    boolean n = level.getBuilding(position.getNeighbor(TileDirection.NORTH)) == Buildings.WALL;

    rg.sprite(position.toGamePosition(), getTextureSheet(),
        new IntRectangle(64, 240, 16, 32),
        color, position.y);

    if (e) {
      rg.sprite(position.toGamePosition(), getTextureSheet(),
          new IntRectangle(64+16, 240, 32, 32),
          color, position.y-.01);
    }

    if (n) {
      rg.sprite(position.toGamePosition(), getTextureSheet(),
          new IntRectangle(64+48, 224, 16, 48),
          color, position.y);
    }
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.WOOD;
  }
}
