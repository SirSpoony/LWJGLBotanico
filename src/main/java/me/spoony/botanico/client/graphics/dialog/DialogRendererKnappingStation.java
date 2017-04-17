package me.spoony.botanico.client.graphics.dialog;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import me.spoony.botanico.client.ClientEntityPlayer;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.client.graphics.gui.RendererItemSlot;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.dialog.DialogKnappingStation;
import me.spoony.botanico.common.util.IntRectangle;

import java.util.Set;

/**
 * Created by Colten on 11/27/2016.
 */
public class DialogRendererKnappingStation extends DialogRendererAdapter<DialogKnappingStation> {
    private static String TEXTURE_LOCATION = "dialog/dialog_knapping_station.png";

    protected IntRectangle dialogTextureSource;

    @Override
    public void init(DialogKnappingStation dialog) {
        Preconditions.checkArgument(dialog instanceof DialogKnappingStation, "Dialog isn't a knapping station, so it can't be rendered!");

        super.init(dialog);
        ClientEntityPlayer player = GameView.getPlayer();

        this.dialogTextureSource = new IntRectangle(0, 0, 232, 108);
        this.dialogBounds.set(new GuiRectangle(0, 0, dialogTextureSource.width, dialogTextureSource.height));

        this.initPlayerItemSlots(player.inventory, 6, 6);
        this.rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(0), 176, 40));
        this.rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(1), 210, 40));
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(RendererGUI rendererGUI) {
//        centerDialogBounds(rendererGUI.guiViewport);
//
//        if (!isOpen()) return;
//
//        Texture dialogTexture = rendererGUI.getResourceManager().getTexture(TEXTURE_LOCATION);
//
//        rendererGUI.sprite(getDialogPosition(),
//                dialogTexture, dialogTextureSource);
//
//        for (DialogButtonKnappingStation db : knappingModeButtons) {
//            db.updatePosition(this);
//            db.isselected = (db.id == dialog.mode);
//            db.render(rendererGUI);
//        }
//
////        System.out.println(((DialogKnappingStation) dialog).mode);
//
//        this.renderItemSlots(rendererGUI);
//
//        rendererGUI.text(offsetByDialogBounds(new GuiPosition(dialogTextureSource.width / 2, dialogTextureSource.height - 12)), "Knapping Station",
//                new TextColors(new Color(.33f, .33f, .33f, 1)), CallAlign.BOTTOM_CENTER);
    }
}
