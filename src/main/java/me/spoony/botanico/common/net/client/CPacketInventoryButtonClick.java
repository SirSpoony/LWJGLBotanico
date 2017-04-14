package me.spoony.botanico.common.net.client;

import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.server.RemoteClient;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 11/25/2016.
 */
public class CPacketInventoryButtonClick extends AutoPacketAdapter implements IServerHandler
{
    public int dialogID;
    public int buttonID;

    @Override
    public void onReceive(BotanicoServer server, RemoteClient client) {
        if (client.getPlayer().currentDialog != null)
        {
            client.getPlayer().currentDialog.onButtonPressed(buttonID);
        }
    }
}
