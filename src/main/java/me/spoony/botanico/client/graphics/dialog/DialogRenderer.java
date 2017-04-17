package me.spoony.botanico.client.graphics.dialog;

import me.spoony.botanico.client.graphics.gui.GUIRenderable;
import me.spoony.botanico.client.input.BinaryInput;
import me.spoony.botanico.common.dialog.Dialog;
import me.spoony.botanico.common.util.position.OmniPosition;

/**
 * Created by Colten on 11/8/2016.
 */
public interface DialogRenderer<T extends Dialog> extends GUIRenderable
{
    void init(T dialog);

    void open();
    void close();

    void onBinaryInputPressed(BinaryInput bin);

    void update(float delta);

    T getDialog();
    boolean isOpen();

    OmniPosition dialogPosition();
}
