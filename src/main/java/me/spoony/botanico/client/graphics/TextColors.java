package me.spoony.botanico.client.graphics;

import me.spoony.botanico.client.engine.Color;

/**
 * Created by Colten on 11/8/2016.
 */
public class TextColors
{
    public static TextColors WHITE = new TextColors(new Color(0xFFFFFFFF), new Color(0,0,0,.5f));
    public static TextColors PINK = new TextColors(new Color(0xFF55FFFF), new Color(0,0,0,.5f));
    public static TextColors GREEN = new TextColors(new Color(0x55FF55FF), new Color(0,0,0,.5f));
    public static TextColors CYAN = new TextColors(new Color(0x55FFFFFF), new Color(0,0,0,.5f));
    public static TextColors YELLOW = new TextColors(new Color(0xFFFF55FF), new Color(0,0,0,.5f));
    public static TextColors RED = new TextColors(new Color(0xFF5555FF), new Color(0,0,0,.5f));

    public Color foreground;
    public Color background;

    public TextColors(Color foreground, Color background)
    {
        this.foreground = foreground;
        this.background = background;
    }

    public TextColors(Color foreground)
    {
        this.foreground = foreground;
        this.background = Color.CLEAR;
    }
}
