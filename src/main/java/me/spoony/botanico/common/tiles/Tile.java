package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class Tile {

  public static TileRegistry REGISTRY;
  public static int[] perm =
      {
          151, 160, 137, 91, 90, 15,
          131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21,
          10, 23,
          190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57,
          177, 33,
          88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27,
          166,
          77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245,
          40, 244,
          102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200,
          196,
          135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124,
          123,
          5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189,
          28, 42,
          223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172,
          9,
          129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97,
          228,
          251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239,
          107,
          49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150,
          254,
          138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
      };
  public TileRenderRules renderRules;
  protected int id;
  protected String name;
  private boolean shouldCollide;
  protected FootStepMaterial footStepMaterial;

  public Tile(int id, String name) {
    this.id = id;
    this.name = name;
    this.shouldCollide = false;
    this.renderRules = new TileRenderRules(new IntRectangle[]{new IntRectangle(0, 0, 16, 16)});
    this.footStepMaterial = FootStepMaterial.GRASS;
  }

  public static void initRegistry() {
    REGISTRY = new TileRegistry();

    REGISTRY.register(new TileGround(0, "ground"));
    REGISTRY.register(new TileGroundLight(1, "ground_light"));
    REGISTRY.register(new TileSand(2, "sand"));
    REGISTRY.register(new TileWater(3, "water"));
    REGISTRY.register(new TileDeepWater(4, "deep_water"));
    REGISTRY.register(new TileFertilizedGround(5, "fertilized_ground"));
    REGISTRY.register(new TileCaveFloor(6, "cave_floor"));
    REGISTRY.register(new TileCaveWall(7, "cave_wall"));

    for (Tile t : REGISTRY.getRegisteredTiles()) {
      t.loadRenderRules();
    }
  }

  public FootStepMaterial getFootStepMaterial() {
    return footStepMaterial;
  }

  public int getID() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean shouldCollide() {
    return shouldCollide;
  }

  public void setShouldCollide(boolean shouldCollide) {
    this.shouldCollide = shouldCollide;
  }

  public IntRectangle foregroundRegion(AdjacentTiles adjtiles,
      TileRenderRule relevantRule, int randtexture) {

    if (relevantRule != null) {
      return relevantRule.getForeground(randtexture);
    } else {
      return renderRules.getDefaultRegions()[randtexture % renderRules.getDefaultRegions().length];
    }
  }

  public IntRectangle backgroundRegion(AdjacentTiles adjtiles,
      TileRenderRule relevantRule, int randtexture) {
    if (relevantRule != null) {
      return relevantRule.getBackground();
    } else {
      return null;
    }
  }

  public void loadRenderRules() {

  }
}
