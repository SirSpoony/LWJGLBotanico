package me.spoony.botanico.client.graphics.gui.control;

import me.spoony.botanico.common.util.position.GuiPosition;

/**
 * Created by Colten on 11/26/2016.
 */
public interface IGUIControlAlignable extends IGUIControl
{
    void setAlignment(GUIControlAlignmentType alignment);
    void setOffset(GuiPosition offset);
}
