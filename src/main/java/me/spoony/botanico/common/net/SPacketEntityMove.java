package me.spoony.botanico.common.net;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.entities.Entity;

/**
 * Created by Colten on 11/20/2016.
 */
public class SPacketEntityMove extends AutoPacketAdapter implements IClientHandler
{
    public int eid;
    public double x;
    public double y;

    @Override
    public void onReceive(BotanicoClient client) {
        for (Entity e : client.getLocalLevel().getEntities()) {
            if (e.eid == eid) {
                e.position.x = x;
                e.position.y = y;

                if (e == client.getLocalPlayer()) {
                    client.gameView.forceCenterCameraOnPlayer();
                }
                return;
            }
        }
        System.out.println("[ClientPacketHandler] Received entity move with unknown EID: "+eid);
    }
}
