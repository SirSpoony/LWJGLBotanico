package me.spoony.botanico.common.net;

/**
 * Created by Colten on 12/30/2016.
 */
public interface IPacketInterpreter {
    void encodeToPacket(PacketEncoder encoder);
    void decodeFromPacket(PacketDecoder decoder);
}
