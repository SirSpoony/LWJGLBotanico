package me.spoony.botanico.common.items;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

import java.util.*;

/**
 * Created by coltenwebb on 11/9/16.
 */
public class Inventory
{
    protected Set<ItemSlot> slots;

    public Inventory(int size, int dialogID)
    {
        slots = Sets.newConcurrentHashSet();
        for (int i = 0; i < size; i++)
        {
            slots.add(new ItemSlot(this, i, dialogID));
        }
    }

    public int getLength()
    {
        return slots.size();
    }

    public ItemStack addItem(ItemStack stack)
    {
        return addItem(stack, Range.all(), false);
    }

    public ItemStack addItem(ItemStack stack, Range slotRange)
    {
        return addItem(stack, slotRange, false);
    }

    private ItemStack addItem(ItemStack stack, Range slotRange, boolean allowEmpty)
    {
        if (stack == null) return null;
        for (int i = 0;i<slots.size();i++)
        {
            ItemSlot slot = getSlot(i);
            if (!slotRange.contains(slot.getSlotIndex())) continue;
            if (!allowEmpty && getStack(slot.getSlotIndex()) == null) continue;
            int stackcount = stack.count;
            ItemStackExchange ise = new ItemStackExchange(getStack(slot.getSlotIndex()), stack);
            if (ise.mergeIntoOneStack())
            {
                slot.setStack(ise.to);
                return ise.from;
            } else if (ise.merge() && ise.from.count != stackcount)
            {
                ItemStack currentstack = addItem(ise.from, slotRange, false);
                slot.setStack(ise.to);
                return currentstack;
            }
        }
        if (allowEmpty) {
            return stack;
        } else {
            return addItem(stack, slotRange, true);
        }
    }

    public int setStack(int slot, ItemStack itemstack)
    {
        getSlot(slot).setStack(itemstack);
        return slot;
    }

    public ItemStack getStack(int slot)
    {
        if (slot < 0 || slot > slots.size()) return null;
        return getSlot(slot).getStack();
    }

    public ItemSlot getSlot(int slot)
    {
        Preconditions.checkArgument(!(slot < 0 || slot > slots.size()), "Slot out of bounds [" + slot + "] out of range (0," + slots.size() + ")");
        for (ItemSlot itemslot:slots) {
            if (itemslot.getSlotIndex() == slot) return itemslot;
        }
        return null;
    }

    public int[] getSlotsToUpdate() {
        List<Integer> slots = Lists.newArrayList();
        for (int i = 0;i<getLength();i++) {
            ItemSlot slot = getSlot(i);
            if (slot.needsSync) {
                slots.add(i);
            }
        }
        return Ints.toArray(slots);
    }
}
