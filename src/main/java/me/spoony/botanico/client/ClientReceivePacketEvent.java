package me.spoony.botanico.client;

import io.netty.channel.Channel;
import me.spoony.botanico.common.net.Packet;

/**
 * Created by Colten on 11/20/2016.
 */
public class ClientReceivePacketEvent
{
    protected Packet packet;

    public Packet getPacket()
    {
        return packet;
    }

    public Channel getChannel()
    {
        return channel;
    }

    protected Channel channel;

    public ClientReceivePacketEvent(Packet packet, Channel channel)
    {
        this.packet = packet;
        this.channel = channel;
    }
}
