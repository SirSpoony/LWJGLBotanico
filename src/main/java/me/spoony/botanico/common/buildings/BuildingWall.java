package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.TileDirection;

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
  public void render(RendererGame rg, ClientPlane level, OmniPosition position, byte extra,
      Color color) {
    boolean e = level.getBuilding(position.getTileNeighbor(TileDirection.EAST)) == Buildings.WALL;
    boolean n = level.getBuilding(position.getTileNeighbor(TileDirection.NORTH)) == Buildings.WALL;

    rg.sprite(position, getTextureSheet(),
        new IntRectangle(64, 240, 16, 32),
        color, position.y);

    if (e) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(64 + 16, 240, 32, 32),
          color, position.y - .01);
    }

    if (n) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(64 + 48, 224, 16, 48),
          color, position.y);
    }
  }

  @Override
  public ItemStack[] getDrops(IPlane level, OmniPosition tilePosition) {
    return new ItemStack[]
        {
            new ItemStack(Items.WALL),
        };
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.WOOD;
  }
}
