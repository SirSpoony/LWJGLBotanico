package me.spoony.botanico.common.items;

import me.spoony.botanico.common.entities.EntityPlayer;

/**
 * Created by Colten on 11/10/2016.
 */
public class ItemSlot
{
    protected Inventory inventory;
    private int slot;
    private ItemStack stack;
    private ItemStack ghost;
    private int dialogID;
    private ItemSlotMode mode;
    private ItemSlotRestriction restriction;
    public boolean needsSync;

    public ItemSlot(Inventory inventory, int slot, int dialogID) {
        this.inventory = inventory;
        this.slot = slot;
        this.dialogID = dialogID;
        this.mode = ItemSlotMode.NORMAL;
        this.restriction = ItemSlotRestriction.DEFAULT;
    }

    public static void exchange(ItemSlot invslot, ItemSlot cursorslot, byte clicktype) {
        ItemStack invslotstack = ItemStack.clone(invslot.getStack());
        ItemStack cursorslotstack = ItemStack.clone(cursorslot.getStack());

        if (cursorslotstack != null && !invslot.getRestriction().isValid(cursorslotstack)) return;

        if (invslot.getMode() == ItemSlotMode.NORMAL) {
            ItemStackExchange exchange = new ItemStackExchange(invslotstack, cursorslotstack);
            if (clicktype == 0 || clicktype == 2) {
                exchange.exchange();
            } else if (clicktype == 1 || clicktype == 3) {
                exchange.exchangeHalf();
            }
            invslot.setStack(exchange.to);
            cursorslot.setStack(exchange.from);
        } else if (invslot.getMode() == ItemSlotMode.TAKE_ONLY) {
            ItemStackExchange exchange = new ItemStackExchange(cursorslotstack, invslotstack);
            exchange.merge();
            cursorslot.setStack(exchange.to);
            invslot.setStack(exchange.from);
        }
    }

    public ItemSlotMode getMode()
    {
        return mode;
    }

    public void setMode(ItemSlotMode mode)
    {
        this.mode = mode;
    }

    public ItemStack getStack() {
        clean();
        return stack;
    }

    public void setStack(ItemStack stack)
    {
        this.stack = stack;
        if (inventory != null) {
            needsSync = true;
        }
    }

    public ItemStack getGhost() {
        if (ghost == null) return null;
        if (ghost.isEmpty()) ghost = null;
        return ghost;
    }

    public void setGhost(ItemStack stack)
    {
        this.ghost = stack;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public int getSlotIndex()
    {
        return slot;
    }

    public int getDialogID()
    {
        return dialogID;
    }

    public ItemSlotRestriction getRestriction()
    {
        return restriction;
    }

    public void setRestriction(ItemSlotRestriction restriction)
    {
        this.restriction = restriction;
    }

    public boolean isEmpty()
    {
        clean();
        return stack == null;
    }

    public void clean() {
        if (stack == null) return;
        if (stack.isEmpty()) stack = null;
    }
}
