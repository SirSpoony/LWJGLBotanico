package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 12/28/2016.
 */
public class BuildingMysticFlower extends Building {

  public BuildingMysticFlower(int id) {
    super(id);
    this.name = "mystic_flower";
    shouldCollide = false;

    this.alwaysBehindCharacter = false;
    this.setTextureBounds(16, 176, 16, 32);
    this.hardness = .1f;
  }

  @Override
  public boolean canCreate(IPlane level, TilePosition position) {
    return super.canCreate(level, position) && level.getTile(position) == Tiles.GROUND;
  }

  @Override
  public ItemStack[] getDrops(IPlane level, TilePosition position) {
    return new ItemStack[]{new ItemStack(Items.MYSTIC_FLOWER, 1)};
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }
}