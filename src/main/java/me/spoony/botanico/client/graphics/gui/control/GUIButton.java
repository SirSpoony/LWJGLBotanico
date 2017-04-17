package me.spoony.botanico.client.graphics.gui.control;

import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/11/2016.
 */
public class GUIButton extends GUIControlAlignableAdapter implements IClickable
{
    String text;

    public GUIButton(String text) {
        this.text = text;

        this.width = 100;
        this.height = 16;
    }

    public void render(RendererGUI rendererGUI) {
        preRender(rendererGUI);
        GuiRectangle bounds = this.getBounds();

        int hoveringoffset = doesContainCursor() ? 16: 0;

        rendererGUI.sprite(new GuiRectangle(bounds.x, bounds.y, bounds.width, bounds.height),
                rendererGUI.getResourceManager().getTexture("button.png"), new IntRectangle(10,hoveringoffset,12,16));
        rendererGUI.sprite(new GuiRectangle(bounds.x, bounds.y, 9, bounds.height),
                rendererGUI.getResourceManager().getTexture("button.png"), new IntRectangle(0,hoveringoffset,9,16));
        rendererGUI.sprite(new GuiRectangle(bounds.x+bounds.width-9, bounds.y, 9, bounds.height),
                rendererGUI.getResourceManager().getTexture("button.png"), new IntRectangle(23,hoveringoffset,9,16));

        GuiRectangle textbounds = rendererGUI.getTextBounds(text);
        textbounds.setCenter(bounds.getCenter());
        textbounds.y += 2;
        rendererGUI.text(
                textbounds.x, textbounds.y,
                text, TextColors.WHITE, CallAlign.BOTTOM_LEFT);
    }

    @Override
    public void checkClick(BinaryInput binaryInput) {
        if (binaryInput == Input.BUTTON_LEFT) {
            if (doesContainCursor()) {
                onClick();
            }
        }
    }

    public void onClick() {
//        Sound sound = Botanico.INSTANCE.getResourceManager().getSound("button.wav");
//        sound.play(); todo play sound
    }

    public boolean doesContainCursor() {
        if (getBounds().contains(Input.CURSOR_POS)) return true;
        return false;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
