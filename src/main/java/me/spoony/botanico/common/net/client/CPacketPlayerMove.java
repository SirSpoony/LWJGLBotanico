package me.spoony.botanico.common.net.client;

import com.google.common.base.Preconditions;
import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.common.net.server.SPacketEntityMove;
import me.spoony.botanico.server.BotanicoServer;
import me.spoony.botanico.server.RemoteEntityPlayer;

/**
 * Created by Colten on 11/20/2016.
 */
public class CPacketPlayerMove extends AutoPacketAdapter implements IServerHandler
{
    public double x;
    public double y;

    @Override
    public void onReceive(BotanicoServer server, RemoteEntityPlayer player) {
        Preconditions.checkNotNull(player, "Client does not have a player entity yet.");

        Entity ent = player;
        ent.position.x = x;
        ent.position.y = y;

        SPacketEntityMove pem = new SPacketEntityMove();
        pem.eid = ent.eid;
        pem.x = ent.position.x;
        pem.y = ent.position.y;
        server.getClientManager().sendPacketToAll(pem, p -> p != player);
    }
}
