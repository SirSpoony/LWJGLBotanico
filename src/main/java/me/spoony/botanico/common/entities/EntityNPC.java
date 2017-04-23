package me.spoony.botanico.common.entities;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 4/23/2017.
 */
public class EntityNPC extends Entity {

  public EntityNPC(IPlane plane) {
    super(plane);
  }

  @Override
  public void render(RendererGame rg) {
    Texture texture = rg.getResourceManager().getTexture("foreign_character_sheet.png");
    rg.sprite(this.posX, this.posY, texture, new IntRectangle(0, 0, 16, 32), this.posY);
  }


}
