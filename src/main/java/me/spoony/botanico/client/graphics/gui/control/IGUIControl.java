package me.spoony.botanico.client.graphics.gui.control;

import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.common.util.position.GuiRectangle;

/**
 * Created by Colten on 11/26/2016.
 */
public interface IGUIControl
{
    GuiRectangle getBounds();
    void render(RendererGUI rendererGUI);
}
