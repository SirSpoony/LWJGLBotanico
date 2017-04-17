package me.spoony.botanico.client.graphics.dialog;

import me.spoony.botanico.client.ClientEntityPlayer;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.client.graphics.gui.RendererItemSlot;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.dialog.DialogFurnace;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/12/2016.
 */
public class DialogRendererFurnace extends DialogRendererAdapter<DialogFurnace> {
    private static String TEXTURE_LOCATION = "dialog/furnace.png";

    protected IntRectangle dialogTextureSource;

    @Override
    public void init(DialogFurnace dialog) {
        super.init(dialog);
        ClientEntityPlayer player = GameView.getClient().getLocalPlayer();

        this.dialogTextureSource = new IntRectangle(0, 0, 232, 108);
        this.dialogBounds.set(new GuiRectangle(0, 0, dialogTextureSource.width, dialogTextureSource.height));

        this.initPlayerItemSlots(player.inventory, 6, 6);

        rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(0), 142, 63));
        rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(1), 176, 15));

        rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(2), 210, 63));
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(RendererGUI rendererGUI) {
        centerDialogBounds(rendererGUI.guiViewport);

        if (!isOpen()) return;

        rendererGUI.sprite(getDialogPosition(),
                rendererGUI.getResourceManager().getTexture(TEXTURE_LOCATION), dialogTextureSource);

        // Render smelting progress
        rendererGUI.sprite(offsetByDialogBounds(new GuiPosition(163, 67)),
                rendererGUI.getResourceManager().getTexture(TEXTURE_LOCATION), new IntRectangle(0, 128, (int)(42f*this.dialog.progress), 8));

        // Render fuel progress
        rendererGUI.sprite(offsetByDialogBounds(new GuiPosition(156, 42)),
                rendererGUI.getResourceManager().getTexture(TEXTURE_LOCATION), new IntRectangle(0, 150, (int)(58f*this.dialog.burnProgress), 8));

        this.renderItemSlots(rendererGUI);

        rendererGUI.text(offsetByDialogBounds(new GuiPosition(dialogTextureSource.width / 2, dialogTextureSource.height - 12)), "Furnace",
                new TextColors(new Color(.33f, .33f, .33f, 1)), CallAlign.BOTTOM_CENTER);
    }
}
