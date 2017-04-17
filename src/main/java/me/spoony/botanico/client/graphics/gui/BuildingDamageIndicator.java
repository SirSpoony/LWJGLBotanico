package me.spoony.botanico.client.graphics.gui;

import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by coltenwebb on 11/9/16.
 */
public class BuildingDamageIndicator {

  public OmniPosition getRenderPosition() {
    return new OmniPosition(PositionType.GAME, tilePosition.x + 1f / 8f, tilePosition.y + 1f / 8f);
  }

  public OmniPosition tilePosition;

  public float health;
  public float maxHealth;

  public BuildingDamageIndicator(OmniPosition tilePosition, float maxhealth) {
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