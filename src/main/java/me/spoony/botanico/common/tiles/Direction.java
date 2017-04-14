package me.spoony.botanico.common.tiles;

/**
 * Created by Colten on 11/8/2016.
 */
public enum Direction
{
    DOWN((byte)0),
    DOWN_RIGHT((byte)1),
    RIGHT((byte)2),
    UP_RIGHT((byte)3),
    UP((byte)4),
    UP_LEFT((byte)5),
    LEFT((byte)6),
    DOWN_LEFT((byte)7);

    private byte value;

    private Direction(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
