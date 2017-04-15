package me.spoony.botanico.common.tiles;

import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/11/2016.
 */
public class TileConnected extends Tile {

  public TileConnected(int id, String name) {
    super(id, name);
  }

  public void addSandRules(int x, int y, Tile connection, IntRectangle background) {
    //===============
    renderRules.addRule(
        new IntRectangle(x + 0, y + 0, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 16, y + 0, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.UP, TileMatcherType.EXCEPT, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 32, y + 0, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 48, y + 0, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.UP, TileMatcherType.EXCEPT, connection))
        .setBackground(background);
    //==========
    renderRules.addRule(
        new IntRectangle(x + 0, y + 16, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 16, y + 16, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.UP, TileMatcherType.EXCEPT, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 32, y + 16, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 48, y + 16, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.UP, TileMatcherType.EXCEPT, connection))
        .setBackground(background);
    //===============
    renderRules.addRule(
        new IntRectangle(x + 0, y + 32, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 16, y + 32, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.UP, TileMatcherType.EXCEPT, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 32, y + 32, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 48, y + 32, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.UP, TileMatcherType.EXCEPT, connection))
        .setBackground(background);
    //==========
    renderRules.addRule(
        new IntRectangle(x + 0, y + 48, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 16, y + 48, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.REQUIRE, connection),
        new TileMatcher(Direction.UP, TileMatcherType.EXCEPT, connection))
        .setBackground(background);

    renderRules.addRule(
        new IntRectangle(x + 32, y + 48, 16, 16),
        new TileMatcher(Direction.DOWN, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.RIGHT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.LEFT, TileMatcherType.EXCEPT, connection),
        new TileMatcher(Direction.UP, TileMatcherType.REQUIRE, connection))
        .setBackground(background);
  }

  public void addSandRules(int x, int y, Tile connection) {
    addSandRules(x, y, connection, null);
  }
}
