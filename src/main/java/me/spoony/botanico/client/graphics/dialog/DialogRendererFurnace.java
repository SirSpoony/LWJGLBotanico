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

    dialogTextureSource = new IntRectangle(0, 0, 219, 135);
    dialogBounds
        .set(new GuiRectangle(0, 0, dialogTextureSource.width, dialogTextureSource.height));

    initPlayerItemSlots(player.inventory, 4, 25);

    rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(0), 160, 76+18));
    rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(1), 160, 76));

    rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(2), 160+36, 76+18));
  }

  @Override
  public void update(float delta) {

  }

  @Override
  public void render(RendererGUI rg) {
        centerDialogBounds(rg.guiViewport);

        if (!isOpen()) return;

        rg.sprite(dialogBounds.x, dialogBounds.y,
                rg.getResourceManager().getTexture(TEXTURE_LOCATION), dialogTextureSource);

/*        // Render smelting progress
        rg.sprite(offsetByDialogBounds(new GuiPosition(163, 67)),
                rg.getResourceManager().getTexture(TEXTURE_LOCATION), new IntRectangle(0, 128, (int)(42f*this.dialog.progress), 8));

        // Render fuel progress
        rg.sprite(offsetByDialogBounds(new GuiPosition(156, 42)),
                rg.getResourceManager().getTexture(TEXTURE_LOCATION), new IntRectangle(0, 150, (int)(58f*this.dialog.burnProgress), 8));*/

        this.renderItemSlots(rg);

    rg.text(offsetXByDialogBounds(150 / 2), offsetYByDialogBounds(dialogTextureSource.height - 14),
        "Furnace",
        new TextColors(new Color(173 / 255f, 100 / 255f, 0 / 255f, 1)), CallAlign.BOTTOM_CENTER);
  }
}
