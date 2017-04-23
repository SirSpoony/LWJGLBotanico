package me.spoony.botanico.server.level.levelgen.biome;

import me.spoony.botanico.common.tiles.Tiles;

/**
 * Created by Colten on 11/26/2016.
 */
public class BiomeDeepLake extends Biome
{
    public BiomeDeepLake(int id) {
        super(id);

        this.setTile(Tiles.WATER);
    }
}
