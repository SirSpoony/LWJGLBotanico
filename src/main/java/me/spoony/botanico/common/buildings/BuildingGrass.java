package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.items.Item;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.items.Items;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.client.ClientPlane;

import java.util.Random;

/**
 * Created by coltenwebb on 11/9/16.
 */
public class BuildingGrass extends Building {
    private static final Random DROP_RAND = new Random();

    public BuildingGrass(int id) {
        super(id);
        this.name = "grass";
        this.textureName = "building/grass.png";
        shouldCollide = false;
        this.alwaysBehindCharacter = true;

        this.hardness = .1f;
    }

    @Override
    public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d, boolean highlight) {
        int val = hash(position.x, position.y) % 4;
        rg.sprite(position.toGamePosition(new GamePosition()), rg.getResourceManager().getTexture(textureName),
                new IntRectangle(16 * val, 0, 16, 16),
                highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE, position.y + (alwaysBehindCharacter ? 1 : 0));
    }

    private long OffsetBasis = 216613626;
    private long FnvPrime = 16777619;

    public int hash(long a, long b) {
        return Math.abs((int) ((((OffsetBasis ^ a) * FnvPrime) ^ b) * FnvPrime));
    }

    @Override
    public ItemStack[] getDrops(IPlane level, TilePosition position) {
        int count = DROP_RAND.nextInt(8) - 6;
        Item item = DROP_RAND.nextBoolean() ? Items.HEMP_SEEDS : Items.WHEAT_SEEDS;
        System.out.println(item);
        return count > 0 ? new ItemStack[]
                {
                        new ItemStack(item, count)
                } : null;
    }

    @Override
    public BuildingBreakMaterial getBreakParticle() {
        return BuildingBreakMaterial.PLANT;
    }
}
