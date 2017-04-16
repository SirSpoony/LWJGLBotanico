package me.spoony.botanico.common.dialog;

import me.spoony.botanico.ServerOnly;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by Colten on 11/24/2016.
 */
public class Dialog {

  public static int PLAYER_INV_ID = 2;
  public static int PLAYER_INV_CRAFTING_ID = 3;
  public static int TOOL_STATION_ID = 4;
  public static int KNAPPING_STATION_ID = 5;
  public static int FURNACE_ID = 6;
  public static int BOILER_ID = 7;

  public int id;
  public Inventory inventory;
  public DialogViewerManager viewers;

  public Dialog(int id) {
    this.id = id;
    this.viewers = new DialogViewerManager(this);
  }

  public static Dialog fromID(int id) {
    if (id == 3) {
      return new DialogInventoryPlayer();
    }
    if (id == 4) {
      return new DialogToolStation();
    }
    if (id == 5) {
      return new DialogKnappingStation();
    }
    if (id == 6) {
      return new DialogFurnace();
    }
    if (id == 7) {
      return new DialogBoiler();
    }
    return null;
  }

  public void onButtonPressed(int button) {

  }

  @ServerOnly
  public void onOpen(RemoteEntityPlayer player) {
    viewers.addViewer(player);
    inventory.addViewer(player);
    onAddViewer(player);
  }

  @ServerOnly
  public void onClose(RemoteEntityPlayer player) {
    viewers.removeViewer(player);
    inventory.removeViewer(player);
    onRemoveViewer(player);
  }

  @ServerOnly
  public void onAddViewer(RemoteEntityPlayer player) {

  }

  @ServerOnly
  public void onRemoveViewer(RemoteEntityPlayer player) {

  }

  public void onItemSlotInteraction(int slot, byte type) {

  }
}
