package me.spoony.botanico.server.net;

import me.spoony.botanico.common.net.Packet;
import me.spoony.botanico.server.RemoteClient;

/**
 * Created by Colten on 11/20/2016.
 */
public class ServerReceivePacketEvent
{
    protected Packet packet;
    protected RemoteClient player;

    public ServerReceivePacketEvent(Packet packet, RemoteClient player)
    {
        this.packet = packet;
        this.player = player;
    }

    public Packet getPacket()
    {
        return packet;
    }

    public RemoteClient getRemoteClient()
    {
        return player;
    }
}
