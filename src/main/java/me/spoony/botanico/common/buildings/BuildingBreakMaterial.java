package me.spoony.botanico.common.buildings;

import me.spoony.botanico.common.util.IntRectangle;

import java.util.Random;

/**
 * Created by Colten on 12/10/2016.
 */
public enum BuildingBreakMaterial {
  DEFAULT(new IntRectangle(48, 48, 16, 16)),

  DIRT(new IntRectangle(0, 0, 16, 16)),
  PLANT(new IntRectangle(16, 0, 16, 16)),
  ROCK(new IntRectangle(32, 0, 16, 16)),
  WOOD(new IntRectangle(48, 0, 16, 16));

  private static final Random RAND = new Random();

  private final IntRectangle region;

  public IntRectangle getRegion() {
    return getRegion(3, 3);
  }

  public IntRectangle getRegion(int width, int height) {
    int maxx = region.width - width;
    int maxy = region.height - height;

    return new IntRectangle(RAND.nextInt(maxx - 1) + region.x, RAND.nextInt(maxy - 1) + region.y,
        width, height);
  }

  BuildingBreakMaterial(IntRectangle region) {
    this.region = region;
  }
}
