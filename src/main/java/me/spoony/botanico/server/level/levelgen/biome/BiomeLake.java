package me.spoony.botanico.server.level.levelgen.biome;

import me.spoony.botanico.common.tiles.Tiles;

/**
 * Created by Colten on 11/26/2016.
 */
public class BiomeLake extends Biome
{
    public BiomeLake(int id) {
        super(id);

        this.setTile(Tiles.WATER);
    }
}
