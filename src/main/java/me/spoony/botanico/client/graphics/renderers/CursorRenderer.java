package me.spoony.botanico.client.graphics.renderers;

import com.google.common.base.Preconditions;
import me.spoony.botanico.client.ClientEntityPlayer;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.gui.GUIRenderable;
import me.spoony.botanico.client.graphics.gui.RendererItemStack;
import me.spoony.botanico.client.graphics.gui.Tooltip;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by Colten on 11/8/2016.
 */
public class CursorRenderer implements GUIRenderable
{
    private ItemSlot slot;
    private Tooltip tooltip;

    public ItemStack getStack()
    {
        return slot.getStack();
    }

    public void setStack(ItemStack stack)
    {
        this.slot.setStack(stack);
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public CursorRenderer(ClientEntityPlayer player)
    {
        Preconditions.checkNotNull(player);
        tooltip = new Tooltip();
        slot = player.inventory.getSlot(EntityPlayer.SLOT_CURSOR);
    }

    @Override
    public void render(RendererGUI rendererGUI)
    {
        float renderX = (float) Input.CURSOR_POS.getX(PositionType.GUI);
        float renderY = (float) Input.CURSOR_POS.getY(PositionType.GUI);
        renderX-=8;
        renderY-=8;

        if (getStack() != null) {
            RendererItemStack rendererItemStack = new RendererItemStack(getStack());
            rendererItemStack.clickEffect = Input.BUTTON_LEFT.isDown();
            rendererItemStack.setPosition(renderX, renderY);
            rendererItemStack.render(rendererGUI);
        } else {
            tooltip.setPosition(renderX + 11, renderY + 18);
            tooltip.render(rendererGUI);
            tooltip.clear();
        }
    }
}
