package me.spoony.botanico.client.graphics.dialog;

import me.spoony.botanico.common.dialog.*;

/**
 * Created by Colten on 11/25/2016.
 */
public class DialogRenderers
{
    public static Class<? extends DialogRenderer> getDialogRendererClass(Dialog dialog) {
        if (dialog instanceof DialogToolStation) return DialogRendererToolStation.class;
        if (dialog instanceof DialogInventoryPlayer) return DialogRendererInventoryPlayer.class;
        if (dialog instanceof DialogKnappingStation) return DialogRendererKnappingStation.class;
        if (dialog instanceof DialogFurnace) return DialogRendererFurnace.class;
        if (dialog instanceof DialogBoiler) return DialogRendererBoiler.class;

        return null;
    }

    public static DialogRenderer newDialogRenderer(Dialog dialog) {
        Class<? extends DialogRenderer> rendererclass = getDialogRendererClass(dialog);
        if (rendererclass == null) {
            throw new RuntimeException("Couldn't load a dialog renderer for this dialog!");
        }
        try
        {
            DialogRenderer renderer = rendererclass.newInstance();
            renderer.init(dialog);
            return renderer;
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
