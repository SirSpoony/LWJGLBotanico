package me.spoony.botanico.client.graphics.gui.control;

import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by Colten on 11/26/2016.
 */
public class GUIControlAlignableAdapter implements IGUIControlAlignable
{
    GUIControlAlignmentType alignment;
    float offsetX;
    float offsetY;

    protected float width;
    protected float height;

    private GuiRectangle bounds;

    public GUIControlAlignableAdapter() {
        alignment = GUIControlAlignmentType.BOTTOM_LEFT;

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
    public void setOffset(float x, float y)
    {
        this.offsetX = x;
        this.offsetY = y;
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
            ret.setCenter((float)viewport.getCenter().getX(PositionType.GUI), (float)ret.getCenter().getY(PositionType.GUI));
        } else if (alignment.x == 2) {
            ret.setPosition(viewport.width-ret.width, ret.y);
        }

        if (alignment.y == 0) {
            // do nothing
        } else if (alignment.y == 1) {
            ret.setCenter((float)ret.getCenter().getX(PositionType.GUI), (float)viewport.getCenter().getY(PositionType.GUI));
        } else if (alignment.y == 2) {
            ret.setPosition(ret.x, viewport.height-ret.height);
        }

        ret.x+=offsetX;
        ret.y+=offsetY;

        this.bounds.set(ret);
    }

    protected void preRender(RendererGUI rendererGUI) {
        updateBounds(rendererGUI.guiViewport);
    }
}
