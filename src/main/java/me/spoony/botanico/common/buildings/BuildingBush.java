package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 4/15/2017.
 *
 * DATA:
 * 0: nothing
 * 1: blueberry
 */
public class BuildingBush extends Building {

  public BuildingBush(int id) {
    super(id);
    this.name = "bush";
    this.hardness = 5;
    this.alwaysBehindCharacter = false;
    this.collisionBounds = new DoubleRectangle(0, 0, 2, 1.5);
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, OmniPosition position, int d, Color c) {
    int regionX = 128 + (d == 1 ? 0 : 32);
    rg.sprite(position, getTextureSheet(),
        new IntRectangle(regionX, 160, 32, 32), c, position.getGameY());
  }

  @Override
  public void onClick(IPlane plane, EntityPlayer player, OmniPosition position) {
    if (plane.isLocal()) {
      return;
    }

    ServerPlane serverPlane = (ServerPlane) plane;
    RemoteEntityPlayer remoteEntityPlayer = (RemoteEntityPlayer) player;
    int data = plane.getBuildingData(position);

    switch (data) {
      case 1:
        remoteEntityPlayer.giveItemStack(new ItemStack(Items.BLUEBERRY));
        serverPlane.setBuildingData(position, 0);
        break;
      default:
        break;
    }
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }
}

