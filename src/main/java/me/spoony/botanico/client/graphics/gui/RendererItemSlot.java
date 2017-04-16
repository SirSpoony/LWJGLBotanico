package me.spoony.botanico.client.graphics.gui;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.dialog.DialogRenderer;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.util.IntRectangle;

import static com.google.common.base.Preconditions.*;

/**
 * Created by Colten on 11/10/2016.
 */
public class RendererItemSlot implements GUIRenderable {

  protected ItemSlot itemSlot;

  public ItemSlot getItemSlot() {
    return itemSlot;
  }

  protected GuiPosition position;
  private GuiPosition renderposition;

  protected RendererItemStack guiitem;

  public ItemStack getStack() {
    return itemSlot.getStack();
  }

  public RendererItemSlot(ItemSlot itemSlot, int x, int y) {
    checkNotNull(itemSlot, "RendererItemSlot cannot be initialized with null itemslot!");
    this.position = new GuiPosition(x, y);
    this.renderposition = new GuiPosition();
    this.itemSlot = itemSlot;
    this.guiitem = new RendererItemStack(itemSlot.getStack());
  }

  public void setPosition(GuiPosition pos) {
    position.set(pos);
  }

  public void update(float delta) {

  }

  public void checkClick(BinaryInput binaryInput) {
    GuiRectangle slotbounds = new GuiRectangle(renderposition.x, renderposition.y, 16, 16);
    if (!slotbounds.contains(Input.CURSOR_POS.toGuiPosition())) {
      return;
    }

    byte clicktype = (binaryInput == Input.BUTTON_RIGHT ? (byte) 1 : (byte) 0);
    if (Input.SHIFT.isDown()) {
      clicktype += 2;
    }
    GameView.getClient().packetHandler.sendItemSlotExchange(itemSlot, clicktype);
  }

  public void render(RendererGUI rg) {
    //if (GameView.get() == null) return;

    GuiRectangle slotbounds = new GuiRectangle(renderposition.x, renderposition.y, 16, 16);
    if (slotbounds.contains(Input.CURSOR_POS.toGuiPosition())) {
      rg.sprite(slotbounds, rg.getResourceManager().getTexture("slot_highlight.png"),
          new IntRectangle(0, 0, 16, 16), new Color(1, 1, 1, .5f));

      if (itemSlot.getStack() != null) {
        GameView.getCursor().getTooltip().setText(getStack().getItem().getLocalizedName());
      } else if (itemSlot.getGhost() != null) {
        GameView.getCursor().getTooltip().setText(itemSlot.getGhost().getItem().getLocalizedName());
      }
    }

    guiitem.updateFromSlot(itemSlot);
    guiitem.setPosition(renderposition);
    guiitem.render(rg);
  }

  public void updatePosition(GuiPosition offset) {
    renderposition.set(position);
    renderposition.add(offset);
  }

  public void updatePosition(DialogRenderer dialogRenderer) {
    renderposition.set(position);
    renderposition.add(dialogRenderer.dialogPosition());
  }
}
