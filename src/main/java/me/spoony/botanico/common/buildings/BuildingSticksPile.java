package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;

import java.util.Random;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingSticksPile extends Building
{
    public BuildingSticksPile(int id)
    {
        super(id);
        this.name = "sticks_pile";
        shouldCollide = false;

        this.textureName = "building/sticks_pile.png";
        this.alwaysBehindCharacter = true;

        this.hardness = .5f;
    }

    @Override
    public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d, boolean highlight)
    {
        int val = hash(position.x, position.y) % 4;
        rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
                new IntRectangle(16*val, 0, 16, 16), highlight ? new Color(.8f,.8f,.8f,1) : Color.WHITE,
                position.y + (alwaysBehindCharacter ? 1 : 0));
    }

    private long OffsetBasis = 216613626;
    private long FnvPrime = 16777619;

    public int hash(long a, long b)
    {
        return Math.abs((int)((((OffsetBasis ^ a) * FnvPrime) ^ b) * FnvPrime));
    }

    @Override
    public ItemStack[] getDrops(IPlane level, TilePosition tilePosition)
    {
        return new ItemStack[]
                {
                        new ItemStack(Items.WOOD, new Random().nextInt(3) + 1),
                };
    }

    @Override
    public BuildingBreakMaterial getBreakParticle()
    {
        return BuildingBreakMaterial.WOOD;
    }
}
