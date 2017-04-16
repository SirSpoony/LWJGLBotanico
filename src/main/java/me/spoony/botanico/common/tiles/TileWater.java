package me.spoony.botanico.common.tiles;

import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/8/2016.
 */
public class TileWater extends TileConnected {

  public TileWater(int id, String name) {
    super(id, name);
    this.footStepMaterial = FootStepMaterial.WATER;
    setShouldCollide(true);
  }

  @Override
  public void loadRenderRules() {
    this.renderRules = new TileRenderRules(new IntRectangle[]{
        new IntRectangle(64, 96, 16, 16)});

    addSandRules(0, 160, Tile.REGISTRY.get("sand"));
  }

  @Override
  public IntRectangle foregroundRegion(AdjacentTiles adjtiles, TileRenderRule relevantRule,
      int randtexture) {
    if (relevantRule != null) {
      return relevantRule.getForeground(randtexture);
    }
    return null;
  }

  @Override
  public IntRectangle backgroundRegion(AdjacentTiles adjtiles, TileRenderRule relevantRule,
      int randtexture) {
    int val =(int) (BotanicoGame.getLastLoopTime()/300000000)&7;
    return new IntRectangle(64+val*16, 96,16, 16);
  }
}
