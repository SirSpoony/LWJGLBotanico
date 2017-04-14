package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.client.views.GameView;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;

/**
 * Created by Colten on 11/19/2016.
 */
public class SPacketMessage extends AutoPacketAdapter implements IClientHandler
{
    public String message;

    public SPacketMessage() {
        this(null);
    }

    public SPacketMessage(String message) {
        this.message = message;
    }

    @Override
    public void onReceive(BotanicoClient client) {
        GameView.getCommandLine().addMessage(message);
    }
}
