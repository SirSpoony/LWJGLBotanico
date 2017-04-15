package me.spoony.botanico.server.net;

import com.google.common.base.Preconditions;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.net.server.SPacketBuildingChange;
import me.spoony.botanico.common.net.server.SPacketBuildingData;
import me.spoony.botanico.common.net.server.SPacketChunk;
import me.spoony.botanico.common.net.server.SPacketEntityState;
import me.spoony.botanico.common.net.server.SPacketTeleport;
import me.spoony.botanico.common.net.server.SPacketTileChange;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 11/20/2016.
 */
public class ServerPacketHandler {

  BotanicoServer server;

  public ServerPacketHandler(BotanicoServer server) {
    this.server = server;
  }

  public void sendBuildingChange(TilePosition position, ServerPlane plane, Building building) {
    SPacketBuildingChange pbc = new SPacketBuildingChange();
    pbc.x = position.x;
    pbc.y = position.y;
    pbc.building = building;
    server.getClientManager().sendPacketToAll(pbc, p -> p.getPlane() == plane);
  }

  public void sendTileChange(TilePosition position, ServerPlane plane, Tile tile) {
    SPacketTileChange pbc = new SPacketTileChange();
    pbc.x = position.x;
    pbc.y = position.y;
    pbc.tile = tile;
    server.getClientManager().sendPacketToAll(pbc, p -> p.getPlane() == plane);
  }

  public void sendBuildingDataChange(TilePosition position, ServerPlane plane, byte data) {
    SPacketBuildingData pbd = new SPacketBuildingData();
    pbd.x = position.x;
    pbd.y = position.y;
    pbd.data = data;
    server.getClientManager().sendPacketToAll(pbd, p -> p.getPlane() == plane);
  }

  public void sendChunk(Chunk chunk, RemoteEntityPlayer player) {
    Preconditions.checkNotNull(player);
    Preconditions.checkNotNull(chunk);

    SPacketChunk packet = new SPacketChunk();
    packet.buildings = chunk.buildings.clone();
    packet.tiles = chunk.tiles.clone();
    packet.buildingdata = chunk.buildingData.clone();
    packet.x = chunk.position.x;
    packet.y = chunk.position.y;

    server.getClientManager().sendPacket(packet, player);
  }

  public void sendEntityState(Entity entity) {
    SPacketEntityState pes = new SPacketEntityState();
    pes.eid = entity.eid;
    pes.state = entity.getState();
    server.getClientManager().sendPacketToAll(pes, p -> p.getPlane() == entity.getPlane());
  }

  public void sendTeleport(RemoteEntityPlayer player) {
    SPacketTeleport teleport = new SPacketTeleport();
    teleport.x = player.getPosition().x;
    teleport.y = player.getPosition().y;
    teleport.plane = player.getPlane().getID();
    server.getClientManager().sendPacket(teleport, player);
  }
}
