package me.spoony.botanico.common.items;

import com.google.common.base.Preconditions;

/**
 * Created by Colten on 11/10/2016.
 */
public class ItemSlot {

  public static int LEFT_CLICK = 0;
  public static int RIGHT_CLICK = 1;
  public static int SHIFT_LEFT_CLICK = 2;
  public static int SHIFT_RIGHT_CLICK = 3;

  protected Inventory inventory;
  private int slot;
  private ItemStack stack;
  private ItemStack ghost;
  private ItemSlotMode mode;
  private ItemSlotRestriction restriction;

  public ItemSlot(Inventory inventory, int slot) {
    Preconditions.checkNotNull(inventory);

    this.inventory = inventory;
    this.slot = slot;
    this.mode = ItemSlotMode.NORMAL;
    this.restriction = ItemSlotRestriction.DEFAULT;
  }

  public static void exchange(ItemSlot invSlot, ItemSlot cursor, byte clickType) {
    ItemStack invSlotStack = ItemStack.clone(invSlot.getStack());
    ItemStack cursorStack = ItemStack.clone(cursor.getStack());

    if (cursorStack != null && !invSlot.getRestriction().isValid(cursorStack)) {
      return;
    }

    if (invSlot.getMode() == ItemSlotMode.NORMAL) {
      ItemStackExchange exchange = new ItemStackExchange(invSlotStack, cursorStack);
      if (clickType == ItemSlot.LEFT_CLICK || clickType == ItemSlot.SHIFT_LEFT_CLICK) {
        exchange.exchange();
      } else if (clickType == ItemSlot.RIGHT_CLICK || clickType == ItemSlot.SHIFT_RIGHT_CLICK) {
        exchange.exchangeHalf();
      }
      invSlot.setStack(exchange.getTo());
      cursor.setStack(exchange.getFrom());
    } else if (invSlot.getMode() == ItemSlotMode.TAKE_ONLY) {
      ItemStackExchange exchange = new ItemStackExchange(cursorStack, invSlotStack);
      exchange.merge();
      cursor.setStack(exchange.getTo());
      invSlot.setStack(exchange.getFrom());
    }
  }

  public ItemSlotMode getMode() {
    return mode;
  }

  public void setMode(ItemSlotMode mode) {
    this.mode = mode;
  }

  public ItemStack getStack() {
    clean();
    return stack;
  }

  public void setStack(ItemStack stack) {
    this.stack = stack;
    if (stack != null) {
      this.stack.owner = this;
    }
    onUpdate();
  }

  public void onUpdate() {
    inventory.onSlotUpdate(slot);
  }

  public ItemStack getGhost() {
    if (ghost == null) {
      return null;
    }
    if (ghost.isEmpty()) {
      ghost = null;
    }
    return ghost;
  }

  public void setGhost(ItemStack stack) {
    this.ghost = stack;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public int getSlotIndex() {
    return slot;
  }

  public ItemSlotRestriction getRestriction() {
    return restriction;
  }

  public void setRestriction(ItemSlotRestriction restriction) {
    this.restriction = restriction;
  }

  public boolean isEmpty() {
    clean();
    return stack == null;
  }

  public void clean() {
    if (stack == null) {
      return;
    }
    if (stack.isEmpty()) {
      stack = null;
    }
  }
}
