package me.spoony.botanico.common.buildings;

import me.spoony.botanico.client.BotanicoGame;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.common.util.position.GamePosition;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.client.graphics.RendererGame;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.tiles.Tiles;
import me.spoony.botanico.common.util.DoubleRectangle;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.client.ClientPlane;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 11/8/2016.
 */
public class Building {

  public static final BuildingRegistry REGISTRY = new BuildingRegistry();

  protected String name;
  protected boolean shouldCollide;
  protected DoubleRectangle collisionBounds;
  protected IntRectangle textureBounds;
  protected boolean alwaysBehindCharacter;
  protected float hardness;

  public int getID() {
    return id;
  }

  public void setID(int id) {
    this.id = id;
  }

  protected int id;

  public static void initRegistry() {
    REGISTRY.registerBuilding(new BuildingWorkbench(0));

    REGISTRY.registerBuilding(new BuildingFlower(1, "flower_orange", 0));
    REGISTRY.registerBuilding(new BuildingFlower(2, "flower_red", 1));

    REGISTRY.registerBuilding(new BuildingGrass(3));
    REGISTRY.registerBuilding(new BuildingDirtMound(4));
    REGISTRY.registerBuilding(new BuildingRocks(5));
    REGISTRY.registerBuilding(new BuildingSticksPile(6));

    REGISTRY.registerBuilding(new BuildingBoulder(7));
    REGISTRY.registerBuilding(new BuildingTree(8));
    REGISTRY.registerBuilding(new BuildingTreeCold(9));

    REGISTRY.registerBuilding(new BuildingToolStation(10));

    REGISTRY.registerBuilding(new BuildingKnappingStation(11));

    REGISTRY.registerBuilding(new BuildingWheat(12));

    REGISTRY.registerBuilding(new BuildingJar(13));

    REGISTRY.registerBuilding(new BuildingMysticFlower(14));
    REGISTRY.registerBuilding(new BuildingEnergyPipe(15));

    REGISTRY.registerBuilding(new BuildingCave(16));

    REGISTRY.registerBuilding(new BuildingFurnace(17));
    REGISTRY.registerBuilding(new BuildingBoiler(18));

    REGISTRY.registerBuilding(new BuildingFluidPipe(19));

    REGISTRY.registerBuilding(new BuildingHemp(20));
    REGISTRY.registerBuilding(new BuildingCaveRope(21));

    REGISTRY.registerBuilding(new BuildingCopperOre(22));
    REGISTRY.registerBuilding(new BuildingCoalOre(23));

    REGISTRY.registerBuilding(new BuildingBush(24));
    REGISTRY.registerBuilding(new BuildingReeds(25));
    REGISTRY.registerBuilding(new BuildingSeashell(26));

    REGISTRY.registerBuilding(new BuildingWall(27));
  }

  public Building(int id) {
    name = "unnamed";
    shouldCollide = true;
    collisionBounds = new DoubleRectangle(0, 0, 1, 1);
    alwaysBehindCharacter = false;
    hardness = .5f;
    this.id = id;
    this.textureBounds = new IntRectangle(0, 0, 16, 16);
  }

  public String getLocalizedName() {
    return "[" + name + "]";
  }

  public void render(RendererGame rg, ClientPlane level, TilePosition position, byte extra,
      Color color) {
    rg.sprite(new GamePosition(position), getTextureSheet(),
        textureBounds, color, position.y + (alwaysBehindCharacter ? 1 : 0));
  }

  protected Texture getTextureSheet() {
    return BotanicoGame.getResourceManager().getTexture("buildings.png");
  }

  public void create(IPlane level, TilePosition position) {
    // TODO
  }

  public boolean canCreate(IPlane level, TilePosition position) {
    if (level.getBuilding(position) != null) {
      return false;
    }
    if (level.getTile(position) == Tiles.WATER) {
      return false;
    }
    if (level.getTile(position) == Tiles.DEEP_WATER) {
      return false;
    }
    return true;
  }

  public void destroy(IPlane level, TilePosition position) {

  }

  public boolean onClick(IPlane level, EntityPlayer player, TilePosition position) {
    return false;
  }

  public ItemStack[] getDrops(IPlane level, TilePosition position) {
    return getDrops();
  }

  public float getHardness(IPlane level, TilePosition position) {
    return getHardness();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean shouldCollide() {
    return shouldCollide;
  }

  public void setShouldCollide(boolean shouldCollide) {
    this.shouldCollide = shouldCollide;
  }

  public DoubleRectangle getCollisionBounds() {
    return collisionBounds;
  }

  public void setCollisionBounds(DoubleRectangle collisionBounds) {
    this.collisionBounds = collisionBounds;
  }

  public boolean isAlwaysBehindCharacter() {
    return alwaysBehindCharacter;
  }

  public void setAlwaysBehindCharacter(boolean alwaysBehindCharacter) {
    this.alwaysBehindCharacter = alwaysBehindCharacter;
  }

  public float getHardness() {
    return hardness;
  }

  public void setHardness(float hardness) {
    this.hardness = hardness;
  }

  public ItemStack[] getDrops() {
    return null;
  }

  public BuildingBreakMaterial getBreakParticle() {
    return BuildingBreakMaterial.DEFAULT;
  }

  public void setTextureBounds(int x, int y, int width, int height) {
    this.textureBounds.x = x;
    this.textureBounds.y = y;
    this.textureBounds.width = width;
    this.textureBounds.height = height;
  }
}
