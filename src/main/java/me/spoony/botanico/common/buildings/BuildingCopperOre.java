package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 1/2/2017.
 */
public class BuildingCopperOre extends Building {

  public BuildingCopperOre(int id) {
    super(id);

    name = "copper_ore";
    this.setTextureBounds(16, 112, 16, 16);

    this.hardness = 10;
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.ROCK;
  }

  @Override
  public ItemStack[] getDrops(IPlane level, TilePosition position) {
    return new ItemStack[]{new ItemStack(Items.COPPER_ORE)};
  }
}
