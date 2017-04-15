package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;
import me.spoony.botanico.common.net.NotTransferable;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 11/23/2016.
 */
public class SPacketBuildingChange extends AutoPacketAdapter implements IClientHandler {

  public long x;
  public long y;

  @NotTransferable
  public Building building;

  private int buildingid;

  @Override
  public void preEncode() {
    if (building == null) {
      buildingid = -1;
    } else {
      buildingid = building.getID();
    }
  }

  @Override
  public void postDecode() {
    if (buildingid == -1) {
      this.building = null;
    } else {
      this.building = Building.REGISTRY.getBuilding(buildingid);
    }
  }

  @Override
  public void onReceive(BotanicoClient client) {
    client.getLocalLevel().receiveBuildingUpdate(new TilePosition(x, y), building);
  }
}
