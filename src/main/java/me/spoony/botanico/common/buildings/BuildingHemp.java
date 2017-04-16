package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityCrop;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.level.ServerPlane;

import java.util.Random;

/**
 * Created by Colten on 1/2/2017.
 */
public class BuildingHemp extends Building implements IBuildingEntityHost {

  public BuildingHemp(int id) {
    super(id);
    this.name = "hemp";
    shouldCollide = false;
    this.hardness = .1f;
  }

  @Override
  public boolean canCreate(IPlane level, TilePosition position) {
    return super.canCreate(level, position) && level.getTile(position) == Tiles.FERTILIZED_GROUND;
  }

  @Override
  public void create(IPlane level, TilePosition position) {
    if (!(level instanceof ServerPlane)) {
      return;
    }
    ServerPlane serverLevel = (ServerPlane) level;

    serverLevel.setBuildingData(position, (byte) 0);
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d,
      Color color) {
    rg.sprite(position.toGamePosition(), getTextureSheet(),
        new IntRectangle(80 + d * 16, 128, 16, 32), color,
        position.y);
  }

  @Override
  public ItemStack[] getDrops(IPlane level, TilePosition position) {
    if (!(level instanceof ServerPlane)) {
      return new ItemStack[]{new ItemStack(Items.WHEAT_SEEDS, 1)};
    }
    ServerPlane serverLevel = (ServerPlane) level;

    BuildingEntityCrop bec = (BuildingEntityCrop) serverLevel.getBuildingEntity(position);
    if (bec.isMature()) {
      return new ItemStack[]{new ItemStack(Items.HEMP_FIBER, new Random().nextInt(3) + 1)};
    } else {
      return new ItemStack[]{new ItemStack(Items.HEMP_SEEDS, 1)};
    }
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }

  @Override
  public BuildingEntity createNewEntity(IPlane plane, TilePosition position) {
    return new BuildingEntityCrop(position, plane, (byte) 4);
  }
}