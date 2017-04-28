package me.spoony.botanico.server.level.levelgen.biome;

import com.google.common.collect.Lists;
import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.server.level.levelgen.buildingfeature.IBuildingFeature;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Colten on 11/26/2016.
 */
public class Biome {
  public int id;

  public ArrayList<IBuildingFeature> buildingFeatures;
  public Tile tile;

  public Biome(int id) {
    this.id = id;

    this.buildingFeatures = Lists.newArrayList();
    this.tile = Tiles.GROUND;
  }

  public void addBuildingFeature(IBuildingFeature bf) {
    buildingFeatures.add(bf);
  }

  public void setTile(Tile tile) {
    this.tile = tile;
  }
}
