package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntity;
import me.spoony.botanico.common.buildings.buildingentity.BuildingEntityJar;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 12/18/2016.
 */
public class BuildingJar extends Building implements IBuildingEntityHost {

  public BuildingJar(int id) {
    super(id);
    this.name = "jar";
    this.shouldCollide = true;
    this.alwaysBehindCharacter = false;
    this.hardness = 1f;
  }

  @Override
  public boolean onClick(IPlane level, EntityPlayer player, OmniPosition position) {
    if (!(level instanceof ServerPlane)) {
      return false;
    }
    ServerPlane serverLevel = (ServerPlane) level;

    BuildingEntity be = serverLevel.getBuildingEntity(position);
    if (!(be instanceof BuildingEntityJar)) {
      return false;
    }
    BuildingEntityJar bej = (BuildingEntityJar) be;

    ((RemoteEntityPlayer) player)
        .sendMessage("Jar Contents: " + bej.getEnergyStored() + "/" + bej.getEnergyCapacity());

    return false;
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, OmniPosition position, byte d,
      Color color) {
    rg.sprite(position, getTextureSheet(),
        new IntRectangle(0, 160, 16, 16),
        color, position.getGameY());
    rg.sprite(position, getTextureSheet(),
        new IntRectangle(16, 160, 16, 16),
        color, position.getGameY());
  }

  @Override
  public BuildingEntity createNewEntity(IPlane plane, OmniPosition position) {
    return new BuildingEntityJar(position, plane);
  }
}
