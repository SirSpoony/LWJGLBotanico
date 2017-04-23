package me.spoony.botanico.common.entities;

import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by Colten on 11/8/2016.
 */
public class EntityPlayer extends Entity {

  public static final int ID = 235;

  public static final int HOTBAR_INVENTORY_MIN = 0;
  public static final int HOTBAR_INVENTORY_MAX = 7;

  public static final int NORMAL_INVENTORY_MIN = 0;
  public static final int NORMAL_INVENTORY_MAX = 47;

  public static final int SLOT_CURSOR = 50;

  public static final int SLOT_RING1 = 48;
  public static final int SLOT_RING2 = 49;

  public static final int INVENTORY_SIZE = 51;

  public EntityPlayer(IPlane plane) {
    super(plane);
    this.typeID = EntityPlayer.ID;
    this.collider = new DoubleRectangle(.1f, 0, .8f, .4f);
  }

  @Override
  public void render(RendererGame rg) {
    Texture texture = rg.getResourceManager().getTexture("foreign_character_sheet.png");
    if (animation == 1) {
      rg.sprite(posX, posY, texture, new IntRectangle(0, 32, 16, 32), posY);
    } else if (animation == 0) {
      rg.sprite(posX, posY, texture, new IntRectangle(0, 96, 16, 32), posY);
    } else if (animation == 2) {
      rg.sprite(posX, posY, texture, new IntRectangle(0, 0, 16, 32), posY);
    } else if (animation == 3) {
      rg.sprite(posX, posY, texture, new IntRectangle(0, 64, 16, 32), posY);
    }
  }
}
