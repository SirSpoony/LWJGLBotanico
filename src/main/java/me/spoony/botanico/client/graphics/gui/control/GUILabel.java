package me.spoony.botanico.client.graphics.gui.control;

import com.google.common.base.Strings;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.common.util.position.GuiRectangle;

/**
 * Created by Colten on 11/24/2016.
 */
public class GUILabel extends GUIControlAlignableAdapter
{
    private String text;

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public GUILabel(String text)
    {
        this.setText(text);
    }

    @Override
    public void render(RendererGUI rendererGUI)
    {
        GuiRectangle textbounds = rendererGUI.getTextBounds(Strings.nullToEmpty(getText()));
        this.width = textbounds.width;
        this.height = textbounds.height;

        preRender(rendererGUI);

        rendererGUI.text(
                getBounds().getPosition(),
                text, TextColors.WHITE, CallAlign.BOTTOM_LEFT);
    }
}
