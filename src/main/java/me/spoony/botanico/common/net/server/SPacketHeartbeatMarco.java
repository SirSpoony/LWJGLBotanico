package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;

/**
 * Created by Colten on 12/9/2016.
 */
public class SPacketHeartbeatMarco extends AutoPacketAdapter implements IClientHandler
{
    @Override
    public void onReceive(BotanicoClient client)
    {
        client.sendHeartbeatPolo();
    }
}
