package me.spoony.botanico.client.graphics.gui.control;

import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.util.position.GuiRectangle;

/**
 * Created by Colten on 11/26/2016.
 */
public class GUIControlAlignableAdapter implements IGUIControlAlignable
{
    GUIControlAlignmentType alignment;
    GuiPosition offset;

    protected float width;
    protected float height;

    private GuiRectangle bounds;

    public GUIControlAlignableAdapter() {
        alignment = GUIControlAlignmentType.BOTTOM_LEFT;
        offset = new GuiPosition();

        width = 0;
        height = 0;

        bounds = new GuiRectangle();
    }

    @Override
    public void setAlignment(GUIControlAlignmentType alignment)
    {
        this.alignment = alignment;
    }

    @Override
    public void setOffset(GuiPosition offset)
    {
        this.offset.set(offset);
    }

    @Override
    public GuiRectangle getBounds()
    {
        return bounds;
    }

    @Override
    public void render(RendererGUI rendererGUI)
    {
        preRender(rendererGUI);
    }

    public void updateBounds(GuiRectangle viewport) {
        GuiRectangle ret = new GuiRectangle(0,0,width,height);

        if (alignment.x == 0) {
            // do nothing
        } else if (alignment.x == 1) {
            ret.setCenter(new GuiPosition(viewport.getCenter().x, ret.getCenter().y));
        } else if (alignment.x == 2) {
            ret.setPosition(new GuiPosition(viewport.width-ret.width, ret.y));
        }

        if (alignment.y == 0) {
            // do nothing
        } else if (alignment.y == 1) {
            ret.setCenter(new GuiPosition(ret.getCenter(new GuiPosition()).x, viewport.getCenter(new GuiPosition()).y));
        } else if (alignment.y == 2) {
            ret.setPosition(new GuiPosition(ret.x, viewport.height-ret.height));
        }

        ret.x+=offset.x;
        ret.y+=offset.y;

        this.bounds.set(ret);
    }

    protected void preRender(RendererGUI rendererGUI) {
        updateBounds(rendererGUI.guiViewport);
    }
}
