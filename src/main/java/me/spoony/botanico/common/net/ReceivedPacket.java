package me.spoony.botanico.common.net;

import io.netty.channel.Channel;

/**
 * Created by Colten on 11/20/2016.
 */
public class ReceivedPacket
{
    public ReceivedPacket(Packet packet, Channel channel)
    {
        this.packet = packet;
        this.channel = channel;
    }

    public Packet getPacket()
    {
        return packet;
    }

    protected Packet packet;
    protected Channel channel;
}
