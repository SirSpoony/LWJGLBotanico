package me.spoony.botanico.common.tiles;

import com.google.common.collect.Lists;
import me.spoony.botanico.client.ResourceManager;

import java.util.List;
import java.util.Random;

/**
 * Created by Colten on 12/3/2016.
 */
public enum FootStepMaterial
{
    GRASS (genFilePaths("footstep/grass/grass", 6)),
    WATER (genFilePaths("footstep/water/water_through", 11)),
    SAND (genFilePaths("footstep/sand/sand_run", 11)),
    WOOD (new String[] {"footstep/wood/wood1.ogg"});

    String[] filepaths;
    FootStepMaterial(String[] filepaths) {
        this.filepaths = filepaths;
    }

    private static String[] genFilePaths(String start, int count) {
        List<String> paths = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            paths.add(start+(i+1)+".ogg");
        }
        return paths.toArray(new String[paths.size()]);
    }

/*    public Sound getRandomSound(ResourceManager manager) {
        return manager.getSound(filepaths[new Random().nextInt(filepaths.length)]); TODO audio
    }*/
}
