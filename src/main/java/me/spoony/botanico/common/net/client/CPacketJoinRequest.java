package me.spoony.botanico.common.net.client;

import me.spoony.botanico.common.entities.Entity;
import me.spoony.botanico.common.net.AutoPacketAdapter;
import me.spoony.botanico.common.net.IServerHandler;
import me.spoony.botanico.common.net.server.SPacketNewEntity;
import me.spoony.botanico.common.net.server.SPacketPlayerEID;
import me.spoony.botanico.server.RemoteClient;
import me.spoony.botanico.server.RemoteEntityPlayer;
import me.spoony.botanico.server.net.BotanicoServer;

/**
 * Created by Colten on 11/20/2016.
 */
public class CPacketJoinRequest extends AutoPacketAdapter implements IServerHandler {

  public int gameversion; // TODO not currently used
  public String name;

  @Override
  public void onReceive(BotanicoServer server, RemoteClient client) {
    RemoteEntityPlayer eplayer = new RemoteEntityPlayer(client, name,
        server.level.getOverworld()); // todo possibility of being in any plane
    eplayer.setPosition(8, 8);
    client.getPlayer().getPlane().addEntity(eplayer);

    SPacketPlayerEID peid = new SPacketPlayerEID();
    peid.eid = eplayer.eid;
    client.sendPacket(peid);

    for (Entity ent : server.level.getOverworld().getEntities()) {
      SPacketNewEntity temppne = new SPacketNewEntity();
      temppne.type = ent.getTypeID();
      temppne.eid = ent.eid;
      temppne.x = ent.position.x;
      temppne.y = ent.position.y;
      client.sendPacket(temppne);
    }

/*        for (RemoteClient rc : server.remoteClients) {
            if (rc == client) {
                SPacketPlayerEID ajr = new SPacketPlayerEID();
                ajr.eid = eplayer.eid;
                ajr.x = eplayer.position.x;
                ajr.y = eplayer.position.y;
                rc.sendPacket(ajr);
            } else {
                rc.sendPacket(pne);
            }
        }*/

    eplayer.onJoin();
  }
}
