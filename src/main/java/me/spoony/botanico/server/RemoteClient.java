package me.spoony.botanico.server;

import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.net.Packet;

/**
 * Created by Colten on 11/20/2016.
 */
public class RemoteClient
{
    private final Channel channel;
    private RemoteEntityPlayer player;

    public RemoteClient(Channel channel) {
        Preconditions.checkNotNull(channel, "Cannot initialize remote client with null channel");
        this.channel = channel;
    }

    public RemoteEntityPlayer getPlayer()
    {
        return player;
    }

    public void setPlayer(RemoteEntityPlayer player)
    {
        this.player = player;
    }

    public void sendPacket(Packet packet) {
        channel.writeAndFlush(packet);
    }

    public boolean isSelf(Channel channel) {
        return (channel == this.channel);
    }

    public String getRemoteAddress() {
        return channel.remoteAddress().toString();
    }

    public void closeConnection() {
        channel.close();
        if (getPlayer() != null) {
            player.onLeave();
            player.getPlane().removeEntity(player);
        }
    }
}
