package me.spoony.botanico.common.items;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;

import java.util.*;
import me.spoony.botanico.ServerOnly;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.net.server.SPacketSlot;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by coltenwebb on 11/9/16.
 */
public class Inventory {

  public static int PLAYER_ID = Dialog.PLAYER_INV_ID;
  public static int PLAYER_CRAFTING_ID = Dialog.PLAYER_INV_CRAFTING_ID;
  public static int TOOL_STATION_ID = Dialog.TOOL_STATION_ID;
  public static int KNAPPING_STATION_ID = Dialog.KNAPPING_STATION_ID;
  public static int FURNACE_ID = Dialog.FURNACE_ID;
  public static int BOILER_ID = Dialog.BOILER_ID;

  public final int id;
  protected final Set<ItemSlot> slots;

  public Set<RemoteEntityPlayer> viewers;

  public Inventory(int size, int inventoryID) {
    slots = Sets.newConcurrentHashSet();
    for (int i = 0; i < size; i++) {
      slots.add(new ItemSlot(this, i));
    }
    this.id = inventoryID;
    this.viewers = Sets.newHashSet();
  }

  public int getLength() {
    return slots.size();
  }

  public ItemStack addItem(ItemStack stack) {
    return addItem(stack, Range.all(), false);
  }

  public ItemStack addItem(ItemStack stack, Range slotRange) {
    return addItem(stack, slotRange, false);
  }

  private ItemStack addItem(ItemStack stack, Range slotRange, boolean allowEmpty) {
    if (stack == null) {
      return null;
    }
    for (int i = 0; i < slots.size(); i++) {
      ItemSlot slot = getSlot(i);
      if (!slotRange.contains(slot.getSlotIndex())) {
        continue;
      }
      if (!allowEmpty && getStack(slot.getSlotIndex()) == null) {
        continue;
      }
      int stackcount = stack.count;
      ItemStackExchange ise = new ItemStackExchange(getStack(slot.getSlotIndex()), stack);
      if (ise.mergeIntoOneStack()) {
        slot.setStack(ise.getTo());
        return ise.getFrom();
      } else if (ise.merge() && ise.getFrom().count != stackcount) {
        ItemStack currentstack = addItem(ise.getFrom(), slotRange, false);
        slot.setStack(ise.getTo());
        return currentstack;
      }
    }
    if (allowEmpty) {
      return stack;
    } else {
      return addItem(stack, slotRange, true);
    }
  }

  public int setStack(int slot, ItemStack itemstack) {
    getSlot(slot).setStack(itemstack);
    return slot;
  }

  public ItemStack getStack(int slot) {
    if (slot < 0 || slot > slots.size()) {
      return null;
    }
    return getSlot(slot).getStack();
  }

  public ItemSlot getSlot(int slot) {
    Preconditions.checkArgument(!(slot < 0 || slot > slots.size()),
        "Slot out of bounds [" + slot + "] out of range (0," + slots.size() + ")");
    for (ItemSlot itemslot : slots) {
      if (itemslot.getSlotIndex() == slot) {
        return itemslot;
      }
    }
    return null;
  }

  @ServerOnly
  public void onSlotUpdate(int slot) {
    if (viewers.size() == 0) {
      return; // If 0, this is a client's inventory so there is no need to worry about slot updates (only the server can cause slot updates)
    }

    for (RemoteEntityPlayer viewer: viewers) {
      SPacketSlot pps = new SPacketSlot();
      pps.inventoryID = id;
      pps.slotpos = slot;
      pps.stack = getStack(slot);
      viewer.sendPacket(pps);
    }
  }

  public void addViewer(RemoteEntityPlayer player) {
    viewers.add(player);
    for (ItemSlot slot : slots) {
      SPacketSlot pps = new SPacketSlot();
      pps.inventoryID = id;
      pps.slotpos = slot.getSlotIndex();
      pps.stack = slot.getStack();
      player.sendPacket(pps);
    }
  }

  public void removeViewer(RemoteEntityPlayer player) {
    viewers.remove(player);
  }

  public void clearViewers() {
    viewers.clear();
  }
}
