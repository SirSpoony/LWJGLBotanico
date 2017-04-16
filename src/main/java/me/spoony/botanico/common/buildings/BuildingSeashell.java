package me.spoony.botanico.common.buildings;

/**
 * Created by Colten on 4/15/2017.
 */
public class BuildingSeashell extends Building {

  public BuildingSeashell(int id) {
    super(id);
    this.name = "seashell";
    this.shouldCollide = false;
    this.alwaysBehindCharacter = true;
    this.textureName = "building/seashell.png";

    this.hardness = 1;
  }

  @Override
  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.ROCK;
  }
}
