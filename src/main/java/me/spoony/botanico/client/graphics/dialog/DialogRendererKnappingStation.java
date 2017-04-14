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
import me.spoony.botanico.common.util.position.GuiPosition;
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
    private Set<DialogButtonKnappingStation> knappingModeButtons;
    private DialogCraftingButton craftingButton;

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

        craftingButton = new DialogCraftingButton(193, 40, dialog,
                TEXTURE_LOCATION, new IntRectangle(0, 128, 16, 16));

        knappingModeButtons = Sets.newHashSet();
        knappingModeButtons.add(new DialogButtonKnappingStation(142, 74, 0, "Blade"));
        knappingModeButtons.add(new DialogButtonKnappingStation(142, 74 - 17, 1, "Hoe Head"));
        knappingModeButtons.add(new DialogButtonKnappingStation(142, 74 - 17 * 2, 2, "Pickaxe Head"));
        knappingModeButtons.add(new DialogButtonKnappingStation(142, 74 - 17 * 3, 3, "Axe Head"));
    }

    @Override
    public void onBinaryInputPressed(BinaryInput bin) {
        super.onBinaryInputPressed(bin);

        if (bin != Input.BUTTON_LEFT && bin != Input.BUTTON_RIGHT) return;

        for (DialogButton db : knappingModeButtons) {
            db.updatePosition(this);
            db.checkClick(bin);
        }

        if (bin == Input.BUTTON_LEFT && craftingButton.isHighlighted() && dialog.canCraft()) {
            GameView.getClient().packetHandler.sendDialogButtonPress(0);
        }
    }

    @Override
    public void update(float delta) {
        if (dialog.canCraft()) {
            dialog.inventory.getSlot(1).setGhost(dialog.queryCraft());
        } else {
            dialog.inventory.getSlot(1).setGhost(null);
        }
    }

    @Override
    public void render(RendererGUI rendererGUI) {
        centerDialogBounds(rendererGUI.guiViewport);

        if (!isOpen()) return;

        Texture dialogTexture = rendererGUI.getResourceManager().getTexture(TEXTURE_LOCATION);

        rendererGUI.sprite(getDialogPosition(),
                dialogTexture, dialogTextureSource);

        craftingButton.updatePosition(this);
        craftingButton.render(rendererGUI);

        for (DialogButtonKnappingStation db : knappingModeButtons) {
            db.updatePosition(this);
            db.isselected = (db.id == dialog.mode);
            db.render(rendererGUI);
        }

//        System.out.println(((DialogKnappingStation) dialog).mode);

        this.renderItemSlots(rendererGUI);

        rendererGUI.text(offsetByDialogBounds(new GuiPosition(dialogTextureSource.width / 2, dialogTextureSource.height - 12)), "Knapping Station",
                new TextColors(new Color(.33f, .33f, .33f, 1)), CallAlign.BOTTOM_CENTER);
    }

    protected class DialogButtonKnappingStation extends DialogButton {
        public int id;
        public boolean isselected;

        public DialogButtonKnappingStation(int x, int y, int id, String tooltip) {
            super(new GuiPosition(x, y), "dialog/dialog_knapping_station.png");
            this.width = 16;
            this.height = 16;
            this.id = id;
            this.isselected = false;
            setTooltip(tooltip);
        }

        @Override
        public IntRectangle getTextureSource() {
            int retx = 48 + 16 * id;
            int rety = isHighlighted() ? 128 : 128 + 16;
            if (isselected) {
                rety += 16 * 2;
            }
            return new IntRectangle(retx, rety, 16, 16);
        }

        @Override
        public void onClick() {
            super.onClick();
            GameView.getClient().packetHandler.sendDialogButtonPress(id + 1);
        }
    }
}
