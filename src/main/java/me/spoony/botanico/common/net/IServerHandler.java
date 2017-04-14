package me.spoony.botanico.common.net;

import me.spoony.botanico.server.BotanicoServer;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by Colten on 12/9/2016.
 */
public interface IServerHandler
{
    void onReceive(BotanicoServer server, RemoteEntityPlayer client);
}
