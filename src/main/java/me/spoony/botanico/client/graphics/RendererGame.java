package me.spoony.botanico.client.graphics;

import com.google.common.collect.Lists;
import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.client.ResourceManager;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.SpriteBatch;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.particle.BuildingBreakParticles;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.common.util.position.WindowPosition;
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

    List<OrderedRenderable> orderedRenderables;

    public static final double BACK_LAYER = Double.MAX_VALUE;
    public static final double FRONT_LAYER = -Double.MAX_VALUE;

    BuildingBreakParticles effect;

    private int scale = 1;

    public float nightCycle = 1; // 0 is midnight, 1 is daytime
    public boolean tint;

    public GamePosition windowPosToGamePos(WindowPosition windowpos, GamePosition gamepos) {
        gamepos.x = gameViewport.x + windowpos.x / (16f * scale);
        gamepos.y = gameViewport.y + windowpos.y / (16f * scale);
        return gamepos;
    }

    public WindowPosition gamePosToWindowPos(GamePosition gamepos, WindowPosition windowpos) {
        windowpos.x = (float) (-gameViewport.x * (16f * scale) + gamepos.x * (16f * scale));
        windowpos.y = (float) (-gameViewport.y * (16f * scale) + gamepos.y * (16f * scale));
        return windowpos;
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
            batch.ambientColor.r *=.6f;
            batch.ambientColor.g *=.6f;
            batch.ambientColor.b *=.6f;
        }

        batch.begin();
    }

    public void end() {
        effect.step((float)BotanicoGame.DELTA);
        effect.render(this);

        Collections.sort(orderedRenderables, (o1, o2) ->
        {
            if (o1.order == o2.order) return 0;
            return o1.order > o2.order ? -1 : 1;
        });

        for (OrderedRenderable r : orderedRenderables) {
            r.render(batch);
        }

        orderedRenderables.clear();

        batch.end();
    }

    private void updateGameViewport() {
        int windowWidth = BotanicoGame.WINDOW.getWidth();
        int windowHeight = BotanicoGame.WINDOW.getHeight();

        // Find scale
        if (windowWidth / windowHeight < 1)
            scale = BMath.clamp((int) Math.floor(windowWidth / 300) + 1, 2, 4); // width less than height
        else
            scale = BMath.clamp((int) Math.floor(windowHeight / 200) + 1, 2, 4); // height less than width

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

        double xdif = gameViewport.x-targetViewport.x;
        double ydif = gameViewport.y-targetViewport.y;

        if (deltalerp > 0 && deltalerp < 1 && Math.sqrt(xdif*xdif+ydif*ydif) > .1d*deltalerp) {
            gameViewport.setX(BMath.lerp(gameViewport.x, targetViewport.x, deltalerp));
            gameViewport.setY(BMath.lerp(gameViewport.y, targetViewport.y, deltalerp));
        } else {
            gameViewport.set(targetViewport);
        }
    }

    /**
     * Renders a sprite
     *
     * @param pos     GAME position
     * @param texture texture
     * @param source  source of the texture to be rendered (relative to top left)
     * @param layer   order to be rendered, lower is closer
     */
    public void sprite(GamePosition pos, Texture texture, IntRectangle source, double layer) {
        sprite(pos, texture, source, Color.WHITE, layer);
    }

    public void sprite(GamePosition pos, Texture texture, IntRectangle source, Color color, double layer) {
        WindowPosition windowPos = new WindowPosition(pos);

        orderedRenderables.add(OrderedRenderable.create(layer, batch1 -> batch1.sprite(
                windowPos.x, windowPos.y, source.getWidth() * this.scale, source.getHeight() * this.scale,
                color,
                source.getX(), source.getY(), source.getWidth(), source.getHeight(),
                texture)));
    }

    public void sprite(GamePosition pos, float scale, Texture texture, IntRectangle source, Color color, double layer) {
        WindowPosition windowPos = new WindowPosition(pos);

        orderedRenderables.add(OrderedRenderable.create(layer, batch1 -> batch1.sprite(
                windowPos.x, windowPos.y, source.getWidth() * this.scale * scale, source.getHeight() * this.scale * scale,
                color,
                source.getX(), source.getY(), source.getWidth(), source.getHeight(),
                texture)));
    }

    public void particleBuildingBreak(TilePosition position, BuildingBreakMaterial particles) {
        effect.start(position, particles);
    }
}
