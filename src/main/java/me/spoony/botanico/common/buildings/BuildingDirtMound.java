package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.TilePosition;

import java.util.Random;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingDirtMound extends Building {
    public BuildingDirtMound(int id) {
        super(id);
        this.name = "dirt_mound";
        shouldCollide = false;

        this.textureName = "building/dirt_mound.png";
        this.alwaysBehindCharacter = true;

        this.hardness = 1;
    }

    @Override
    public ItemStack[] getDrops(IPlane level, TilePosition position) {
        return new ItemStack[]
                {
                        new ItemStack(Items.DIRT, new Random().nextInt(3) + 2),
                };
    }

    @Override
    public BuildingBreakMaterial getBreakParticle() {
        return BuildingBreakMaterial.DIRT;
    }
}
