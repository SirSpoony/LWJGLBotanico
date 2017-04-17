package me.spoony.botanico.client.graphics.gui;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.util.position.GuiRectangle;

/**
 * Created by Colten on 11/10/2016.
 */
public class RendererItemStack {
    private static Color ghost_color = new Color(1, 1, 1, .5f);
    protected ItemStack stack;
    public boolean isGhost;
    public boolean clickEffect;

    public float x;
    public float y;

    public RendererItemStack(ItemStack itemstack) {
        this.stack = itemstack;
        this.isGhost = false;
        this.clickEffect = false;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
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
            rg.sprite(x, y, texture,
                    stack.getItem().textureBounds, ghost_color);
        } else {
            if (clickEffect) {
                rg.sprite(new GuiRectangle(x+1, y+1, 14, 14), texture, stack.getItem().textureBounds);
            } else {
                rg.sprite(x, y, texture, stack.getItem().textureBounds);
            }
            if (stack.showCount()) {
                rg.text(x, y, "" + stack.getCount(), TextColors.WHITE, CallAlign.BOTTOM_LEFT);
            }
        }
    }
}
