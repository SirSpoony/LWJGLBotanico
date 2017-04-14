package me.spoony.botanico.client.input;

/**
 * Created by coltenwebb on 11/9/16.
 */
public class BinaryInput {
    // 0 = key, 1 = mouse
    public final BinaryInputType type;
    public final int id;

    protected boolean down;

    public BinaryInput(BinaryInputType type, int id) {
        this.type = type;
        this.id = id;
    }

    public boolean isDown() {
        return down;
    }
}
