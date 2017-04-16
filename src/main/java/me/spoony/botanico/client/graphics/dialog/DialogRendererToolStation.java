package me.spoony.botanico.client.graphics.dialog;

import me.spoony.botanico.client.ClientEntityPlayer;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.client.graphics.gui.RendererItemSlot;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.common.dialog.DialogToolStation;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/12/2016.
 */
public class DialogRendererToolStation extends DialogRendererAdapter {

  protected IntRectangle dialogTextureSource;
  private RendererGUI renderer;

  @Override
  public void init(Dialog dialog) {
    super.init(dialog);
    ClientEntityPlayer player = GameView.getClient().getLocalPlayer();

    this.dialogTextureSource = new IntRectangle(0, 0, 198, 108);
    this.dialogBounds
        .set(new GuiRectangle(0, 0, dialogTextureSource.width, dialogTextureSource.height));

    this.initPlayerItemSlots(player.inventory, 6, 6);

    rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(0), 142, 23));
    rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(1), 142, 40));
    rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(2), 142, 57));

    rendererItemSlots.add(new RendererItemSlot(dialog.inventory.getSlot(3), 176, 40));
  }

  @Override
  public void onBinaryInputPressed(BinaryInput bin) {
    super.onBinaryInputPressed(bin);
  }

  @Override
  public void update(float delta) {
  }
  @Override
  public void render(RendererGUI rendererGUI) {
    centerDialogBounds(rendererGUI.guiViewport);

    if (!isOpen()) {
      return;
    }

    rendererGUI.sprite(getDialogPosition(),
        rendererGUI.getResourceManager().getTexture("dialog/dialog_tool_station.png"),
        dialogTextureSource);

    this.renderItemSlots(rendererGUI);

    rendererGUI.text(offsetByDialogBounds(
        new GuiPosition(dialogTextureSource.width / 2, dialogTextureSource.height - 12)),
        "Tool Station",
        new TextColors(new Color(.33f, .33f, .33f, 1)), CallAlign.BOTTOM_CENTER);
  }
}
