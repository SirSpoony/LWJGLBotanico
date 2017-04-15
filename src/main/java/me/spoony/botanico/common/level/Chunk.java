package me.spoony.botanico.common.level;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.util.position.ChunkPosition;

public class Chunk {

  public static final int CHUNK_SIZE = 32;

  public final Tile[] tiles;
  public final Building[] buildings;
  public final byte[] buildingData;

  public final ChunkPosition position;

  public Chunk(ChunkPosition position, Tile[] ts, Building[] bs, byte[] bd) {
    this.tiles = ts;
    this.buildings = bs;
    this.buildingData = bd;
    this.position = position;
  }

  public Tile getTile(int x, int y) {
    if (x >= 32 || y >= 32 || x < 0 || y < 0) {
      return null;
    }
    return tiles[x*32+y];
  }

  public void setTile(int x, int y, Tile tile) {
    this.tiles[x*32+y] = tile;
  }

  public Building getBuilding(int x, int y) {
    return buildings[x*32+y];
  }

  public void setBuilding(int x, int y, Building b) {
    this.buildings[x*32+y] = b;
    if (b == null) {
      setBuildingData(x, y, (byte) 0);
    }
  }

  public void setBuildingData(int x, int y, byte b) {
    this.buildingData[x*32+y] = b;
  }

  public byte getBuildingData(int x, int y) {
    return buildingData[x*32+y];
  }


  public void update(float timeDiff) {

  }
}
