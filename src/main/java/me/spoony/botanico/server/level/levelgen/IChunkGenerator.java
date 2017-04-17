package me.spoony.botanico.server.level.levelgen;

import me.spoony.botanico.common.level.Chunk;

/**
 * Created by Colten on 1/1/2017.
 */
public interface IChunkGenerator {
    Chunk generateChunk(long x, long y);
    long getSeed();
}
