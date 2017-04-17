package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;

import java.util.Random;
import me.spoony.botanico.common.util.position.OmniPosition;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingDirtMound extends Building {

  public BuildingDirtMound(int id) {
    super(id);
    this.name = "dirt_mound";
    shouldCollide = false;

    this.setTextureBounds(16, 96, 16, 16);
    this.alwaysBehindCharacter = true;

    this.hardness = 1;
  }

  @Override
  public ItemStack[] getDrops(IPlane level, OmniPosition position) {
    return new ItemStack[]
        {
            new ItemStack(Items.DIRT, new Random().nextInt(3) + 2),
        };
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.DIRT;
  }
}
