package me.spoony.botanico.client.graphics.renderers;

import com.google.common.collect.Lists;
import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.client.graphics.gui.GameRenderable;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.tiles.AdjacentTiles;
import me.spoony.botanico.common.tiles.Direction;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.TileRenderRule;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;

import java.util.List;

/**
 * Created by Colten on 12/17/2016.
 */
public class PlaneRenderer implements GameRenderable {

  public ClientPlane level;

  public PlaneRenderer(ClientPlane level) {
    this.level = level;
  }

  @Override
  public void render(RendererGame rg) {
    // FANCY RENDERING VOODOO, IT WILL BREAK
    // OK NVM THIS IS SOME RLLY SHITTY CODE HOLY SHIT

    if (level.getID() == IPlane.UNDERWORLD) {
      rg.nightCycle = 0;
    } else {
      rg.nightCycle = 1;
    }

    DoubleRectangle gameview = rg.gameViewport;

    int firstTileX = (int) Math.floor(gameview.x) - 4;
    int firstTileY = (int) Math.floor(gameview.y) - 4;
    int lastTileX = (int) Math.ceil(gameview.x + gameview.width) + 4;
    int lastTileY = (int) Math.ceil(gameview.y + gameview.height) + 4;
    int tileViewWidth = lastTileX - firstTileX;
    int tileViewHeight = lastTileY - firstTileY;

    Texture tileTexture = rg.getResourceManager().getTexture("tiles.png");

    Tile[][] tiles = new Tile[tileViewWidth][tileViewHeight];
    TilePosition pos = new TilePosition();
    for (int x = firstTileX; x < lastTileX; x++) {
      for (int y = firstTileY; y < lastTileY; y++) {
        tiles[x - firstTileX][y - firstTileY] = level.getTile(pos.set(x, y));
      }
    }

    for (int x = firstTileX + 2; x < lastTileX - 2; x++) {
      for (int y = firstTileY + 2; y < lastTileY - 2; y++) {
        Tile tile = tiles[x - firstTileX][y - firstTileY];

        if (tile == null) {
          continue;
        }

        AdjacentTiles adjtiles = new AdjacentTiles();

        adjtiles.setTile(Direction.DOWN,       tiles[x - firstTileX]    [y - firstTileY - 1]);
        adjtiles.setTile(Direction.DOWN_RIGHT, tiles[x - firstTileX + 1][y - firstTileY - 1]);
        adjtiles.setTile(Direction.RIGHT,      tiles[x - firstTileX + 1][y - firstTileY]);
        adjtiles.setTile(Direction.UP_RIGHT,   tiles[x - firstTileX + 1][y - firstTileY + 1]);
        adjtiles.setTile(Direction.UP,         tiles[x - firstTileX]    [y - firstTileY + 1]);
        adjtiles.setTile(Direction.UP_LEFT,    tiles[x - firstTileX - 1][y - firstTileY + 1]);
        adjtiles.setTile(Direction.LEFT,       tiles[x - firstTileX - 1][y - firstTileY]);
        adjtiles.setTile(Direction.DOWN_LEFT,  tiles[x - firstTileX - 1][y - firstTileY - 1]);

        final int randtexture = Tile.perm[Math.abs(5 * x + y) & 255];
        final TileRenderRule relevantRule = tile.renderRules.findRenderRule(adjtiles);

        IntRectangle foreground = tile.foregroundRegion(adjtiles, relevantRule, randtexture);
        IntRectangle background = tile.backgroundRegion(adjtiles, relevantRule, randtexture);



        if (background != null) {
          rg.sprite(new GamePosition(x, y), tileTexture,
              background, RendererGame.BACK_LAYER);
        }

        if (foreground != null) {
          rg.sprite(new GamePosition(x, y), tileTexture,
              foreground, RendererGame.BACK_LAYER);
        }
      }
    }

    TilePosition highlightedBuilding = Input.CURSOR_POS.toGamePosition().toTilePosition();
    if (!level.getLocalPlayer().canReach(highlightedBuilding.toGamePosition())) {
      highlightedBuilding.set(Long.MAX_VALUE, Long.MAX_VALUE);
    }

    for (int x = firstTileX; x < lastTileX; x++) {
      for (int y = firstTileY; y < lastTileY; y++) {
        TilePosition currentPosition = new TilePosition(x, y);

        Building b = level.getBuilding(currentPosition);
        byte d = level.getBuildingData(currentPosition);

        boolean shouldHighlight =
            highlightedBuilding.equals(currentPosition) && !GameView.getPlayer().hasDialogOpen();
        if (b != null) {
          b.render(rg, level, currentPosition, d, shouldHighlight);
        }
      }
    }

    List<Entity> temptlist = Lists.newArrayList(level.getEntities()); // due to thread safety bugs
    for (Entity e : temptlist) {
      e.render(rg);
    }
  }
}
