package me.spoony.botanico.server.level.levelgen.biome;

import com.google.common.collect.Sets;
import java.util.Set;

/**
 * Created by Colten on 4/23/2017.
 */
public class Biomes {
  public final static Set<Biome> BIOME_SET;

  public static Biome OCEAN = new BiomeLake(0);
  public static Biome PRAIRIE = new BiomePrairie(1);
  public static Biome BEACH = new BiomeBeach(3);

  static {
    BIOME_SET = Sets.newHashSet();

    BIOME_SET.add(OCEAN);
    BIOME_SET.add(PRAIRIE);
    BIOME_SET.add(BEACH);
  }
}
