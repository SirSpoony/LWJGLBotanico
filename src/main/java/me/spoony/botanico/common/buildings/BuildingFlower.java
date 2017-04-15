package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 11/10/2016.
 */
public class BuildingFlower extends Building {

  int subid;

  public BuildingFlower(int id, String name, int subid) {
    super(id);
    this.name = name;
    shouldCollide = false;

    this.alwaysBehindCharacter = false;
    this.textureName = "building/flower.png";
    this.hardness = .1f;

    this.subid = subid;
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d,
      boolean highlight) {
    rg.sprite(new GamePosition(position), rg.getResourceManager().getTexture(textureName),
        new IntRectangle(subid * 16, 0, 16, 16),
        highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE,
        position.y + (alwaysBehindCharacter ? 1 : 0));
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }
}
