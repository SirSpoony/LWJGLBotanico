package me.spoony.botanico.common.net.client;

import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.RemoteClient;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 11/27/2016.
 */
public class CPacketUseItem extends AutoPacketAdapter implements IServerHandler
{
    public long x;
    public long y;

    @Override
    public void onReceive(BotanicoServer server, RemoteClient client) {
        ItemSlot cursor = client.getPlayer().inventory.getSlot(EntityPlayer.SLOT_CURSOR);
        if (cursor.getStack() == null) return;
        cursor.getStack().getItem().onUse(client.getPlayer().getPlane(), client.getPlayer(), cursor, new TilePosition(x, y));
    }
}