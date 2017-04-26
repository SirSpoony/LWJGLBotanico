package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.net.IClientHandler;
import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.common.net.PacketDecoder;
import me.spoony.botanico.common.net.PacketEncoder;
import me.spoony.botanico.common.tiles.Tile;

/**
 * Created by Colten on 11/22/2016.
 */
public class SPacketChunk implements Packet, IClientHandler {

  public long x;
  public long y;
  public Tile[] tiles;
  public Building[] buildings;
  public int[] buildingdata;

  @Override
  public void encode(PacketEncoder encoder) {
    encoder.writeLong(x);
    encoder.writeLong(y);
    try {
      int[] itiles = new int[32 * 32];
      int[] ibuildings = new int[32 * 32];
      int[] ibuildingdata = new int[32 * 32];
      for (int x = 0; x < 32; x++) {
        for (int y = 0; y < 32; y++) {
          itiles[x * 32 + y] = (tiles[x * 32 + y] != null ? tiles[x * 32 + y].getID() : -1);
          ibuildings[x * 32 + y] = (buildings[x * 32 + y] != null ? buildings[x * 32 + y].getID()
              : -1);
          ibuildingdata[x * 32 + y] = buildingdata[x * 32 + y];
        }
      }
      encoder.writeIntArray(itiles);
      encoder.writeIntArray(ibuildings);
      encoder.writeIntArray(ibuildingdata);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void decode(PacketDecoder decoder) {
    x = decoder.readLong();
    y = decoder.readLong();
    int[] itiles = decoder.readIntArray();
    int[] ibuildings = decoder.readIntArray();
    int[] ibuildingdata = decoder.readIntArray();
    tiles = new Tile[32 * 32];
    buildings = new Building[32 * 32];
    buildingdata = new int[32 * 32];
    for (int x = 0; x < 32; x++) {
      for (int y = 0; y < 32; y++) {
        tiles[x * 32 + y] = Tile.REGISTRY.get(itiles[x * 32 + y]);
        buildings[x * 32 + y] = Building.REGISTRY.getBuilding(ibuildings[x * 32 + y]);
        buildingdata[x * 32 + y] = ibuildingdata[x * 32 + y];
      }
    }
  }


  @Override
  public void onReceive(BotanicoClient client) {
    client.getLocalLevel()
        .receiveChunk(new Chunk(x, y, tiles, buildings, buildingdata));
  }
}
