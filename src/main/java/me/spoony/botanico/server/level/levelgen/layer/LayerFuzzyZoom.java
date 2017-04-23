package me.spoony.botanico.server.level.levelgen.layer;

/**
 * Created by Colten on 4/23/2017.
 *
 * Generally generates less land the LayerZoom
 */
public class LayerFuzzyZoom extends LayerZoom {

  public LayerFuzzyZoom(Layer child) {
    super(child);
  }

  /**
   * Returns the most frequently occurring number of the set, or a random number from those provided
   */
  protected int selectModeOrRandom(int a, int b, int c, int d) {
    return this.selectRandom(a, b, c, d);
  }
}
