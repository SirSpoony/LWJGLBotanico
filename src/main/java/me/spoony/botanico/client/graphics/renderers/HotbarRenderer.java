package me.spoony.botanico.client.graphics.renderers;

import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.gui.GUIRenderable;
import me.spoony.botanico.client.graphics.gui.RendererItemSlot;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.common.items.Inventory;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 12/17/2016.
 */
public class HotbarRenderer implements GUIRenderable {

  private GuiRectangle bounds;
  private IntRectangle textureregion;

  private RendererItemSlot[] rendererItemSlots;

  public HotbarRenderer(Inventory inventory) {
    textureregion = new IntRectangle(0, 0, 158, 18);
    bounds = new GuiRectangle(0, 0, textureregion.width, textureregion.height);

    rendererItemSlots = new RendererItemSlot[8];

    for (int i = 0; i < rendererItemSlots.length; i++) {
      rendererItemSlots[i] = new RendererItemSlot(inventory.getSlot(i), 1 + i * 20, 1);
    }
  }

  public boolean onBinaryInputPressed(BinaryInput bin) {
    if (bin != Input.BUTTON_LEFT && bin != Input.BUTTON_RIGHT) {
      return false;
    }
    if (bounds.contains(Input.CURSOR_POS.toGuiPosition())) {
      for (RendererItemSlot slot : rendererItemSlots) {
        slot.checkInteraction(bin);
      }
      return true;
    }
    return false;
  }

  @Override
  public void render(RendererGUI rg) {
    bounds.setCenter(rg.guiViewport.getCenter());
    bounds.y = 3;

    for (int i = 0; i < rendererItemSlots.length; i++) {
      rendererItemSlots[i].updatePosition(bounds.getPosition());
    }

    Texture texture = rg.getResourceManager().getTexture("hotbar.png");

    rg.sprite(bounds.getPosition(), texture,
        new IntRectangle(0, 0, textureregion.width, textureregion.height));

    float damage = .5f;

    rg.sprite(bounds.getPosition().add(18f, 21f), texture,
        new IntRectangle(0, 24, 113, 14));

    rg.sprite(bounds.getPosition().add(18f + 11, 25f), texture,
        new IntRectangle(0, 42, (int) Math.floor(100 * damage), 6));

    for (RendererItemSlot slot : rendererItemSlots) {
      slot.render(rg);
    }

    if (GameView.getPlayer().isMining()) {
      ItemSlot slot = GameView.getPlayer().getCurrentBuildingDamageTool();
      if (slot == null) {
        return;
      }
      rg.sprite(bounds.getPosition().add(slot.getSlotIndex() * 17, 0f), texture,
          new IntRectangle(124, 0, 20, 20));
    }
  }
}
