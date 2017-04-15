package me.spoony.botanico.common.buildings;

/**
 * Created by Colten on 4/15/2017.
 */
public class BuildingReeds extends Building {

  public BuildingReeds(int id) {
    super(id);

    name = "reeds";
    this.textureName = "building/reeds.png";

    shouldCollide = false;

    this.hardness = .1f;
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.PLANT;
  }
}
