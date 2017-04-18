package me.spoony.botanico.common.net.server;

import me.spoony.botanico.client.BotanicoClient;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IClientHandler;
import me.spoony.botanico.common.entities.EntityItemStack;
import me.spoony.botanico.common.entities.EntityPlayer;
import me.spoony.botanico.common.items.Item;
import me.spoony.botanico.common.items.ItemStack;
import me.spoony.botanico.common.util.position.OmniPosition;
import me.spoony.botanico.common.util.position.PositionType;

/**
 * Created by Colten on 11/20/2016.
 */
public class SPacketNewEntity extends AutoPacketAdapter implements IClientHandler {
    public int type;
    public int eid;
    public double x;
    public double y;
    public int misc;

    @Override
    public void onReceive(BotanicoClient client) {
        if (type == EntityPlayer.ID) {
            EntityPlayer ep = new EntityPlayer(client.getLocalLevel());
            ep.eid = eid;
            ep.position.setX(PositionType.GAME, x);
            ep.position.setY(PositionType.GAME, y);

            client.getLocalLevel().addEntity(ep);

            if (eid == client.getLocalLevel().LOCAL_PLAYER_EID) {
                client.getLocalLevel().setLocalEID();
            }

            //log("Added EntityPlayer EID: "+eid);
        } else if (type == EntityItemStack.ID) {
            EntityItemStack eis = new EntityItemStack(new OmniPosition(PositionType.GAME, x, y), client.getLocalLevel(), new ItemStack(Item.REGISTRY.get(misc)), false);
            eis.eid = eid;
            client.getLocalLevel().addEntity(eis);

            //log("Added EntityItemStack EID: "+eid);
        } else {
            log("Received entity with unrecognized type ID with EID: " + eid);
        }
    }
}
