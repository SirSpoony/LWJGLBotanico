package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.level.ServerPlane;

/**
 * Created by Colten on 1/2/2017.
 */
public class BuildingCaveRope extends Building {

  public BuildingCaveRope(int id) {
    super(id);
    this.name = "cave_rope";
    this.setTextureBounds(0, 32, 16, 64);
    this.hardness = Float.MAX_VALUE;
    this.alwaysBehindCharacter = true;
    this.shouldCollide = false;
  }

  @Override
  public boolean onClick(IPlane plane, EntityPlayer player, OmniPosition position) {
    if (plane.isLocal()) {
      return true;
    }
    if (player == null) {
      return true;
    }

    ((RemoteEntityPlayer) player).teleport(position.add(PositionType.GAME, 0, 2.5f),
        ((ServerPlane) plane).getLevel().getOverworld());

    return true;
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.ROCK;
  }
}