package me.spoony.botanico.client.engine;

/**
 * Created by Colten on 12/25/2016.
 */
public class Color {
    public static final Color WHITE = new Color(1, 1, 1, 1);
    public static final Color BLACK = new Color(0, 0, 0, 1);
    public static final Color GRAY = new Color(.5f, .5f, .5f, 1);
    public static final Color CLEAR = new Color(0, 0, 0, 0);

    public float r, g, b, a;

    public Color(int rgba8888) {
        rgba8888(this, rgba8888);
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(Color color) {
        this(color.r, color.g, color.b, color.a);
    }

    public void set(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    public static void rgba8888(Color color, int value) {
        color.r = ((value & 0xff000000) >>> 24) / 255f;
        color.g = ((value & 0x00ff0000) >>> 16) / 255f;
        color.b = ((value & 0x0000ff00) >>> 8) / 255f;
        color.a = ((value & 0x000000ff)) / 255f;
    }
}
