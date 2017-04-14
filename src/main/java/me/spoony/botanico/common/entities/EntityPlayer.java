package me.spoony.botanico.common.entities;

import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class EntityPlayer extends Entity {
    public static final int ID = 235;

    public static final int SLOT_CURSOR = 48;

    public static final int SLOT_RING1 = 42;
    public static final int SLOT_RING2 = 43;

    public static final int SLOT_SWORD = 44;
    public static final int SLOT_HOE = 45;
    public static final int SLOT_AXE = 46;
    public static final int SLOT_PICKAXE = 47;

    public static final int INVENTORY_SIZE = 49;

    public EntityPlayer(IPlane plane) {
        super(plane);
        this.typeID = EntityPlayer.ID;
        this.position = new GamePosition(0, 0);
        this.collider = new DoubleRectangle(.1f, 0, .8f, .4f);
    }

    @Override
    public void render(RendererGame rg) {
        Texture texture = rg.getResourceManager().getTexture("foreign_character_sheet.png");
        if (animation == 1)
            rg.sprite(position, texture, new IntRectangle(0, 32, 16, 32), position.y);
        else if (animation == 0)
            rg.sprite(position, texture, new IntRectangle(0, 96, 16, 32), position.y);
        else if (animation == 2)
            rg.sprite(position, texture, new IntRectangle(0, 0, 16, 32), position.y);
        else if (animation == 3)
            rg.sprite(position, texture, new IntRectangle(0, 64, 16, 32), position.y);
    }
}
