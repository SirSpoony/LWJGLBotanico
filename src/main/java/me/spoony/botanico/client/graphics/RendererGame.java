package me.spoony.botanico.client.graphics;

import com.google.common.collect.Lists;
import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.client.ResourceManager;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.SpriteBatch;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.particle.BuildingBreakParticles;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;
import me.spoony.botanico.common.buildings.BuildingBreakMaterial;
import me.spoony.botanico.common.util.BMath;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.DoubleVector2D;
import me.spoony.botanico.common.util.IntRectangle;

import java.util.*;

/**
 * Created by Colten on 11/8/2016.
 */
public class RendererGame {

  private final SpriteBatch batch;

  public DoubleRectangle gameViewport; // stored relative to game coordinates

  List<R> orderedRenderables;

  public static final double BACK_LAYER = Double.MAX_VALUE;
  public static final double FRONT_LAYER = -Double.MAX_VALUE;

  BuildingBreakParticles effect;

  private int scale = 1;

  public float nightCycle = 1; // 0 is midnight, 1 is daytime
  public boolean tint;

  public double windowXToGame(double x) {
    return gameViewport.x + x / (16f * scale);
  }

  public double windowYToGame(double y) {
    return gameViewport.y + y / (16f * scale);
  }

  public double gameXToWindow(double x) {
    return (-gameViewport.x * (16f * scale) + x * (16f * scale));
  }

  public double gameYToWindow(double y) {
    return (-gameViewport.y * (16f * scale) + y * (16f * scale));
  }

  public RendererGame() {
    this.gameViewport = new DoubleRectangle(0, 0, 0, 0);

    batch = new SpriteBatch(8191);

    orderedRenderables = Lists.newArrayList();

    effect = new BuildingBreakParticles();

    updateGameViewport();
  }

  public void unload() {
    //batch.dispose(); TODO: Fix dispose logic
  }

  public ResourceManager getResourceManager() {
    return BotanicoGame.getResourceManager();
  }

  public void begin() {
    updateGameViewport();

    batch.ambientColor.set(new Color(
        BMath.lerp(.5f, 1, nightCycle),
        BMath.lerp(.5f, 1, nightCycle),
        BMath.lerp(.8f, 1, nightCycle), 1));

    if (tint) {
      batch.ambientColor.r *= .6f;
      batch.ambientColor.g *= .6f;
      batch.ambientColor.b *= .6f;
    }

    batch.begin();
  }

  public void end() {
    effect.step((float) BotanicoGame.DELTA);
    effect.render(this);

    Collections.sort(orderedRenderables, (o1, o2) ->
    {
      if (o1.layer == o2.layer) {
        return 0;
      }
      return o1.layer > o2.layer ? -1 : 1;
    });

    for (R r : orderedRenderables) {
      batch
          .sprite(r.x, r.y, r.width, r.height, r.color, r.sx, r.sy, r.swidth, r.sheight, r.texture);
    }

    orderedRenderables.clear();

    batch.end();
  }

  private void updateGameViewport() {
    int windowWidth = BotanicoGame.WINDOW.getWidth();
    int windowHeight = BotanicoGame.WINDOW.getHeight();

    // Find scale
    if (windowWidth / windowHeight < 1) {
      scale = BMath.clamp((int) Math.floor(windowWidth / 300) + 1, 2, 4); // width less than height
    } else {
      scale = BMath.clamp((int) Math.floor(windowHeight / 200) + 1, 2, 4); // height less than width
    }

    // Find Width and Height based on Scale and Window Size
    gameViewport.width = windowWidth / (16f * scale);
    gameViewport.height = windowHeight / (16f * scale);

    batch.viewwidth = windowWidth;
    batch.viewheight = windowHeight;
    // We set the position in centerOn()
  }

  public void centerOn(DoubleVector2D center, float deltalerp) {
    updateGameViewport();
    //deltalerp = 1;

    DoubleRectangle targetViewport = new DoubleRectangle(gameViewport);
    targetViewport.setCenter(center);

    double xdif = gameViewport.x - targetViewport.x;
    double ydif = gameViewport.y - targetViewport.y;

    if (deltalerp > 0 && deltalerp < 1 && Math.sqrt(xdif * xdif + ydif * ydif) > .1d * deltalerp) {
      gameViewport.setX(BMath.lerp(gameViewport.x, targetViewport.x, deltalerp));
      gameViewport.setY(BMath.lerp(gameViewport.y, targetViewport.y, deltalerp));
    } else {
      gameViewport.set(targetViewport);
    }
  }

  /**
   * Renders a sprite
   *
   * @param pos GAME position
   * @param texture texture
   * @param source source of the texture to be rendered (relative to top left)
   * @param layer order to be rendered, lower is closer
   */
  public void sprite(OmniPosition pos, Texture texture, IntRectangle source, double layer) {
    sprite(pos, texture, source, Color.WHITE, layer);
  }

  public void sprite(OmniPosition pos, Texture texture, IntRectangle source, Color color,
      double layer) {
    orderedRenderables.add(new R(layer,
        (float) pos.getX(PositionType.WINDOW), (float) pos.getY(PositionType.WINDOW),
        source.getWidth() * this.scale, source.getHeight() * this.scale,
        color,
        source.getX(), source.getY(), source.getWidth(), source.getHeight(),
        texture));
  }

  public void sprite(OmniPosition pos, float scale, Texture texture, IntRectangle source,
      Color color, double layer) {
    orderedRenderables.add(new R(layer,
        (float) pos.getX(PositionType.WINDOW), (float) pos.getY(PositionType.WINDOW),
        source.getWidth() * this.scale * scale,
        source.getHeight() * this.scale * scale,
        color,
        source.getX(), source.getY(), source.getWidth(), source.getHeight(),
        texture));
  }

  public void particleBuildingBreak(OmniPosition position, BuildingBreakMaterial particles) {
    effect.start(position, particles);
  }

  private class R {

    final double layer;
    final float x;
    final float y;
    final float width;
    final float height;
    final Color color;
    final int sx;
    final int sy;
    final int swidth;
    final int sheight;
    final Texture texture;

    public R(double layer, float x, float y, float width, float height, Color color, int sx, int sy,
        int swidth,
        int sheight, Texture texture) {
      this.layer = layer;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.color = color;
      this.sx = sx;
      this.sy = sy;
      this.swidth = swidth;
      this.sheight = sheight;
      this.texture = texture;
    }
  }
}

