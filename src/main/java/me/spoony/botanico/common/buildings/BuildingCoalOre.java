package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 1/2/2017.
 */
public class BuildingCoalOre extends Building {

  public BuildingCoalOre(int id) {
    super(id);

    name = "coal_ore";
    this.setTextureBounds(0, 112, 16, 16);

    this.hardness = 10;
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.ROCK;
  }

  @Override
  public ItemStack[] getDrops(IPlane level, TilePosition position) {
    return new ItemStack[]{new ItemStack(Items.CHARCOAL)};
  }
}
