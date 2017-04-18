package me.spoony.botanico.client.graphics.renderers;

import com.google.common.collect.Lists;
import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.client.graphics.gui.GameRenderable;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.tiles.AdjacentTiles;
import me.spoony.botanico.common.tiles.Direction;
import me.spoony.botanico.common.tiles.Tile;
import me.spoony.botanico.common.tiles.TileRenderRule;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;

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

    DoubleRectangle gameViewport = rg.gameViewport;

    int firstTileX = (int) Math.floor(gameViewport.x) - 4;
    int firstTileY = (int) Math.floor(gameViewport.y) - 4;
    int lastTileX = (int) Math.ceil(gameViewport.x + gameViewport.width) + 4;
    int lastTileY = (int) Math.ceil(gameViewport.y + gameViewport.height) + 4;

    renderTiles(rg);

    OmniPosition highlightedBuilding = level.getLocalPlayer().getHighlightedBuildingPosition();

    OmniPosition currentPos = new OmniPosition(PositionType.GAME, 0, 0);
    for (int x = firstTileX; x < lastTileX; x++) {
      for (int y = firstTileY; y < lastTileY; y++) {
        Building b = level.getBuilding(x, y);
        byte d = level.getBuildingData(x, y);

        boolean shouldHighlight = false;
        if (highlightedBuilding != null) {
          shouldHighlight = x == highlightedBuilding.getTileX() &&
              y == highlightedBuilding.getTileY() &&
              !GameView.getPlayer().hasDialogOpen();
        }

        if (b != null) {
          currentPos.setGameX(x);
          currentPos.setGameY(y);
          b.render(rg, level, currentPos, d,
              shouldHighlight ? new Color(.8f, .8f, .8f, 1) : Color.WHITE);
        }
      }
    }

//    List<Entity> temptlist = Lists.newArrayList(level.getEntities()); // due to thread safety bugs
    for (Entity e : level.getEntities()) {
      e.render(rg);
    }
  }

  private void renderTiles(RendererGame rg) {
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
    for (int x = firstTileX; x < lastTileX; x++) {
      for (int y = firstTileY; y < lastTileY; y++) {
        tiles[x - firstTileX][y - firstTileY] = level.getTile(x, y);
      }
    }

    AdjacentTiles adjtiles = new AdjacentTiles();

    for (int x = firstTileX + 2; x < lastTileX - 2; x++) {
      for (int y = firstTileY + 2; y < lastTileY - 2; y++) {
        Tile tile = tiles[x - firstTileX][y - firstTileY];

        if (tile == null) {
          continue;
        }

        adjtiles.setTile(Direction.DOWN, tiles[x - firstTileX][y - firstTileY - 1]);
        adjtiles.setTile(Direction.DOWN_RIGHT, tiles[x - firstTileX + 1][y - firstTileY - 1]);
        adjtiles.setTile(Direction.RIGHT, tiles[x - firstTileX + 1][y - firstTileY]);
        adjtiles.setTile(Direction.UP_RIGHT, tiles[x - firstTileX + 1][y - firstTileY + 1]);
        adjtiles.setTile(Direction.UP, tiles[x - firstTileX][y - firstTileY + 1]);
        adjtiles.setTile(Direction.UP_LEFT, tiles[x - firstTileX - 1][y - firstTileY + 1]);
        adjtiles.setTile(Direction.LEFT, tiles[x - firstTileX - 1][y - firstTileY]);
        adjtiles.setTile(Direction.DOWN_LEFT, tiles[x - firstTileX - 1][y - firstTileY - 1]);

        final int randtexture = Tile.perm[Math.abs(5 * x + y) & 255];
        final TileRenderRule relevantRule = tile.renderRules.findRenderRule(adjtiles);

        IntRectangle foreground = tile.foregroundRegion(adjtiles, relevantRule, randtexture);
        IntRectangle background = tile.backgroundRegion(adjtiles, relevantRule, randtexture);

        if (background != null) {
          rg.sprite(x, y, 1, tileTexture,
              background, Color.WHITE, RendererGame.BACK_LAYER);
        }

        if (foreground != null) {
          rg.sprite(x, y, 1, tileTexture,
              foreground, Color.WHITE, RendererGame.BACK_LAYER);
        }
      }
    }
  }
}
