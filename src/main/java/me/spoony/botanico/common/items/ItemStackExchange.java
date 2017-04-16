package me.spoony.botanico.common.items;

/**
 * Created by Colten on 11/29/2016.
 */
public class ItemStackExchange {

  private ItemStack slot;
  private ItemStack cursor;

  public ItemStack getTo() {
    return slot;
  }

  public ItemStack getFrom() {
    return cursor;
  }

  public ItemStackExchange(ItemStack slot, ItemStack cursor) {
    this.slot = ItemStack.clone(slot);
    this.cursor = ItemStack.clone(cursor);
  }

  public void exchangeHalf() {
    if (cursor == null) {
      cursor = ItemStack.split(slot);
    } else {
      if (ItemStack.match(slot, cursor) && slot.getCount() + 1 <= slot.getItem().maxStackSize) {
        slot.increaseCount(1);
        cursor.increaseCount(-1);
      } else if (slot == null) {
        slot = ItemStack.clone(cursor);
        slot.setCount(1);
        cursor.increaseCount(-1);
      } else {
        exchange();
      }
    }
  }

  public void exchange() {
    if (!merge()) {
      ItemStack a = ItemStack.clone(slot);
      slot = ItemStack.clone(cursor);
      cursor = ItemStack.clone(a);
    }
  }

  public boolean merge() {
    if (cursor == null && slot == null) {
      return false; // no merge can take place if there is nothing to take from or merge to
    }

    if (cursor == null) {
      return false; // no merge can take place if there is nothing to take from
    }

    if (slot == null) {
      slot = cursor;
      cursor = null;
      return true; // add the from to to is easy because both are null
    }

    if (!ItemStack.match(slot, cursor)) {
      return false; // if they don't match there is no way they can be merged
    }

    int maxcount = slot.getItem().maxStackSize;
    int tocount = slot.getCount();
    int fromcount = cursor.getCount();

    if (tocount + fromcount > maxcount) {
      slot.setCount(maxcount);
      cursor.setCount(fromcount - (maxcount - tocount));
      return true; // add the count of the from stack to the to stack with respect for maxstacksize
    }

    if (tocount + fromcount <= maxcount) {
      slot.setCount(tocount + fromcount);
      cursor = null;
      return true;// take all of from and add it to to
    }

    return false; // should never reach this code
  }

  public boolean mergeIntoOneStack() {
    ItemStack tempTo = ItemStack.clone(slot);
    ItemStack tempFrom = ItemStack.clone(cursor);

    if (merge() && cursor == null) {
      return true;
    }

    slot = tempTo;
    cursor = tempFrom;
    return false;
  }
}
