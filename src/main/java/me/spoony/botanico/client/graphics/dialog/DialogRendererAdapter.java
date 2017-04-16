package me.spoony.botanico.client.graphics.dialog;

import com.google.common.collect.Sets;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.gui.RendererItemSlot;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.items.ItemSlot;

import java.util.Set;

/**
 * Created by Colten on 11/10/2016.
 */
public abstract class DialogRendererAdapter<T extends Dialog> implements DialogRenderer<T> {

  public GuiRectangle dialogBounds;
  public T dialog;
  protected boolean open;

  protected Set<RendererItemSlot> rendererItemSlots;

  public DialogRendererAdapter() {
    this.dialogBounds = new GuiRectangle();
    this.dialog = null;
    this.open = false;
  }

  public void centerDialogBounds(GuiRectangle viewport) {
    this.dialogBounds.setCenter(viewport.getCenter());
    this.dialogBounds
        .setPosition((float) Math.floor(dialogBounds.x), (float) Math.floor(dialogBounds.y));
  }

  public GuiPosition getDialogPosition() {
    return dialogBounds.getPosition();
  }

  public GuiPosition offsetByDialogBounds(GuiPosition pos) {
    pos.x += dialogBounds.x;
    pos.y += dialogBounds.y;
    return pos;
  }

  public GuiRectangle offsetByDialogBounds(GuiRectangle bounds) {
    bounds.x += dialogBounds.x;
    bounds.y += dialogBounds.y;
    return bounds;
  }

  @Override
  public void open() {
    this.open = true;
  }

  @Override
  public void close() {
    this.open = false;
  }

  @Override
  public boolean isOpen() {
    return open;
  }

  @Override
  public T getDialog() {
    return dialog;
  }

  @Override
  public void init(T dialog) {
    this.dialog = dialog;
  }

  protected void initPlayerItemSlots(Inventory playerInventory, int offsetx, int offsety) {
    this.rendererItemSlots = Sets.newHashSet();
    for (int row = 1; row <= 5; row++) {
      for (int col = 0; col < 8; col++) {
        int slotid = row * 8 + col;
        ItemSlot slot = playerInventory.getSlot(slotid);
        rendererItemSlots.add(new RendererItemSlot(slot, col * 18 + offsetx,
            ((-row + 6) - 1) * 18 + offsety));
      }
    }
  }

  protected void renderItemSlots(RendererGUI rendererGUI) {
    for (RendererItemSlot slot : rendererItemSlots) {
      if (slot == null) {
        continue;
      }
      slot.updatePosition(this);
      slot.render(rendererGUI);
    }
  }

  @Override
  public void onBinaryInputPressed(BinaryInput bin) {
    if (bin == Input.INVENTORY) {
      GameView.getPlayer().closeCurrentDialog();
      return;
    }

    if (bin != Input.BUTTON_LEFT && bin != Input.BUTTON_RIGHT) {
      return;
    }
    for (RendererItemSlot slot : rendererItemSlots) {
      if (slot == null) {
        continue;
      }
      slot.checkInteraction(bin);
    }
  }

  @Override
  public GuiPosition dialogPosition() {
    return dialogBounds.getPosition();
  }
}
