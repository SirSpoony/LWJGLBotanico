package me.spoony.botanico.client.graphics.dialog;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.client.graphics.gui.RendererItemSlot;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.dialog.DialogInventoryPlayer;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlotMode;
import me.spoony.botanico.common.items.ItemStackExchange;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/10/2016.
 */
public class DialogRendererInventoryPlayer extends DialogRendererAdapter<DialogInventoryPlayer> {
    public boolean showTrinket;

    protected Inventory craftingInventory;
    protected IntRectangle dialogTextureSource;

    @Override
    public void init(DialogInventoryPlayer dialog) {
        super.init(dialog);

        this.showTrinket = false;
        dialogTextureSource = new IntRectangle(0, 0, 130, 130);
        this.dialogBounds.set(new GuiRectangle(0, 0, dialogTextureSource.width, dialogTextureSource.height));

        Inventory inv = GameView.getClient().getLocalPlayer().inventory;
        this.initPlayerItemSlots(inv, 6, 28);

        // RING SLOTS
        RendererItemSlot ring1 = new RendererItemSlot(inv.getSlot(EntityPlayer.SLOT_RING1), 6, 6);
        RendererItemSlot ring2 = new RendererItemSlot(inv.getSlot(EntityPlayer.SLOT_RING2), 23, 6);

        rendererItemSlots.add(ring1);
        rendererItemSlots.add(ring2);

        // CRAFTING INVENTORY
        craftingInventory = dialog.inventory;
        craftingInventory.getSlot(2).setMode(ItemSlotMode.TAKE_ONLY);

        rendererItemSlots.add(new RendererItemSlot(craftingInventory.getSlot(0), 57, 6));
        rendererItemSlots.add(new RendererItemSlot(craftingInventory.getSlot(1), 74, 6));
        rendererItemSlots.add(new RendererItemSlot(craftingInventory.getSlot(2), 108, 6));
    }

    @Override
    public void onBinaryInputPressed(BinaryInput bin) {
        super.onBinaryInputPressed(bin);

        if (bin == Input.BUTTON_LEFT && craftingButtonContainsCursor() && dialog.canCraft()) {
            GameView.getClient().packetHandler.sendDialogButtonPress(0);
        }
    }

    protected boolean craftingButtonContainsCursor() {
        if (offsetByDialogBounds(new GuiRectangle(91, 6, 16, 16))
                .contains(Input.CURSOR_POS.toGuiPosition())) {
            return true;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        if (dialog.canCraft()) {
            ItemStackExchange ise = new ItemStackExchange(craftingInventory.getStack(2), dialog.queryCraft().products[0]);
            if (ise.mergeIntoOneStack()) {
                craftingInventory.getSlot(2).setGhost(ise.to);
                return;
            }
        }
        craftingInventory.getSlot(2).setGhost(null);
    }

    @Override
    public void render(RendererGUI rg) {
        this.centerDialogBounds(rg.guiViewport);

        if (!isOpen()) return;

        rg.sprite(getDialogPosition(),
                rg.getResourceManager().getTexture("dialog/dialog_inventory.png"), dialogTextureSource);

        int buttontexture = dialog.canCraft() ? (craftingButtonContainsCursor() ? 1 : 0) : 2;

        rg.sprite(offsetByDialogBounds(new GuiPosition(91, 6)),
                rg.getResourceManager().getTexture("dialog/dialog_inventory.png"),
                new IntRectangle(16 * buttontexture, 144, 16, 16));


        this.renderItemSlots(rg);

        rg.text(offsetByDialogBounds(new GuiPosition(dialogTextureSource.width / 2, dialogTextureSource.height - 12)), "Inventory",
                new TextColors(new Color(1/3f, 1/3f, 1/3f, 1)), CallAlign.BOTTOM_CENTER);
    }
}
