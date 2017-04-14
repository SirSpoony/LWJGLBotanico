package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;

/**
 * Created by Colten on 12/3/2016.
 */
public class SPacketRemoveEntity extends AutoPacketAdapter implements IClientHandler {
    public int eid;

    @Override
    public void onReceive(BotanicoClient client) {
        client.getLocalLevel().getEntity(eid).remove(client.getLocalLevel());
    }
}
