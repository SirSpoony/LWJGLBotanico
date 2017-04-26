package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityEnergyPipe;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.TileDirection;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 12/28/2016.
 */
public class BuildingEnergyPipe extends Building implements IBuildingEntityHost {

  public BuildingEnergyPipe(int id) {
    super(id);

    this.shouldCollide = true;
    this.name = "energy_pipe";
    this.alwaysBehindCharacter = false;
    this.hardness = 1;
    this.collisionBounds.set(.2d, .2d, .6d, .6d);
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, OmniPosition position, int d,
      Color color) {
    renderPipe(rg, level, position, d, color);
    renderPipeConnections(rg, level, position, d, color);
  }

  private void renderPipe(RendererGame rg, ClientPlane level, OmniPosition position, int d,
      Color color) {
    boolean u, dwn, l, r;
    u = dwn = l = r = false;
    if (level.getBuilding(position.getTileNeighbor(TileDirection.NORTH)) == Buildings.ENERGY_PIPE) {
      u = true;
    }
    if (level.getBuilding(position.getTileNeighbor(TileDirection.SOUTH)) == Buildings.ENERGY_PIPE) {
      dwn = true;
    }
    if (level.getBuilding(position.getTileNeighbor(TileDirection.EAST)) == Buildings.ENERGY_PIPE) {
      r = true;
    }
    if (level.getBuilding(position.getTileNeighbor(TileDirection.WEST)) == Buildings.ENERGY_PIPE) {
      l = true;
    }

    if (l && r && !u && !dwn) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(0+32, 48+64, 16, 16), color, position.getGameY());
      return;
    }
    if (!l && !r && u && dwn) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(16+32, 48+64, 16, 16), color, position.getGameY());
      return;
    }

    if (u) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(48+32, 32+64, 16, 16), color, position.getGameY());
    }
    rg.sprite(position, getTextureSheet(),
        new IntRectangle(0+32, 16+64, 16, 16), color, position.getGameY());
    if (dwn) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(16+32, 32+64, 16, 16), color, position.getGameY());
    }
    if (l) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(0+32, 32+64, 16, 16), color, position.getGameY());
    }
    if (r) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(32+32, 32+64, 16, 16), color, position.getGameY());
    }
  }

  private void renderPipeConnections(RendererGame rg, ClientPlane level, OmniPosition position,
      int d, Color color) {
    boolean u, dwn, l, r;
    u = dwn = l = r = false;
    if (level.getBuilding(position.getTileNeighbor(TileDirection.NORTH)) == Buildings.JAR) {
      u = true;
    }
    if (level.getBuilding(position.getTileNeighbor(TileDirection.SOUTH)) == Buildings.JAR) {
      dwn = true;
    }
    if (level.getBuilding(position.getTileNeighbor(TileDirection.WEST)) == Buildings.JAR) {
      l = true;
    }
    if (level.getBuilding(position.getTileNeighbor(TileDirection.EAST)) == Buildings.JAR) {
      r = true;
    }
    if (u) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(48, 0, 16, 16), color,
          position.getGameY());
    }
    if (dwn) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(16, 0, 16, 16), color,
          position.getGameY());
    }
    if (l) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(0, 0, 16, 16), color,
          position.getGameY());
    }
    if (r) {
      rg.sprite(position, getTextureSheet(),
          new IntRectangle(32, 0, 16, 16), color,
          position.getGameY());
    }
  }

  @Override
  public void onClick(IPlane level, EntityPlayer player, OmniPosition position) {
    if (!(level instanceof ServerPlane)) {
      return;
    }
    ServerPlane serverLevel = (ServerPlane) level;

    BuildingEntity be = serverLevel.getBuildingEntity(position);
    if (!(be instanceof BuildingEntityEnergyPipe)) {
      return;
    }
    BuildingEntityEnergyPipe beep = (BuildingEntityEnergyPipe) be;

    ((RemoteEntityPlayer) player)
        .sendMessage("Pipe Contents: " + beep.getEnergyStored() + "/" + beep.getEnergyCapacity());

  }

  @Override
  public ItemStack[] getDrops(IPlane level, OmniPosition position) {
    return new ItemStack[]{new ItemStack(Items.ENERGY_PIPE)};
  }

  @Override
  public BuildingEntity createNewEntity(IPlane plane, OmniPosition position) {
    return new BuildingEntityEnergyPipe(position, plane);
  }
}
