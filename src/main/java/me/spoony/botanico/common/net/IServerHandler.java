package me.spoony.botanico.common.net;

import me.spoony.botanico.server.net.BotanicoServer;
import me.spoony.botanico.server.RemoteClient;

/**
 * Created by Colten on 12/9/2016.
 */
public interface IServerHandler
{
    void onReceive(BotanicoServer server, RemoteClient client);
}
