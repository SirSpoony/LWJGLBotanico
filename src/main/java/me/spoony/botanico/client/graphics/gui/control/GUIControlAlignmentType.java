package me.spoony.botanico.client.graphics.gui.control;

/**
 * Created by Colten on 11/26/2016.
 */
public enum GUIControlAlignmentType
{
    TOP_LEFT ((byte)0,(byte)2),
    TOP_CENTER ((byte)1,(byte)2),
    TOP_RIGHT ((byte)2,(byte)2),

    CENTER_LEFT ((byte)0,(byte)1),
    CENTER_CENTER ((byte)1,(byte)1),
    CENTER_RIGHT ((byte)2,(byte)1),

    BOTTOM_LEFT ((byte)0,(byte)0),
    BOTTOM_CENTER ((byte)1,(byte)0),
    BOTTOM_RIGHT ((byte)2,(byte)0);

    public final byte x;
    public final byte y;

    GUIControlAlignmentType(byte x, byte y) {
        this.x = x;
        this.y = y;
    }
}
