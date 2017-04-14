package me.spoony.botanico.client.graphics.gui;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.util.position.GuiRectangle;

/**
 * Created by Colten on 11/10/2016.
 */
public class RendererItemStack {
    private static Color ghost_color = new Color(1, 1, 1, .5f);
    protected GuiPosition position;
    protected ItemStack stack;
    public boolean isGhost;
    public boolean clickEffect;

    public RendererItemStack(ItemStack itemstack) {
        this.stack = itemstack;
        this.isGhost = false;
        this.position = new GuiPosition();
        this.clickEffect = false;
    }

    public GuiPosition getPosition() {
        return position;
    }

    public void setPosition(GuiPosition position) {
        this.position = new GuiPosition(position);
    }

    public void updateFromSlot(ItemSlot slot) {
        if (slot.getStack() != null) {
            stack = slot.getStack();
            isGhost = false;
        } else if (slot.getGhost() != null) {
            stack = slot.getGhost();
            isGhost = true;
        } else {
            stack = null;
        }
    }

    public void render(RendererGUI rg) {
        if (stack == null) return;
        Texture texture = rg.getResourceManager().getTexture("items.png");
        if (isGhost) {
            rg.sprite(position, texture,
                    stack.getItem().textureBounds, ghost_color);
        } else {
            if (clickEffect) {
                rg.sprite(new GuiRectangle(position.x+1, position.y+1, 14, 14), texture, stack.getItem().textureBounds);
            } else {
                rg.sprite(position, texture, stack.getItem().textureBounds);
            }
            if (stack.showCount()) {
                rg.text(position, "" + stack.getCount(), TextColors.WHITE, CallAlign.BOTTOM_LEFT);
            }
        }
    }
}
