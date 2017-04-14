package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 11/9/2016.
 */
public class BuildingBoulder extends Building
{
    public BuildingBoulder(int id)
    {
        super(id);

        name = "boulder";
        this.textureName = "building/boulder.png";

        this.hardness = 10;
    }

    @Override
    public void destroy(IPlane level, TilePosition position)
    {
        if (level.isLocal()) return;
        ServerPlane serverLevel = (ServerPlane)level;
        serverLevel.setBuilding(position, Buildings.ROCKS);
    }

    @Override
    public BuildingBreakMaterial getBreakParticle()
    {
        return BuildingBreakMaterial.ROCK;
    }
}
