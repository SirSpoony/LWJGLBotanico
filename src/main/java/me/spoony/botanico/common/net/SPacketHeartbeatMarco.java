package me.spoony.botanico.common.net;

import me.spoony.botanico.client.BotanicoClient;

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
