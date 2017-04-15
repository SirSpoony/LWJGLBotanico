package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 1/2/2017.
 */
public class BuildingCaveRope extends Building {

  public BuildingCaveRope(int id) {
    super(id);
    this.name = "cave_rope";
    this.textureName = "building/cave_rope.png";
    this.hardness = Float.MAX_VALUE;
    this.alwaysBehindCharacter = true;
    this.shouldCollide = false;
  }

  @Override
  public void render(RendererGame rg, ClientPlane level, TilePosition position, byte d,
      boolean highlight) {
    rg.sprite(position.toGamePosition(), rg.getResourceManager().getTexture(textureName),
        new IntRectangle(0, 0, 16, 64), highlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE,
        position.y);
  }

  @Override
  public boolean onClick(IPlane plane, EntityPlayer player, TilePosition position) {
    if (plane.isLocal()) {
      return true;
    }
    if (player == null) {
      return true;
    }

    ((RemoteEntityPlayer) player).teleport(position.toGamePosition().add(0, 2.5f),
        ((ServerPlane)plane).getLevel().getOverworld());

    return true;
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.ROCK;
  }
}