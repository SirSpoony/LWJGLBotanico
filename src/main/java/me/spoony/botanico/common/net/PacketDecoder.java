package me.spoony.botanico.common.net;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * Created by Colten on 11/20/2016.
 */
public class PacketDecoder
{
    private ByteBuf byteBuf;

    private PacketDecoder()
    {

    }

    public static PacketDecoder start(ByteBuf byteBuf)
    {
        PacketDecoder pe = new PacketDecoder();
        pe.byteBuf = byteBuf;
        return pe;
    }

    public ByteBuf finish()
    {
        return byteBuf;
    }

    public int readInt()
    {
        return byteBuf.readInt();
    }

    public String readString()
    {
        int length = byteBuf.readInt();
        return byteBuf.readCharSequence(length, Charset.defaultCharset()).toString();
    }

    public float readFloat()
    {
        return byteBuf.readFloat();
    }

    public int[] readIntArray()
    {
        int length = byteBuf.readInt();
        int[] ret = new int[length];
        for (int i = 0; i < length; i++)
        {
            ret[i] = byteBuf.readInt();
        }
        return ret;
    }

    public byte readByte() {
        return byteBuf.readByte();
    }

    public Short readShort() {
        return byteBuf.readShort();
    }

    public Double readDouble() {
        return byteBuf.readDouble();
    }

    public Long readLong() {
        return byteBuf.readLong();
    }
}
