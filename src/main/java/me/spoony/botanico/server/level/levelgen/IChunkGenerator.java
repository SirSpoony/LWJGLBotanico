package me.spoony.botanico.server.level.levelgen;

import me.spoony.botanico.common.level.Chunk;
import me.spoony.botanico.common.util.position.ChunkPosition;

/**
 * Created by Colten on 1/1/2017.
 */
public interface IChunkGenerator {
    Chunk generateChunk(ChunkPosition position);
    long getSeed();
}
