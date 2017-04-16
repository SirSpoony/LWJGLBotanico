package me.spoony.botanico.common.items;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;

/**
 * Created by Colten on 11/8/2016.
 */
public class ItemStack {

  protected Item item;
  protected ItemSlot owner;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    if (count < 0) {
      count = 0;
    }
    this.count = count;
    onChange();
  }

  protected int count;

  public Item getItem() {
    return count <= 0 ? null : item;
  }

  public void setItem(Item item) {
    Preconditions.checkNotNull(item, "Item in ItemStack cannot be set to null");
    this.item = item;
    onChange();
  }

  public ItemStack(Item item) {
    setItem(item);
    setCount(1);
  }

  public ItemStack(Item item, int count) {
    setItem(item);
    setCount(count);
  }

  public void increaseCount(int x) {
    count += x;
    if (count <= 0) {
      count = 0;
    }
    onChange();
  }

  public boolean isEmpty() {
    if (getItem() == null) {
      return true;
    }
    return Range.lessThan(0).contains(count);
  }

  public boolean showCount() {
    if (getCount() == 1) {
      return false;
    }
    return true;
  }

  public void set(ItemStack stack) {
    this.setItem(stack.item);
    this.setCount(stack.count);
    onChange();
  }

  public static boolean match(ItemStack a, ItemStack b) {
    if (a == null || b == null) {
      if (a == null && b == null) {
        return true;
      } else {
        return false;
      }
    }
    if (a.getItem() == b.getItem()) {
      return true;
    }
    return false;
  }

  public static ItemStack clone(ItemStack stack) {
    return stack == null ? null : new ItemStack(stack.getItem(), stack.getCount());
  }

  public static ItemStack split(ItemStack stack) {
    if (stack == null) {
      return null;
    }
    ItemStack ret = ItemStack.clone(stack);
    stack.setCount((int) Math.floor(stack.getCount() / 2f));
    ret.setCount((int) Math.ceil(ret.getCount() / 2f));
    return ret;
  }

  public void onChange() {
    if (owner == null) {
      return;
    }
    owner.onUpdate();
  }
}
