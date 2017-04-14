package me.spoony.botanico.common.net;

import me.spoony.botanico.common.buildings.Building;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.ItemBuilding;
import me.spoony.botanico.common.items.ItemSlot;
import me.spoony.botanico.common.util.position.TilePosition;
import me.spoony.botanico.server.level.ServerPlane;
import me.spoony.botanico.server.net.BotanicoServer;
import me.spoony.botanico.server.RemoteClient;

/**
 * Created by Colten on 11/25/2016.
 */
public class CPacketBuildingInteraction extends AutoPacketAdapter implements IServerHandler
{
    public static final byte CLICK = 0;
    public static final byte CREATE = 1;
    public static final byte DESTROY = 2;

    public long x;
    public long y;
    public byte type;

    @Override
    public void onReceive(BotanicoServer server, RemoteClient client)
    {
        TilePosition position = new TilePosition(x, y);
        EntityPlayer player = client.getPlayer();
        if (type == CREATE) {
            ItemSlot cursorSlot = client.getPlayer().getCursor();
            if (!(cursorSlot.getStack().getItem() instanceof ItemBuilding)) {
                System.err.println("Received building interaction but there is no building in the cursor");
                return;
            }

            Building newBuilding = ((ItemBuilding)cursorSlot.getStack().getItem()).getBuilding();

            if (newBuilding.canCreate(player.getPlane(), position))
            {
                ((ServerPlane)player.getPlane()).setBuilding(position, newBuilding);
                client.getPlayer().inventory.getStack(EntityPlayer.SLOT_CURSOR).increaseCount(-1);
                client.getPlayer().updatePlayerInventorySlot(EntityPlayer.SLOT_CURSOR);
            }
        } else if (type == DESTROY) {
            ((ServerPlane)player.getPlane()).breakBuildingAndDrop(position, client.getPlayer());
        } else if (type == CLICK) {
            if (player.getPlane().getBuilding(position) == null){
                System.err.println("Received building interaction with null building, server and client possibly out of sync.");
            } else {
                player.getPlane().getBuilding(position).onClick(player.getPlane(), client.getPlayer(), position);
            }
        }
    }
}
