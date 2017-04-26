package me.spoony.botanico.common.level;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.tiles.Tile;

public class Chunk {

  public static final int CHUNK_SIZE = 32;

  public final long x;
  public final long y;

  public final Tile[] tiles;
  public final Building[] buildings;
  public final int[] buildingData;

  public Chunk(long x, long y, Tile[] ts, Building[] bs, int[] bd) {
    this.x = x;
    this.y = y;

    this.tiles = ts;
    this.buildings = bs;
    this.buildingData = bd;
  }

  public Tile getTile(int x, int y) {
    if (x >= 32 || y >= 32 || x < 0 || y < 0) {
      return null;
    }
    return tiles[x+y*32];
  }

  public void setTile(int x, int y, Tile tile) {
    this.tiles[x+y*32] = tile;
  }

  public Building getBuilding(int x, int y) {
    return buildings[x+y*32];
  }

  public void setBuilding(int x, int y, Building b) {
    this.buildings[x+y*32] = b;
    if (b == null) {
      setBuildingData(x, y, (byte) 0);
    }
  }

  public void setBuildingData(int x, int y, int data) {
    this.buildingData[x+y*32] = data;
  }

  public int getBuildingData(int x, int y) {
    return buildingData[x+y*32];
  }


  public void update(float timeDiff) {

  }
}
