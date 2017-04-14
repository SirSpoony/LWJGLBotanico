package me.spoony.botanico.test;

import libnoiseforjava.NoiseGen;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.Perlin;
import libnoiseforjava.module.RidgedMulti;
import libnoiseforjava.util.NoiseMap;
import libnoiseforjava.util.NoiseMapBuilder;
import libnoiseforjava.util.NoiseMapBuilderPlane;
import me.spoony.botanico.common.util.BMath;
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
        Layer layer = new LayerBase(11);
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

    public static void write(String filename, int chunkx, int chunky) throws ExceptionInvalidParam {
        int size = 32;


        BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        RidgedMulti ridgedMulti = new RidgedMulti();
        ridgedMulti.setOctaveCount(1);
        ridgedMulti.setFrequency(1);

        // create Noisemap object
        NoiseMap heightMap = new NoiseMap(size, size);

        // create Builder object
        NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
        heightMapBuilder.setSourceModule(ridgedMulti);
        heightMapBuilder.setDestNoiseMap(heightMap);
        heightMapBuilder.setDestSize(size, size);
        heightMapBuilder.setBounds(chunkx * 3, chunkx * 3 + 3, chunky * 3, chunky * 3 + 3);
        heightMapBuilder.build();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                //double val = NoiseGen.ValueCoherentNoise3D(x/10d, y/10d, 0.5d, 1, NoiseGen.NoiseQuality.QUALITY_BEST);
                double val = heightMap.getValue(x, y);
                val = BMath.clamp(val / 2 + .5d, 0, 1);
                if (val > .3d) val = 1;
                else val = 0;
                //System.out.println((float) val);
                bi.setRGB(x, y, new Color((float) val, (float) val, (float) val).getRGB());
            }
        }

        try {
            ImageIO.write(bi, "PNG", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("test ");
    }
}
