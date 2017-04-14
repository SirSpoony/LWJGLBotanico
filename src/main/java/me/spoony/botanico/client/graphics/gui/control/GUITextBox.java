package me.spoony.botanico.client.graphics.gui.control;

import com.google.common.base.Strings;
import me.spoony.botanico.client.engine.Color;
import me.spoony.botanico.client.engine.Texture;
import me.spoony.botanico.client.graphics.CallAlign;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.client.graphics.TextColors;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 11/18/2016.
 */
public class GUITextBox extends GUIControlAlignableAdapter implements ITextInputable, IClickable
{
    public String text;
    public String label;
    public boolean isEditing;

    public GUITextBox(String label) {
        this.label = label;
        this.text = "";

        this.width = 100;
        this.height = 16;
    }

    @Override
    public void render(RendererGUI rendererGUI) {
        preRender(rendererGUI);
        GuiRectangle bounds = this.getBounds();

        int hoveringoffset = containsCursor() ? 16: 0;
        Texture textFieldTexture = rendererGUI.getResourceManager().getTexture("text_field.png");
        rendererGUI.sprite(new GuiRectangle(bounds.x, bounds.y, bounds.width, bounds.height),
                textFieldTexture, new IntRectangle(10,hoveringoffset,12,16));
        rendererGUI.sprite(new GuiRectangle(bounds.x, bounds.y, 9, bounds.height),
                textFieldTexture, new IntRectangle(0,hoveringoffset,9,16));
        rendererGUI.sprite(new GuiRectangle(bounds.x+bounds.width-9, bounds.y, 9, bounds.height),
                textFieldTexture, new IntRectangle(23,hoveringoffset,9,16));

        if (!Strings.isNullOrEmpty(text)) {
            rendererGUI.text(
                    new GuiPosition(bounds.x+2, bounds.y+5),
                    text, TextColors.WHITE, CallAlign.BOTTOM_LEFT);
        } else if (!Strings.isNullOrEmpty(label)) {
            rendererGUI.text(
                    new GuiPosition(bounds.x+2, bounds.y+5),
                    label, new TextColors(new Color(.7f,.7f,.7f,1f), new Color(0,0,0,.5f)), CallAlign.BOTTOM_LEFT);
/*            rendererGUI.textbottom(
                    new Vector2(bounds.x+2, bounds.y+5),
                    label, TextColors.WHITE);*/
        }
    }

    @Override
    public void checkClick(BinaryInput binaryInput) {
        if (binaryInput == Input.BUTTON_LEFT) {
            isEditing = containsCursor();
        }
    }

    public boolean containsCursor() {
        if (getBounds().contains(Input.CURSOR_POS.toGuiPosition())) return true;
        return false;
    }

    @Override
    public void onCharTyped(char character)
    {
        if (!isEditing) return;
        if (character == '\u0000') return;

        if ((byte)character == 8) {
            if (Strings.emptyToNull(text) != null) text= text.substring(0, text.length()-1);
            return;
        }
        if ((byte)character == 13) {
            onEnter();
            return;
        }

        if (!acceptChar(character)) return;
        text+=character;
    }

    public boolean acceptChar(char character) {
        if (Character.isDigit(character) || Character.isLetter(character) || "./!@#$%^&*()-_+=[]{}|\\\"':;?<>,".indexOf(character) != -1) return true;
        return false;
    }

    public void onEnter() {

    }

    public void setWidth(float width) {
        this.width = width;
    }
}
