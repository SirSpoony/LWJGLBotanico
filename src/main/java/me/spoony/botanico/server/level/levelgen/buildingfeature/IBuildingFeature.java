package me.spoony.botanico.server.level.levelgen.buildingfeature;

import me.spoony.botanico.common.level.Chunk;

import java.util.Random;

/**
 * Created by Colten on 11/12/2016.
 */
public interface IBuildingFeature {

  void generate(long worldSeed, int chunkX, int chunkY, Chunk chunk);
}
