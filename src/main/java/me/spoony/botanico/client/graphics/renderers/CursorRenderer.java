package me.spoony.botanico.client.graphics.renderers;

import com.google.common.base.Preconditions;
import me.spoony.botanico.client.ClientEntityPlayer;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.gui.GUIRenderable;
import me.spoony.botanico.client.graphics.gui.RendererItemStack;
import me.spoony.botanico.client.graphics.gui.Tooltip;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.items.ItemStack;

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
        GuiPosition renderpos = Input.CURSOR_POS.toGuiPosition(new GuiPosition());
        renderpos.x-=8;
        renderpos.y-=8;

        if (getStack() != null) {
            RendererItemStack rendererItemStack = new RendererItemStack(getStack());
            rendererItemStack.clickEffect = Input.BUTTON_LEFT.isDown();
            rendererItemStack.setPosition(renderpos);
            rendererItemStack.render(rendererGUI);
        } else {
            tooltip.getPosition().set(renderpos);
            tooltip.getPosition().x += 11;
            tooltip.getPosition().y += 18;
            tooltip.render(rendererGUI);
            tooltip.clear();
        }
    }
}
