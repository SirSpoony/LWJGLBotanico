package me.spoony.botanico.client.graphics.gui;

import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by coltenwebb on 11/9/16.
 */
public class BuildingDamageIndicator {

  public GamePosition getRenderPosition() {
    return new GamePosition(tilePosition.x + 1f / 8f, tilePosition.y + 1f / 8f);
  }

  public TilePosition tilePosition;

  public float health;
  public float maxHealth;

  public BuildingDamageIndicator(TilePosition tilePosition, float maxhealth) {
    this.tilePosition = tilePosition;
    this.maxHealth = maxhealth;
    this.health = maxhealth;
  }

  public void render(RendererGame rg) {
    IntRectangle source = new IntRectangle(0,
        (int) (27 - Math.floor((health / maxHealth) * 10) * 3), 12, 3);
    rg.sprite(getRenderPosition(), rg.getResourceManager().getTexture("damage_indicator.png"),
        source, tilePosition.y-10);
  }
}