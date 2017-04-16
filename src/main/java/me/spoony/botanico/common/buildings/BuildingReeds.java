package me.spoony.botanico.common.buildings;

/**
 * Created by Colten on 4/15/2017.
 */
public class BuildingReeds extends Building {

  public BuildingReeds(int id) {
    super(id);

    name = "reeds";
    this.setTextureBounds(32, 176, 16, 32);

    shouldCollide = false;

    this.hardness = .1f;
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }
}
