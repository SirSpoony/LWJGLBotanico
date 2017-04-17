package me.spoony.botanico.client.graphics.gui.control;

/**
 * Created by Colten on 11/26/2016.
 */
public interface IGUIControlAlignable extends IGUIControl
{
    void setAlignment(GUIControlAlignmentType alignment);
    void setOffset(GuiPosition offset);
}
