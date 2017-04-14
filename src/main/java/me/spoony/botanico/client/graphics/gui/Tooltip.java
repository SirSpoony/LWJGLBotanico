package me.spoony.botanico.client.graphics.gui;

import com.google.common.base.Strings;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/10/2016.
 */
public class Tooltip
{
    protected GuiPosition position;
    protected String text;

    public Tooltip()
    {
        position = new GuiPosition();
    }

    public GuiPosition getPosition()
    {
        return position;
    }

    public void setPosition(GuiPosition position)
    {
        this.position.set(position);
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = Strings.emptyToNull(text);
    }

    public void render(RendererGUI rendererGUI)
    {
        if (getText() == null) return;

        GuiRectangle backbounds = new GuiRectangle(position.x, position.y - rendererGUI.getTextBounds(text).height, rendererGUI.getTextBounds(text).width, 10);
        backbounds.x -= 2;
        backbounds.y -= 3;
        backbounds.width += 3;
        backbounds.height += 2;

        Texture tttex = rendererGUI.getResourceManager().getTexture("tooltip.png");

        GuiRectangle fillbounds = new GuiRectangle(backbounds);
        rendererGUI.sprite(fillbounds, tttex, new IntRectangle(6, 6, 4, 4), Color.WHITE);


        GuiRectangle bottombounds = new GuiRectangle(backbounds.x, backbounds.y, backbounds.width, 4);
        rendererGUI.sprite(bottombounds, tttex, new IntRectangle(6, 12, 4, 4), Color.WHITE);

        GuiRectangle topbounds = new GuiRectangle(backbounds.x, backbounds.y + backbounds.height - 4, backbounds.width, 4);
        rendererGUI.sprite(topbounds, tttex, new IntRectangle(6, 0, 4, 4), Color.WHITE);


        GuiRectangle leftbounds = new GuiRectangle(backbounds.x, backbounds.y, 4, backbounds.height);
        rendererGUI.sprite(leftbounds, tttex, new IntRectangle(0, 6, 4, 4), Color.WHITE);

        GuiRectangle rightbounds = new GuiRectangle(backbounds.x + backbounds.width - 4, backbounds.y, 4, backbounds.height);
        rendererGUI.sprite(rightbounds, tttex, new IntRectangle(12, 6, 4, 4), Color.WHITE);


        GuiRectangle bottomleftbounds = new GuiRectangle(backbounds.x, backbounds.y, 4, 4);
        rendererGUI.sprite(bottomleftbounds, tttex, new IntRectangle(0, 12, 4, 4), Color.WHITE);

        GuiRectangle bottomrightbounds = new GuiRectangle(backbounds.x + backbounds.width - 4, backbounds.y, 4, 4);
        rendererGUI.sprite(bottomrightbounds, tttex, new IntRectangle(12, 12, 4, 4), Color.WHITE);

        GuiRectangle toprightbounds = new GuiRectangle(backbounds.x + backbounds.width - 4, backbounds.y + backbounds.height - 4, 4, 4);
        rendererGUI.sprite(toprightbounds, tttex, new IntRectangle(12, 0, 4, 4), Color.WHITE);

        GuiRectangle topleftbounds = new GuiRectangle(backbounds.x, backbounds.y + backbounds.height - 4, 4, 4);
        rendererGUI.sprite(topleftbounds, tttex, new IntRectangle(0, 0, 4, 4), Color.WHITE);

        rendererGUI.text(position, text, TextColors.WHITE, CallAlign.TOP_LEFT);
    }

    public void clear() {
        setText(null);
    }
}
