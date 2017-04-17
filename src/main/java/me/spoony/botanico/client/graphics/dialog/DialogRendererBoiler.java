package me.spoony.botanico.client.graphics.dialog;

import me.spoony.botanico.client.ClientEntityPlayer;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.client.graphics.gui.RendererItemSlot;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.dialog.DialogBoiler;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.GuiRectangle;

/**
 * Created by Colten on 1/1/2017.
 */
public class DialogRendererBoiler extends DialogRendererAdapter<DialogBoiler> {
    private static String TEXTURE_LOCATION = "dialog/boiler.png";

    protected IntRectangle dialogTextureSource;

    @Override
    public void init(DialogBoiler dialog) {
        super.init(dialog);
        ClientEntityPlayer player = GameView.getClient().getLocalPlayer();

        this.dialogTextureSource = new IntRectangle(0, 0, 232, 108);
        this.dialogBounds.set(new GuiRectangle(0, 0, dialogTextureSource.width, dialogTextureSource.height));

        this.initPlayerItemSlots(player.inventory, 6, 6);

        rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(0), 176, 15));
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

        // render fluid
        rendererGUI.sprite(offsetByDialogBounds(new GuiPosition(134, 16)),
                rendererGUI.getResourceManager().getTexture(TEXTURE_LOCATION), new IntRectangle(75,128,16,(int)(64f*this.dialog.waterProgress)));

        // render fuel progress
        rendererGUI.sprite(offsetByDialogBounds(new GuiPosition(156, 42)),
                rendererGUI.getResourceManager().getTexture(TEXTURE_LOCATION), new IntRectangle(0, 150, (int)(58f*this.dialog.burnProgress), 8));

        // render production stats
        rendererGUI.text(offsetByDialogBounds(new GuiPosition(184, 70)), "Production:",
                new TextColors(new Color(.33f, .33f, .33f, 1)), CallAlign.BOTTOM_CENTER);

        rendererGUI.text(offsetByDialogBounds(new GuiPosition(184, 70-10)), (float) Math.round(this.dialog.energyProduction*100)/100f+" J/s",
                new TextColors(new Color(.33f, .33f, .33f, 1)), CallAlign.BOTTOM_CENTER);

        this.renderItemSlots(rendererGUI);

        rendererGUI.text(offsetByDialogBounds(new GuiPosition(dialogTextureSource.width / 2, dialogTextureSource.height - 12)), "Boiler",
                new TextColors(new Color(.33f, .33f, .33f, 1)), CallAlign.BOTTOM_CENTER);
    }
}
