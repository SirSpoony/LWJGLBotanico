package me.spoony.botanico.test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Created by Colten on 12/31/2016.
 */
public class TestMaps {

  public static void main(String[] args) {
    try {
      // retrieve image
      BufferedImage bi = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
      int[] gen =
          gen16
              (gen8(
                  gen4()
              ));

      for (int x = 0; x < 16; x++) {
        for (int y = 0; y < 16; y++) {
          int current = gen[x * 16 + y];
          Color ret = new Color(0, 0, 0);
          if (current == 1) {
            ret = new Color(1f, 0f, 0f);
          }

          bi.setRGB(x, y, ret.getRGB());
        }
      }

      File outputfile = new File("saved.png");
      ImageIO.write(bi, "png", outputfile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Random rand = new Random(4);

  public static int[] gen4() {
    int[] ret = new int[4 * 4];

    for (int i = 0; i < ret.length; i++) {
      ret[i] = rand.nextBoolean() ? 1 : 0;
    }

    return ret;
  }

  public static int[] gen8(int[] prev) {
    int[] ret = new int[8 * 8];

    for (int x = 0; x < 8; x++) {
      for (int y = 0; y < 8; y++) {
        int xoff = rand.nextBoolean() && x < 7 ? 1 : 0;
        int yoff = rand.nextBoolean() && y < 7 ? 1 : 0;
        ret[(x) * 8 + (y)] = prev[(x + xoff) / 2 * 4 + (y + yoff) / 2];
      }
    }

    return ret;
  }

  public static int[] gen16(int[] prev) {
    int[] ret = new int[16 * 16];

    for (int x = 0; x < 16; x++) {
      for (int y = 0; y < 16; y++) {
        int xoff = rand.nextBoolean() && x < 15 ? 1 : 0;
        int yoff = rand.nextBoolean() && y < 15 ? 1 : 0;
        ret[(x) * 16 + (y)] = prev[(x + xoff) / 2 * 8 + (y + yoff) / 2];
      }
    }

    return ret;
  }
}
