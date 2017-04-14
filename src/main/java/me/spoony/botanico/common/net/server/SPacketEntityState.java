package me.spoony.botanico.common.net.server;

import me.spoony.botanico.ClientOnly;
import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;

/**
 * Created by Colten on 12/30/2016.
 */
public class SPacketEntityState extends AutoPacketAdapter implements IClientHandler {
    public int state;
    public int eid;

    @ClientOnly
    @Override
    public void onReceive(BotanicoClient client) {
        client.getLocalLevel().getEntity(eid).receiveState(state);
    }
}
