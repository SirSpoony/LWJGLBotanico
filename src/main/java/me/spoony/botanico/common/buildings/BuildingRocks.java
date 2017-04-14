package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.TilePosition;

import java.util.Random;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingRocks extends Building
{
    public BuildingRocks(int id)
    {
        super(id);
        this.name = "rocks";
        this.shouldCollide = false;
        this.alwaysBehindCharacter = true;
        this.textureName = "building/rocks.png";

        this.hardness = 1;
    }

    @Override
    public ItemStack[] getDrops(IPlane level, TilePosition position)
    {
        return new ItemStack[]
                {
                        new ItemStack(Items.ROCK, new Random().nextInt(2) + 1),
                };
    }

    @Override
    public BuildingBreakMaterial getBreakParticle()
    {
        return BuildingBreakMaterial.ROCK;
    }
}
