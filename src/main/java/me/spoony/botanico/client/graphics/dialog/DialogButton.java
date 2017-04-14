package me.spoony.botanico.client.graphics.dialog;

import com.google.common.base.Strings;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 12/2/2016.
 */
public class DialogButton {
    public String tooltip;
    public GuiPosition position;
    public GuiPosition renderposition;
    public int width;
    public int height;
    public String texture;

    public DialogButton(GuiPosition position, String texture) {
        this.position = position;
        this.renderposition = new GuiPosition();

        this.width = 16;
        this.height = 16;
        this.tooltip = null;

        this.texture = texture;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public IntRectangle getTextureSource() {
        return new IntRectangle();
    }

    public void render(RendererGUI rg) {
        if (isHighlighted()) {
            rg.sprite(renderposition, rg.getResourceManager().getTexture(texture),
                    getTextureSource());
            if (!Strings.isNullOrEmpty(tooltip)) {
                GameView.getCursor().getTooltip().setText(tooltip);
            }
        } else {
            rg.sprite(renderposition, rg.getResourceManager().getTexture(texture),
                    getTextureSource());
        }
    }

    public void updatePosition(GuiPosition offset) {
        renderposition.set(position);
        renderposition.add(offset);
    }

    public void updatePosition(DialogRenderer dialogRenderer) {
        renderposition.set(position);
        renderposition.add(dialogRenderer.dialogPosition());
    }

    public void checkClick(BinaryInput binaryInput) {
        if (isHighlighted() && binaryInput == Input.BUTTON_LEFT) {
            onClick();
        }
    }

    public boolean isHighlighted() {
        GuiRectangle mousebounds = new GuiRectangle(renderposition.x, renderposition.y, 18, 18);
        if (!mousebounds.contains(Input.CURSOR_POS.toGuiPosition())) return false;
        return true;
    }

    public void onClick() {

    }
}
