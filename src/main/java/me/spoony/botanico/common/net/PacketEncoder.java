package me.spoony.botanico.common.net;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * Created by Colten on 11/20/2016.
 */
public class PacketEncoder
{
    private ByteBuf byteBuf;

    private PacketEncoder() {

    }

    public static PacketEncoder start(ByteBuf byteBuf) {
        PacketEncoder pe = new PacketEncoder();
        pe.byteBuf = byteBuf;
        return pe;
    }

    public ByteBuf finish() {
        return byteBuf;
    }

    public PacketEncoder writeInt(int integer) {
        byteBuf.writeInt(integer);
        return this;
    }

    public PacketEncoder writeString(String string) {
        byteBuf.writeInt(string.length());
        byteBuf.writeCharSequence(string, Charset.defaultCharset());
        return this;
    }

    public PacketEncoder writeFloat(float f) {
        byteBuf.writeFloat(f);
        return this;
    }

    public PacketEncoder writeIntArray(int[] array) {
        byteBuf.writeInt(array.length);
        for (int i = 0; i < array.length; i++) {
            byteBuf.writeInt(array[i]);
        }
        return this;
    }

    public PacketEncoder writeByte(byte b) {
        byteBuf.writeByte(b);
        return this;
    }

    public PacketEncoder writeShort(short s) {
        byteBuf.writeShort(s);
        return this;
    }

    public PacketEncoder writeDouble(double d) {
        byteBuf.writeDouble(d);
        return this;
    }

    public PacketEncoder writeLong(long d) {
        byteBuf.writeLong(d);
        return this;
    }
}
