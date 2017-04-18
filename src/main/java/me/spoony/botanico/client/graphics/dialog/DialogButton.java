package me.spoony.botanico.client.graphics.dialog;

import com.google.common.base.Strings;
import me.spoony.botanico.client.graphics.RendererGUI;
import me.spoony.botanico.common.util.position.GuiRectangle;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.client.input.Input;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.util.IntRectangle;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by Colten on 12/2/2016.
 */
public class DialogButton {
    public String tooltip;
    public OmniPosition position;
    private float renderX;
    private float renderY;
    public int width;
    public int height;
    public String texture;

    public DialogButton(OmniPosition position, String texture) {
        this.position = position;

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
            rg.sprite(renderX, renderY, rg.getResourceManager().getTexture(texture),
                    getTextureSource());
            if (!Strings.isNullOrEmpty(tooltip)) {
                GameView.getCursor().getTooltip().setText(tooltip);
            }
        } else {
            rg.sprite(renderX, renderY, rg.getResourceManager().getTexture(texture),
                    getTextureSource());
        }
    }

    public void updateOffsetPosition(float offsetX, float offsetY) {
        renderX = position.getGuiX() + offsetX;
        renderY = position.getGuiY() + offsetY;
    }

    public void updateOffsetPosition(DialogRenderer dialogRenderer) {
        renderX = position.getGuiX() + (float)dialogRenderer.dialogPosition().getX(PositionType.GUI);
        renderY = position.getGuiY() + (float)dialogRenderer.dialogPosition().getY(PositionType.GUI);
    }

    public void checkClick(BinaryInput binaryInput) {
        if (isHighlighted() && binaryInput == Input.BUTTON_LEFT) {
            onClick();
        }
    }

    public boolean isHighlighted() {
        GuiRectangle mousebounds = new GuiRectangle(renderX, renderY, 18, 18);
        if (!mousebounds.contains(Input.CURSOR_POS)) return false;
        return true;
    }

    public void onClick() {

    }
}
