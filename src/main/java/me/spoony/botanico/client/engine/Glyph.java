package me.spoony.botanico.client.engine;

/**
 * Created by Colten on 12/26/2016.
 */
public class Glyph {
    public final int x,y;
    public final int width,height;
    public final int xoffset,yoffset;

    public Glyph(int x, int y, int width, int height, int xoffset, int yoffset) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
    }
}
