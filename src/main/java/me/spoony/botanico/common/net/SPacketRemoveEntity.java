package me.spoony.botanico.common.net;

import me.spoony.botanico.client.BotanicoClient;

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
