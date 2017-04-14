package me.spoony.botanico.client.graphics.gui.control;

import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/11/2016.
 */
public class GUITexture extends GUIControlAlignableAdapter
{
    protected Texture texture;
    protected IntRectangle textureSource;

    public GUITexture(Texture texture, IntRectangle textureSource) {
        this.texture = texture;
        this.textureSource = textureSource;

        this.width = textureSource.width;
        this.height = textureSource.height;
    }

    public void render(RendererGUI rendererGUI) {
        preRender(rendererGUI);

        rendererGUI.sprite(getBounds(), texture, textureSource, Color.WHITE);
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
