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
    writeChunk();
  }

  public static void writeChunk() {
    Layer layer = new LayerZeros(11);
    layer = new LayerLand(layer);
    layer = new LayerZoom(layer, true);
    layer = new LayerZoom(layer, true);
    layer = new LayerBiome(layer);
    layer = new LayerZoom(layer, true);
    layer = new LayerZoom(layer, true);
    layer = new LayerZoom(layer, true);
    layer = new LayerZoom(layer, true);
    layer = new LayerZoom(layer, true);
    layer = new LayerSmooth(layer);

    int[] ints = layer.getInts(0, 0, 1024, 1024);

    BufferedImage bi = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB);
    for (int x = 0; x < 1024; x++) {
      for (int y = 0; y < 1024; y++) {
        float r, g, b;
        r = g = b = 0;

        if ((ints[x + y * 1024] & 1) == 1) {
          r = g = b = .5f;
        }

        bi.setRGB(x, y, new Color(r, g, b).getRGB());
      }
    }

    try {
      ImageIO.write(bi, "PNG", new File("chunk_test0.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
