package me.spoony.botanico.client.graphics.dialog;

import me.spoony.botanico.common.util.position.GuiPosition;
import me.spoony.botanico.common.dialog.DialogCrafting;
import me.spoony.botanico.common.util.IntRectangle;

/**
 * Created by Colten on 12/16/2016.
 */
public class DialogCraftingButton extends DialogButton
{
    protected DialogCrafting dialog;
    protected IntRectangle source;

    public DialogCraftingButton(int x, int y, DialogCrafting dialog, String texture, IntRectangle source)
    {
        super(new GuiPosition(x, y), texture);

        this.setTooltip("Craft");
        this.dialog = dialog;
        this.source = source;
    }

    @Override
    public IntRectangle getTextureSource()
    {
        IntRectangle ret = new IntRectangle((dialog.canCraft() ? isHighlighted() ? 1 : 0 : 2) * 16, 0, width, height);
        ret.x += source.x;
        ret.y += source.y;
        return ret;
    }
}
