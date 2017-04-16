package me.spoony.botanico.common.net.client;

import com.google.common.collect.Range;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 11/24/2016.
 */
public class CPacketInventorySlotClick extends AutoPacketAdapter implements IServerHandler {

  public int slotpos;
  public int inventoryID;
  public byte clickType;

  @Override
  public void onReceive(BotanicoServer server, RemoteEntityPlayer player) {
    ItemSlot clickedSlot;
    ItemSlot cursor;

    if (inventoryID == Inventory.PLAYER_ID) {
      clickedSlot = player.inventory.getSlot(slotpos);
      cursor = player.inventory.getSlot(EntityPlayer.SLOT_CURSOR);

      if (clickType == ItemSlot.SHIFT_LEFT_CLICK || clickType == ItemSlot.SHIFT_RIGHT_CLICK) {
        //shiftclick logic
        if (clickedSlot.isEmpty()) {
          return;
        }

        if (slotpos >= EntityPlayer.HOTBAR_INVENTORY_MIN
            && slotpos <= EntityPlayer.HOTBAR_INVENTORY_MAX) {
          // if slot is hotbar

          ItemStack remains = player.inventory
              .addItem(clickedSlot.getStack(), Range.closed(EntityPlayer.HOTBAR_INVENTORY_MAX + 1,
                  EntityPlayer.NORMAL_INVENTORY_MAX));
          clickedSlot.setStack(remains);
        } else {
          // if slot is anything in the player inventory, except for the hotbar
          ItemStack leftover = player.inventory.addItem(clickedSlot.getStack(),
              Range.closed(EntityPlayer.HOTBAR_INVENTORY_MIN, EntityPlayer.HOTBAR_INVENTORY_MAX));
          clickedSlot.setStack(leftover);
        }
      } else {
        // normal click logic
        ItemSlot.exchange(clickedSlot, cursor, clickType);
      }
    } else if (player.currentDialog != null && inventoryID == player.currentDialog.id) {
      clickedSlot = player.currentDialog.inventory.getSlot(slotpos);
      cursor = player.inventory.getSlot(EntityPlayer.SLOT_CURSOR);

      if (clickType == ItemSlot.SHIFT_LEFT_CLICK || clickType == ItemSlot.SHIFT_RIGHT_CLICK) {
        // shiftclick logic
        if (clickedSlot.isEmpty()) {
          return;
        }

        ItemStack leftover = player.inventory.addItem(clickedSlot.getStack(),
            Range.closed(EntityPlayer.HOTBAR_INVENTORY_MIN, EntityPlayer.NORMAL_INVENTORY_MAX));
        clickedSlot.setStack(leftover);
      } else {
        // normal click logic
        ItemSlot.exchange(clickedSlot, cursor, clickType);
      }
    } else {
      System.err.println("[ServerPacketHandler] Received unknown Inventory Slot Click");
      return;
    }

    if (player.currentDialog != null && inventoryID == player.currentDialog.id) {
      player.currentDialog.onItemSlotInteraction(slotpos, clickType);
    }
  }
}
