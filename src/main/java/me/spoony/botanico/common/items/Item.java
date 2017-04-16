package me.spoony.botanico.common.items;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.buildings.Buildings;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.level.IPlane;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.TilePosition;

/**
 * Created by Colten on 11/10/2016.
 */
public class Item {

  public static ItemRegistry REGISTRY;

  public int id;
  public IntRectangle textureBounds;
  public String name;
  public int maxStackSize;

  public static void initRegistry() {
    REGISTRY = new ItemRegistry();

    REGISTRY.register(new ItemBuilding(129, itemBounds(3), "workbench", Buildings.WORKBENCH, 5));
    REGISTRY
        .register(new ItemBuilding(130, itemBounds(3), "tool_station", Buildings.TOOL_STATION, 5));
    REGISTRY.register(
        new ItemBuilding(131, itemBounds(7), "knapping_station", Buildings.KNAPPING_STATION, 5));

    REGISTRY.register(new Item(0, itemBounds(2), "dirt", 50));
    REGISTRY.register(new Item(1, itemBounds(4), "wood", 50) {
      @Override
      public float getBurnTime() {
        return .25f;
      }
    });
    REGISTRY.register(new Item(2, itemBounds(5), "rock", 50));

    REGISTRY.register(new Item(3, itemBounds(8), "rock_sword_blade", 50));
    REGISTRY.register(new Item(4, itemBounds(9), "rock_hoe_head", 50));
    REGISTRY.register(new Item(5, itemBounds(10), "rock_pickaxe_head", 50));
    REGISTRY.register(new Item(6, itemBounds(11), "rock_axe_head", 50));

    REGISTRY.register(new ItemTool(7, itemBounds(12), "rock_sword", ItemToolType.BLADE, .5f));
    REGISTRY.register(new ItemTool(8, itemBounds(13), "rock_hoe", ItemToolType.HOE, .5f));
    REGISTRY.register(new ItemTool(9, itemBounds(14), "rock_pickaxe", ItemToolType.PICKAXE, .5f));
    REGISTRY.register(new ItemTool(10, itemBounds(15), "rock_axe", ItemToolType.AXE, .5f));

    REGISTRY.register(new ItemBuilding(11, itemBounds(16), "wheat_seeds", Buildings.WHEAT, 50));

    REGISTRY.register(new ItemBuilding(13, itemBounds(18), "jar", Buildings.JAR, 50));

    REGISTRY.register(
        new ItemBuilding(14, itemBounds(19), "mystic_flower", Buildings.MYSTIC_FLOWER, 50));
    REGISTRY
        .register(new ItemBuilding(15, itemBounds(20), "energy_pipe", Buildings.ENERGY_PIPE, 50));

    REGISTRY.register(new ItemBucket(16, itemBounds(21), "bucket", 1));
    REGISTRY.register(new Item(17, itemBounds(22), "water_bucket", 1));

    REGISTRY.register(new ItemBuilding(18, itemBounds(23), "furnace", Buildings.FURNACE, 5));
    REGISTRY.register(new ItemBuilding(20, itemBounds(26), "boiler", Buildings.BOILER, 5));

    REGISTRY.register(new Item(12, itemBounds(24), "wheat", 50));
    REGISTRY.register(new Item(19, itemBounds(25), "charcoal", 50) {
      @Override
      public float getBurnTime() {
        return 1f;
      }
    });

    REGISTRY.register(new ItemBuilding(21, itemBounds(27), "fluid_pipe", Buildings.FLUID_PIPE, 50));

    REGISTRY.register(new ItemBuilding(22, itemBounds(28), "hemp_seeds", Buildings.HEMP, 50));
    REGISTRY.register(new Item(23, itemBounds(29), "hemp_fiber", 50));
    REGISTRY.register(new Item(24, itemBounds(30), "rope", 50));

    REGISTRY.register(new Item(25, itemBounds(31), "copper_ore", 50));

    REGISTRY.register(new ItemBuilding(26, itemBounds(32), "wall", Buildings.WALL, 50));
  }

  public String getName() {
    return name;
  }

  public String getLocalizedName() {
    //return Config.getLocalization().get("item:"+getName(), "["+getName()+"]"); todo fix localizations
    return "[" + getName() + "]";
  }

  public Item(int id, IntRectangle textureBounds, String name, int maxStackSize) {
    this.id = id;
    this.textureBounds = textureBounds;
    this.name = name;
    this.maxStackSize = maxStackSize;
  }

  public void onUse(IPlane level, EntityPlayer player, ItemSlot cursor, TilePosition position) {

  }

  private static IntRectangle itemBounds(int pos) {
    return new IntRectangle((pos % 32) * 16, (pos / 32) * 16, 16, 16); // TODO wrapping
  }

  public int getID() {
    return id;
  }

  public float getBurnTime() {
    return 0;
  }

  public float getBuildingDamageModifier(Building b) {
    return 1;
  }

  @Override
  public String toString() {
    return "{Item: " + name + "}";
  }
}
