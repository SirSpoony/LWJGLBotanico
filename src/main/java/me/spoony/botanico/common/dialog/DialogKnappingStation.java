package me.spoony.botanico.common.dialog;

import com.google.common.collect.Range;
import me.spoony.botanico.common.items.*;
import me.spoony.botanico.common.net.IPacketInterpreter;
import me.spoony.botanico.common.net.PacketDecoder;
import me.spoony.botanico.common.net.PacketEncoder;

/**
 * Created by Colten on 11/27/2016.
 */
public class DialogKnappingStation extends Dialog implements IPacketInterpreter {
    public static final byte BLADE = 0;
    public static final byte HOE = 1;
    public static final byte PICKAXE = 2;
    public static final byte AXE = 3;

    public byte mode;

    public DialogKnappingStation() {
        super(Dialog.KNAPPING_STATION_ID);
        this.inventory = new Inventory(2, Dialog.KNAPPING_STATION_ID);
        this.inventory.getSlot(1).setMode(ItemSlotMode.TAKE_ONLY);
    }

    public ItemStack queryCraft() {
        ItemSlot ingredientslot = inventory.getSlot(0);
        if (ingredientslot.getStack() == null) return null;

        if (ingredientslot.getStack().getItem() == Items.ROCK) {
            switch (mode) {
                case BLADE:
                    return new ItemStack(Items.ROCK_SWORD_BLADE);
                case HOE:
                    return new ItemStack(Items.ROCK_HOE_HEAD);
                case PICKAXE:
                    return new ItemStack(Items.ROCK_PICKAXE_HEAD);
                case AXE:
                    return new ItemStack(Items.ROCK_AXE_HEAD);
            }
        }

        return null;
    }

    public boolean canCraft() {
        if (queryCraft() != null) {
            ItemStackExchange exchange = new ItemStackExchange(inventory.getStack(1), queryCraft());
            if (exchange.mergeIntoOneStack()) {
                return true;
            }
        }
        return false;
    }

    public void craft() {
        if (!canCraft()) return;

        ItemStack ingredient = inventory.getStack(0);
        ItemStack product = queryCraft();

        ingredient.increaseCount(-1);

        inventory.setStack(0, ingredient);

        ItemStackExchange ise = new ItemStackExchange(inventory.getStack(1), product);
        ise.mergeIntoOneStack();

        inventory.setStack(1, ise.to);
    }

    @Override
    public void onButtonPressed(int button) {
        super.onButtonPressed(button);
        if (button == 0 && canCraft()) {
            this.craft();
            viewers.updateDialogAll();
        } else if (Range.closed(1, 5).contains(button)) {
            this.setMode((byte) (button - 1));
            viewers.updateDialogAll();
        }
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    @Override
    public void encodeToPacket(PacketEncoder encoder) {
        encoder.writeByte(mode);
    }

    @Override
    public void decodeFromPacket(PacketDecoder decoder) {
        mode = decoder.readByte();
    }
}
