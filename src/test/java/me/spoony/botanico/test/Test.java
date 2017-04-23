package me.spoony.botanico.test;

import libnoiseforjava.exception.ExceptionInvalidParam;
import me.spoony.botanico.server.level.levelgen.layer.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Colten on 1/2/2017.
 */
public class Test {

  public static void main(String[] args) throws ExceptionInvalidParam {
    writeChunk(0, 0, 512, "chunk_0_0");
    writeChunk(512, 0, 512, "chunk_1_0");
  }

  public static void writeChunk(int chunkx, int chunky, int size, String name) {
    Layer layer = Layer.getDefaultLayers();
    int[] ints = layer.getInts(chunkx, chunky, size, size);

    BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    for (int x = 0; x < size; x++) {
      for (int y = 0; y < size; y++) {
        float r, g, b;
        r = g = b = 0;

        if ((ints[x + y * size]) == 1) {
          r = g = b = .5f;
        }

        if ((ints[x + y * size]) == 3) {
          r = g = b = 1f;
        }

        bi.setRGB(x, y, new Color(r, g, b).getRGB());
      }
    }

    try {
      ImageIO.write(bi, "PNG", new File(name + ".png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
