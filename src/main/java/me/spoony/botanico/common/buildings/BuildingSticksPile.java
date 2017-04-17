package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.common.util.IntRectangle;

import java.util.Random;
import me.spoony.botanico.common.util.position.OmniPosition;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingSticksPile extends Building {

  public BuildingSticksPile(int id) {
    super(id);
    this.name = "sticks_pile";
    this.shouldCollide = false;
    this.hardness = .5f;
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, OmniPosition position, byte d,
      Color color) {
    int val = hash(position.getTileX(), position.getTileY()) % 4;
    rg.sprite(position, getTextureSheet(),
        new IntRectangle(48 + 16 * val, 176, 16, 16), color,
        position.y + 1);
  }

  private long OffsetBasis = 216613626;
  private long FnvPrime = 16777619;

  public int hash(long a, long b) {
    return Math.abs((int) ((((OffsetBasis ^ a) * FnvPrime) ^ b) * FnvPrime));
  }

  @Override
  public ItemStack[] getDrops(IPlane level, OmniPosition tilePosition) {
    return new ItemStack[]
        {
            new ItemStack(Items.WOOD, new Random().nextInt(3) + 1),
        };
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.WOOD;
  }
}
